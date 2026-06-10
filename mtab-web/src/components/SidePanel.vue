<template>
  <Transition name="slide">
    <div v-if="settingStore.showSidePanel" class="side-panel">
      <div class="side-panel__overlay" @click="settingStore.closeSidePanel"></div>
      <div class="side-panel__content">
        <div class="side-panel__header">
          <h3>{{ panelTitle }}</h3>
          <el-button :icon="Close" circle size="small" @click="settingStore.closeSidePanel" />
        </div>
        <div class="side-panel__body">
          <!-- 书签编辑 -->
          <el-form v-if="settingStore.sidePanelType === 'editBookmark'" :model="formData" label-width="80px">
            <el-form-item label="标题">
              <el-input v-model="formData.title" placeholder="书签标题" />
            </el-form-item>
            <el-form-item label="链接">
              <el-input v-model="formData.url" placeholder="https://" />
            </el-form-item>
            <el-form-item label="图标">
              <el-input v-model="formData.icon" placeholder="图标URL" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSaveBookmark">保存</el-button>
              <el-button type="danger" v-if="formData.id" @click="handleDeleteBookmark">删除</el-button>
            </el-form-item>
          </el-form>

          <!-- 分组编辑 -->
          <el-form v-else-if="settingStore.sidePanelType === 'editGroup'" :model="formData" label-width="80px">
            <el-form-item label="分组名">
              <el-input v-model="formData.title" placeholder="分组名称" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSaveGroup">保存</el-button>
              <el-button type="danger" v-if="formData.id" @click="handleDeleteGroup">删除</el-button>
            </el-form-item>
          </el-form>

          <!-- 设置面板 -->
          <div v-else-if="settingStore.sidePanelType === 'settings'" class="side-panel__settings">
            <div class="setting-item">
              <span>主题切换</span>
              <el-switch
                :model-value="settingStore.isDark"
                active-text="暗色"
                inactive-text="亮色"
                @change="settingStore.toggleTheme()"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </Transition>
</template>

<script setup>
import { computed, watch, ref } from 'vue'
import { Close } from '@element-plus/icons-vue'
import { useSettingStore } from '@/stores/setting'
import { useAppStore } from '@/stores/app'
import { update as updateLink } from '@/api/link'
import { ElMessage } from 'element-plus'
import { deepClone } from '@/utils/common'

const settingStore = useSettingStore()
const appStore = useAppStore()

const formData = ref({})

const panelTitle = computed(() => {
  const titles = {
    editBookmark: '编辑书签',
    addBookmark: '添加书签',
    editGroup: '编辑分组',
    addGroup: '添加分组',
    settings: '设置'
  }
  return titles[settingStore.sidePanelType] || '面板'
})

watch(
  () => settingStore.sidePanelData,
  (val) => {
    if (val) {
      formData.value = deepClone(val)
    } else {
      formData.value = {}
    }
  },
  { immediate: true }
)

async function handleSaveBookmark() {
  try {
    const links = deepClone(appStore.links)
    // 更新或添加书签逻辑
    await updateLink({ links })
    await appStore.fetchLinks()
    settingStore.closeSidePanel()
    ElMessage.success('保存成功')
  } catch {
    // error handled by interceptor
  }
}

async function handleDeleteBookmark() {
  try {
    const links = deepClone(appStore.links)
    await updateLink({ links })
    await appStore.fetchLinks()
    settingStore.closeSidePanel()
    ElMessage.success('删除成功')
  } catch {
    // error handled by interceptor
  }
}

async function handleSaveGroup() {
  try {
    const links = deepClone(appStore.links)
    await updateLink({ links })
    await appStore.fetchLinks()
    settingStore.closeSidePanel()
    ElMessage.success('保存成功')
  } catch {
    // error handled by interceptor
  }
}

async function handleDeleteGroup() {
  try {
    const links = deepClone(appStore.links)
    await updateLink({ links })
    await appStore.fetchLinks()
    settingStore.closeSidePanel()
    ElMessage.success('删除成功')
  } catch {
    // error handled by interceptor
  }
}
</script>

<style lang="scss" scoped>
.side-panel {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 1000;
  display: flex;

  &__overlay {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.4);
  }

  &__content {
    position: relative;
    margin-left: auto;
    width: 400px;
    max-width: 90vw;
    height: 100%;
    background: var(--bg-secondary);
    border-left: 1px solid var(--border-color);
    display: flex;
    flex-direction: column;
    overflow: hidden;
  }

  &__header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: $spacing-lg $spacing-lg $spacing-md;
    border-bottom: 1px solid var(--border-color);

    h3 {
      font-size: $font-size-xl;
      color: var(--text-primary);
      font-weight: 600;
    }
  }

  &__body {
    flex: 1;
    overflow-y: auto;
    padding: $spacing-lg;
  }

  &__settings {
    .setting-item {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: $spacing-md 0;
      border-bottom: 1px solid var(--border-color);
      color: var(--text-primary);
    }
  }
}
</style>
