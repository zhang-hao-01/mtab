<template>
  <div class="search-engine-page">
    <div class="page-header">
      <h2>搜索引擎管理</h2>
      <el-button type="primary" :icon="Plus" @click="handleAdd">添加搜索引擎</el-button>
    </div>

    <div class="glass-card" style="padding: 20px">
      <el-table :data="engines" stripe style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="名称" width="150" />
        <el-table-column prop="url" label="搜索URL" min-width="250" show-overflow-tooltip />
        <el-table-column prop="icon" label="图标" width="80">
          <template #default="{ row }">
            <img v-if="row.icon" :src="row.icon" style="width: 20px; height: 20px" />
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序" width="80" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 添加/编辑对话框 -->
    <el-dialog v-model="showDialog" :title="editForm.id ? '编辑搜索引擎' : '添加搜索引擎'" width="500px">
      <el-form :model="editForm" label-width="100px">
        <el-form-item label="名称">
          <el-input v-model="editForm.name" placeholder="如：百度" />
        </el-form-item>
        <el-form-item label="搜索URL">
          <el-input v-model="editForm.url" placeholder="如：https://www.baidu.com/s?wd=%s" />
        </el-form-item>
        <el-form-item label="图标URL">
          <el-input v-model="editForm.icon" placeholder="图标URL" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="editForm.sort" :min="0" />
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
import { list, add, del } from '@/api/searchEngine'
import { ElMessage, ElMessageBox } from 'element-plus'

const engines = ref([])
const showDialog = ref(false)
const editForm = reactive({
  id: null,
  name: '',
  url: '',
  icon: '',
  sort: 0
})

async function fetchEngines() {
  try {
    const res = await list({})
    engines.value = res.data || []
  } catch {
    // error handled by interceptor
  }
}

function handleAdd() {
  Object.assign(editForm, { id: null, name: '', url: '', icon: '', sort: 0 })
  showDialog.value = true
}

function handleEdit(row) {
  Object.assign(editForm, row)
  showDialog.value = true
}

async function handleSave() {
  try {
    await add(editForm)
    ElMessage.success('保存成功')
    showDialog.value = false
    fetchEngines()
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete(row) {
  try {
    await ElMessageBox.confirm(`确定删除搜索引擎 ${row.name}？`, '提示', { type: 'warning' })
    await del([row.id])
    ElMessage.success('删除成功')
    fetchEngines()
  } catch {
    // cancelled or error
  }
}

onMounted(() => {
  fetchEngines()
})
</script>

<style lang="scss" scoped>
.search-engine-page {
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
