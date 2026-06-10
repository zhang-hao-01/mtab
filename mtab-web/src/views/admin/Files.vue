<template>
  <div class="files-page">
    <div class="page-header">
      <h2>文件管理</h2>
      <div>
        <el-button :icon="Refresh" @click="fetchFiles">刷新</el-button>
        <el-button type="primary" @click="handleScanLocal">扫描本地文件</el-button>
      </div>
    </div>

    <div class="glass-card" style="padding: 20px">
      <el-table :data="files" stripe style="width: 100%">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="文件名" min-width="200" show-overflow-tooltip />
        <el-table-column prop="size" label="大小" width="120">
          <template #default="{ row }">
            {{ formatSize(row.size) }}
          </template>
        </el-table-column>
        <el-table-column prop="type" label="类型" width="120" />
        <el-table-column prop="createTime" label="上传时间" width="180" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
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
        @size-change="fetchFiles"
        @current-change="fetchFiles"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { Refresh } from '@element-plus/icons-vue'
import { list, del, scanLocal } from '@/api/file'
import { ElMessage, ElMessageBox } from 'element-plus'

const files = ref([])
const pagination = reactive({
  page: 1,
  size: 10,
  total: 0
})

function formatSize(bytes) {
  if (!bytes) return '-'
  const units = ['B', 'KB', 'MB', 'GB']
  let i = 0
  let size = bytes
  while (size >= 1024 && i < units.length - 1) {
    size /= 1024
    i++
  }
  return size.toFixed(2) + ' ' + units[i]
}

async function fetchFiles() {
  try {
    const res = await list({ page: pagination.page, size: pagination.size })
    files.value = res.data?.list || []
    pagination.total = res.data?.total || 0
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete(row) {
  try {
    await ElMessageBox.confirm(`确定删除文件 ${row.name}？`, '提示', { type: 'warning' })
    await del([row.id])
    ElMessage.success('删除成功')
    fetchFiles()
  } catch {
    // cancelled or error
  }
}

async function handleScanLocal() {
  try {
    await ElMessageBox.confirm('确定扫描本地文件？', '提示', { type: 'info' })
    await scanLocal()
    ElMessage.success('扫描完成')
    fetchFiles()
  } catch {
    // cancelled or error
  }
}

onMounted(() => {
  fetchFiles()
})
</script>

<style lang="scss" scoped>
.files-page {
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
