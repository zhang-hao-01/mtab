<template>
  <div class="link-store-page">
    <div class="page-header">
      <h2>书签仓库管理</h2>
      <el-button type="primary" :icon="Plus" @click="handleAdd">添加书签</el-button>
    </div>

    <div class="glass-card" style="padding: 20px">
      <el-table :data="links" stripe style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" min-width="150" />
        <el-table-column prop="url" label="链接" min-width="200" show-overflow-tooltip />
        <el-table-column prop="folderName" label="分类" width="120" />
        <el-table-column prop="isPublic" label="类型" width="100">
          <template #default="{ row }">
            <el-tag :type="row.isPublic === 1 ? 'success' : 'info'" size="small">
              {{ row.isPublic === 1 ? '公开' : '私有' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="installNum" label="安装数" width="100" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="pagination.page"
        v-model:page-size="pagination.size"
        :total="pagination.total"
        :page-sizes="[10, 20, 50]"
        layout="total, sizes, prev, pager, next"
        style="margin-top: 20px; justify-content: flex-end"
        @size-change="fetchLinks"
        @current-change="fetchLinks"
      />
    </div>

    <!-- 添加/编辑对话框 -->
    <el-dialog v-model="showDialog" :title="editForm.id ? '编辑书签' : '添加书签'" width="500px">
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="editForm.title" />
        </el-form-item>
        <el-form-item label="链接">
          <el-input v-model="editForm.url" />
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="editForm.folderId" placeholder="选择分类">
            <el-option
              v-for="folder in folders"
              :key="folder.id"
              :label="folder.name"
              :value="folder.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="公开">
          <el-switch v-model="editForm.isPublic" :active-value="1" :inactive-value="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { Plus } from '@element-plus/icons-vue'
import { listManager, add, getFolderAdmin, del } from '@/api/linkStore'
import { ElMessage, ElMessageBox } from 'element-plus'

const links = ref([])
const folders = ref([])
const showDialog = ref(false)
const editForm = reactive({
  id: null,
  title: '',
  url: '',
  folderId: null,
  isPublic: 1
})
const pagination = reactive({
  page: 1,
  size: 10,
  total: 0
})

async function fetchLinks() {
  try {
    const res = await listManager({ page: pagination.page, size: pagination.size })
    links.value = res.data?.list || []
    pagination.total = res.data?.total || 0
  } catch {
    // error handled by interceptor
  }
}

async function fetchFolders() {
  try {
    const res = await getFolderAdmin()
    folders.value = res.data || []
  } catch {
    // error handled by interceptor
  }
}

function handleAdd() {
  Object.assign(editForm, { id: null, title: '', url: '', folderId: null, isPublic: 1 })
  showDialog.value = true
}

function handleEdit(row) {
  Object.assign(editForm, row)
  showDialog.value = true
}

async function handleSave() {
  try {
    if (editForm.id) {
      await add(editForm)
    } else {
      await add(editForm)
    }
    ElMessage.success('保存成功')
    showDialog.value = false
    fetchLinks()
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete(row) {
  try {
    await ElMessageBox.confirm(`确定删除书签 ${row.title}？`, '提示', { type: 'warning' })
    await del([row.id])
    ElMessage.success('删除成功')
    fetchLinks()
  } catch {
    // cancelled or error
  }
}

onMounted(() => {
  fetchLinks()
  fetchFolders()
})
</script>

<style lang="scss" scoped>
.link-store-page {
  .page-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 20px;

    h2 {
      font-size: 20px;
      font-weight: 600;
      color: var(--text-primary);
    }
  }
}
</style>
