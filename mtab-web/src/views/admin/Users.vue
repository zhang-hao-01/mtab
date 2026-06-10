<template>
  <div class="users-page">
    <div class="page-header">
      <h2>用户管理</h2>
      <el-button type="primary" :icon="Refresh" @click="fetchUsers">刷新</el-button>
    </div>

    <div class="glass-card" style="padding: 20px">
      <el-table :data="users" stripe style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" width="150" />
        <el-table-column prop="email" label="邮箱" min-width="200" />
        <el-table-column prop="isAdmin" label="角色" width="100">
          <template #default="{ row }">
            <el-tag :type="row.isAdmin === 1 ? 'danger' : 'info'" size="small">
              {{ row.isAdmin === 1 ? '管理员' : '用户' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'" size="small">
              {{ row.status === 1 ? '正常' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="注册时间" width="180" />
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
        @size-change="fetchUsers"
        @current-change="fetchUsers"
      />
    </div>

    <!-- 编辑对话框 -->
    <el-dialog v-model="showDialog" title="编辑用户" width="500px">
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="editForm.username" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="editForm.email" />
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="editForm.isAdmin">
            <el-option :value="0" label="普通用户" />
            <el-option :value="1" label="管理员" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="editForm.status">
            <el-option :value="1" label="正常" />
            <el-option :value="0" label="禁用" />
          </el-select>
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
import { Refresh } from '@element-plus/icons-vue'
import { userList, userUpdate, userDelete } from '@/api/admin'
import { ElMessage, ElMessageBox } from 'element-plus'

const users = ref([])
const showDialog = ref(false)
const editForm = reactive({
  id: null,
  username: '',
  email: '',
  isAdmin: 0,
  status: 1
})
const pagination = reactive({
  page: 1,
  size: 10,
  total: 0
})

async function fetchUsers() {
  try {
    const res = await userList({ page: pagination.page, size: pagination.size })
    users.value = res.data?.list || []
    pagination.total = res.data?.total || 0
  } catch {
    // error handled by interceptor
  }
}

function handleEdit(row) {
  Object.assign(editForm, {
    id: row.id,
    username: row.username,
    email: row.email,
    isAdmin: row.isAdmin,
    status: row.status
  })
  showDialog.value = true
}

async function handleSave() {
  try {
    await userUpdate(editForm)
    ElMessage.success('更新成功')
    showDialog.value = false
    fetchUsers()
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete(row) {
  try {
    await ElMessageBox.confirm(`确定删除用户 ${row.username}？`, '提示', {
      type: 'warning'
    })
    await userDelete(row.id)
    ElMessage.success('删除成功')
    fetchUsers()
  } catch {
    // cancelled or error
  }
}

onMounted(() => {
  fetchUsers()
})
</script>

<style lang="scss" scoped>
.users-page {
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
