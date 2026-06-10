<template>
  <div class="home-page" :style="bgStyle">
    <div class="home-page__overlay"></div>
    <div class="home-page__content">
      <!-- 时钟区域 -->
      <div class="home-page__clock">
        <div class="clock-time">{{ currentTime }}</div>
        <div class="clock-date">{{ currentDate }}</div>
      </div>

      <!-- 搜索栏 -->
      <SearchBar class="home-page__search" />

      <!-- 书签分组区域 -->
      <div class="home-page__bookmarks">
        <BookmarkGroup
          v-for="group in appStore.links"
          :key="group.id"
          :group="group"
          @add="handleAddBookmark"
          @edit="handleEditGroup"
          @itemEdit="handleEditBookmark"
          @itemDelete="handleDeleteBookmark"
        />
      </div>

      <!-- 底部工具栏 -->
      <div class="home-page__tabbar">
        <div
          v-for="tab in appStore.tabbar"
          :key="tab.id"
          class="tabbar-item"
          @click="handleTabClick(tab)"
        >
          <img v-if="tab.icon" :src="tab.icon" :alt="tab.title" class="tabbar-item__icon" />
          <el-icon v-else><Link /></el-icon>
          <span class="tabbar-item__label">{{ tab.title }}</span>
        </div>
        <!-- 功能按钮 -->
        <div class="tabbar-item" @click="goNoteApp">
          <el-icon><Document /></el-icon>
          <span class="tabbar-item__label">笔记</span>
        </div>
        <div class="tabbar-item" @click="openSettings">
          <el-icon><Setting /></el-icon>
          <span class="tabbar-item__label">设置</span>
        </div>
        <div v-if="!userStore.isLoggedIn" class="tabbar-item" @click="$router.push('/login')">
          <el-icon><User /></el-icon>
          <span class="tabbar-item__label">登录</span>
        </div>
        <div v-else class="tabbar-item" @click="userStore.logout()">
          <el-icon><SwitchButton /></el-icon>
          <span class="tabbar-item__label">退出</span>
        </div>
      </div>
    </div>

    <!-- 侧边面板 -->
    <SidePanel />
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { Link, Document, Setting, User, SwitchButton } from '@element-plus/icons-vue'
import { useAppStore } from '@/stores/app'
import { useUserStore } from '@/stores/user'
import { useSettingStore } from '@/stores/setting'
import SearchBar from '@/components/SearchBar.vue'
import BookmarkGroup from '@/components/BookmarkGroup.vue'
import SidePanel from '@/components/SidePanel.vue'

const router = useRouter()
const appStore = useAppStore()
const userStore = useUserStore()
const settingStore = useSettingStore()

const currentTime = ref('')
const currentDate = ref('')
let timer = null

const bgStyle = computed(() => {
  if (appStore.wallpaper) {
    return {
      backgroundImage: `url(${appStore.wallpaper})`,
      backgroundSize: 'cover',
      backgroundPosition: 'center',
      backgroundRepeat: 'no-repeat'
    }
  }
  return {
    background: 'linear-gradient(135deg, #0c0c1d 0%, #1a1a2e 50%, #16213e 100%)'
  }
})

function updateTime() {
  const now = new Date()
  const hours = String(now.getHours()).padStart(2, '0')
  const minutes = String(now.getMinutes()).padStart(2, '0')
  currentTime.value = `${hours}:${minutes}`

  const weekDays = ['日', '一', '二', '三', '四', '五', '六']
  const month = now.getMonth() + 1
  const day = now.getDate()
  const weekDay = weekDays[now.getDay()]
  currentDate.value = `${month}月${day}日 星期${weekDay}`
}

function handleAddBookmark(group) {
  settingStore.openSidePanel('addBookmark', { groupId: group.id, title: '', url: '', icon: '' })
}

function handleEditGroup(group) {
  settingStore.openSidePanel('editGroup', { ...group })
}

function handleEditBookmark(bookmark) {
  settingStore.openSidePanel('editBookmark', { ...bookmark })
}

function handleDeleteBookmark(bookmark) {
  settingStore.openSidePanel('editBookmark', { ...bookmark })
}

function handleTabClick(tab) {
  if (tab.url) {
    window.open(tab.url, '_blank')
  }
}

function goNoteApp() {
  if (userStore.isLoggedIn) {
    router.push('/noteApp')
  } else {
    router.push('/login')
  }
}

function openSettings() {
  settingStore.openSidePanel('settings')
}

onMounted(() => {
  updateTime()
  timer = setInterval(updateTime, 1000)
  appStore.fetchAllData()
  document.documentElement.setAttribute('data-theme', settingStore.theme)
})

onUnmounted(() => {
  if (timer) {
    clearInterval(timer)
  }
})
</script>

<style lang="scss" scoped>
.home-page {
  width: 100%;
  height: 100vh;
  position: relative;
  overflow: hidden;

  &__overlay {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.3);
    z-index: 0;
  }

  &__content {
    position: relative;
    z-index: 1;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: $spacing-xl $spacing-lg;
    overflow-y: auto;
  }

  &__clock {
    text-align: center;
    margin-bottom: $spacing-xl;
    user-select: none;

    .clock-time {
      font-size: 72px;
      font-weight: 200;
      color: #fff;
      letter-spacing: 4px;
      text-shadow: 0 2px 20px rgba(0, 0, 0, 0.3);
      line-height: 1;
    }

    .clock-date {
      font-size: $font-size-lg;
      color: rgba(255, 255, 255, 0.7);
      margin-top: $spacing-sm;
      text-shadow: 0 1px 10px rgba(0, 0, 0, 0.3);
    }
  }

  &__search {
    margin-bottom: $spacing-xl;
  }

  &__bookmarks {
    width: 100%;
    max-width: 1200px;
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
    gap: $spacing-md;
    flex: 1;
    padding-bottom: 80px;
  }

  &__tabbar {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: $spacing-lg;
    padding: $spacing-md $spacing-lg;
    background: rgba(0, 0, 0, 0.4);
    backdrop-filter: blur(20px);
    -webkit-backdrop-filter: blur(20px);
    border-top: 1px solid rgba(255, 255, 255, 0.1);
    z-index: 10;
  }
}

.tabbar-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
  cursor: pointer;
  padding: 6px 12px;
  border-radius: $border-radius-sm;
  transition: all $transition-fast;
  color: rgba(255, 255, 255, 0.7);

  &:hover {
    color: #fff;
    background: rgba(255, 255, 255, 0.1);
  }

  &__icon {
    width: 22px;
    height: 22px;
    border-radius: 4px;
  }

  &__label {
    font-size: $font-size-xs;
    white-space: nowrap;
  }

  .el-icon {
    font-size: 22px;
  }
}

// 响应式
@media (max-width: $breakpoint-md) {
  .home-page {
    &__clock .clock-time {
      font-size: 48px;
    }

    &__bookmarks {
      grid-template-columns: 1fr;
    }

    &__tabbar {
      gap: $spacing-sm;
    }
  }

  .tabbar-item {
    padding: 4px 8px;

    &__label {
      font-size: 10px;
    }
  }
}
</style>
