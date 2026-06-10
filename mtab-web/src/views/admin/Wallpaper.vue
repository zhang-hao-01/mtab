<template>
  <div class="wallpaper-page">
    <div class="page-header">
      <h2>壁纸管理</h2>
      <el-button type="primary" :icon="Plus" @click="handleAddFolder">添加分类</el-button>
    </div>

    <!-- 分类列表 -->
    <div class="glass-card" style="padding: 20px; margin-bottom: 20px">
      <h3 style="margin-bottom: 16px; color: var(--text-primary)">壁纸分类</h3>
      <el-table :data="folders" stripe style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="分类名" width="200" />
        <el-table-column prop="sort" label="排序" width="100" />
        <el-table-column label="壁纸数" width="100">
          <template #default="{ row }">
            {{ row.count || 0 }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="250">
          <template #default="{ row }">
            <el-button size="small" @click="handleViewWallpapers(row)">查看壁纸</el-button>
            <el-button size="small" @click="handleEditFolder(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDeleteFolder(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 壁纸列表 -->
    <div v-if="currentFolder" class="glass-card" style="padding: 20px">
      <h3 style="margin-bottom: 16px; color: var(--text-primary)">
        {{ currentFolder.name }} - 壁纸列表
        <el-button style="margin-left: 12px" size="small" type="primary" :icon="Plus" @click="handleAddWallpaper">添加壁纸</el-button>
      </h3>
      <div class="wallpaper-grid">
        <div v-for="wp in wallpapers" :key="wp.id" class="wallpaper-item">
          <img :src="wp.url" :alt="wp.title" />
          <div class="wallpaper-item__overlay">
            <el-button type="danger" size="small" :icon="Delete" circle @click="handleDeleteWallpaper(wp)" />
          </div>
        </div>
        <el-empty v-if="wallpapers.length === 0" description="暂无壁纸" />
      </div>
    </div>

    <!-- 分类编辑对话框 -->
    <el-dialog v-model="showFolderDialog" :title="folderForm.id ? '编辑分类' : '添加分类'" width="400px">
      <el-form :model="folderForm" label-width="80px">
        <el-form-item label="分类名">
          <el-input v-model="folderForm.name" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="folderForm.sort" :min="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showFolderDialog = false">取消</el-button>
        <el-button type="primary" @click="saveFolder">保存</el-button>
      </template>
    </el-dialog>

    <!-- 添加壁纸对话框 -->
    <el-dialog v-model="showWallpaperDialog" title="添加壁纸" width="400px">
      <el-form :model="wallpaperForm" label-width="80px">
        <el-form-item label="壁纸URL">
          <el-input v-model="wallpaperForm.url" placeholder="图片URL" />
        </el-form-item>
        <el-form-item label="标题">
          <el-input v-model="wallpaperForm.title" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showWallpaperDialog = false">取消</el-button>
        <el-button type="primary" @click="saveWallpaper">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { Plus, Delete } from '@element-plus/icons-vue'
import {
  getFolder, editFolder, delFolder,
  getFolderWallpaper, addWallpaper, deleteWallpaper
} from '@/api/wallpaper'
import { ElMessage, ElMessageBox } from 'element-plus'

const folders = ref([])
const wallpapers = ref([])
const currentFolder = ref(null)
const showFolderDialog = ref(false)
const showWallpaperDialog = ref(false)

const folderForm = reactive({ id: null, name: '', sort: 0 })
const wallpaperForm = reactive({ url: '', title: '' })

async function fetchFolders() {
  try {
    const res = await getFolder()
    folders.value = res.data || []
  } catch {
    // error handled by interceptor
  }
}

async function fetchWallpapers(folderId) {
  try {
    const res = await getFolderWallpaper({ folderId })
    wallpapers.value = res.data || []
  } catch {
    // error handled by interceptor
  }
}

function handleAddFolder() {
  Object.assign(folderForm, { id: null, name: '', sort: 0 })
  showFolderDialog.value = true
}

function handleEditFolder(row) {
  Object.assign(folderForm, row)
  showFolderDialog.value = true
}

async function saveFolder() {
  try {
    await editFolder(folderForm)
    ElMessage.success('保存成功')
    showFolderDialog.value = false
    fetchFolders()
  } catch {
    // error handled by interceptor
  }
}

async function handleDeleteFolder(row) {
  try {
    await ElMessageBox.confirm(`确定删除分类 ${row.name}？`, '提示', { type: 'warning' })
    await delFolder(row.id)
    ElMessage.success('删除成功')
    if (currentFolder.value?.id === row.id) {
      currentFolder.value = null
    }
    fetchFolders()
  } catch {
    // cancelled or error
  }
}

function handleViewWallpapers(folder) {
  currentFolder.value = folder
  fetchWallpapers(folder.id)
}

function handleAddWallpaper() {
  Object.assign(wallpaperForm, { url: '', title: '' })
  showWallpaperDialog.value = true
}

async function saveWallpaper() {
  try {
    await addWallpaper({
      ...wallpaperForm,
      folderId: currentFolder.value.id
    })
    ElMessage.success('添加成功')
    showWallpaperDialog.value = false
    fetchWallpapers(currentFolder.value.id)
  } catch {
    // error handled by interceptor
  }
}

async function handleDeleteWallpaper(wp) {
  try {
    await ElMessageBox.confirm('确定删除该壁纸？', '提示', { type: 'warning' })
    await deleteWallpaper(wp.id)
    ElMessage.success('删除成功')
    fetchWallpapers(currentFolder.value.id)
  } catch {
    // cancelled or error
  }
}

onMounted(() => {
  fetchFolders()
})
</script>

<style lang="scss" scoped>
.wallpaper-page {
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

  .wallpaper-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 16px;
  }

  .wallpaper-item {
    position: relative;
    border-radius: $border-radius-md;
    overflow: hidden;
    aspect-ratio: 16 / 9;

    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }

    &__overlay {
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background: rgba(0, 0, 0, 0.5);
      display: flex;
      align-items: center;
      justify-content: center;
      opacity: 0;
      transition: opacity $transition-fast;
    }

    &:hover &__overlay {
      opacity: 1;
    }
  }
}
</style>
