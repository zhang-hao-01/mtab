<template>
  <div class="admin-layout">
    <el-container>
      <el-aside :width="isCollapsed ? '64px' : '220px'" class="admin-aside">
        <div class="admin-logo">
          <h1 v-if="!isCollapsed">mTab</h1>
          <span v-else>M</span>
        </div>
        <el-menu
          :default-active="currentRoute"
          :collapse="isCollapsed"
          router
        >
          <el-menu-item index="/admin/dashboard">
            <el-icon><DataAnalysis /></el-icon>
            <template #title>仪表盘</template>
          </el-menu-item>
          <el-menu-item index="/admin/users">
            <el-icon><User /></el-icon>
            <template #title>用户管理</template>
          </el-menu-item>
          <el-menu-item index="/admin/link-store">
            <el-icon><Link /></el-icon>
            <template #title>书签仓库</template>
          </el-menu-item>
          <el-menu-item index="/admin/search-engine">
            <el-icon><Search /></el-icon>
            <template #title>搜索引擎</template>
          </el-menu-item>
          <el-menu-item index="/admin/wallpaper">
            <el-icon><Picture /></el-icon>
            <template #title>壁纸管理</template>
          </el-menu-item>
          <el-menu-item index="/admin/settings">
            <el-icon><Setting /></el-icon>
            <template #title>系统设置</template>
          </el-menu-item>
          <el-menu-item index="/admin/files">
            <el-icon><Folder /></el-icon>
            <template #title>文件管理</template>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header class="admin-header">
          <div class="admin-header__left">
            <el-button :icon="isCollapsed ? Expand : Fold" text @click="isCollapsed = !isCollapsed" />
            <el-breadcrumb separator="/">
              <el-breadcrumb-item :to="{ path: '/admin' }">管理后台</el-breadcrumb-item>
              <el-breadcrumb-item>{{ currentPageTitle }}</el-breadcrumb-item>
            </el-breadcrumb>
          </div>
          <div class="admin-header__right">
            <el-button text @click="$router.push('/')">
              <el-icon><HomeFilled /></el-icon>
              前台首页
            </el-button>
            <el-dropdown @command="handleCommand">
              <span class="admin-header__user">
                <el-avatar :size="28" :icon="UserFilled" />
                <span>{{ userStore.username || '管理员' }}</span>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>
        <el-main class="admin-main">
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import {
  DataAnalysis, User, Link, Search, Picture, Setting, Folder,
  Expand, Fold, HomeFilled, UserFilled
} from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const isCollapsed = ref(false)

const currentRoute = computed(() => route.path)

const pageTitles = {
  '/admin/dashboard': '仪表盘',
  '/admin/users': '用户管理',
  '/admin/link-store': '书签仓库',
  '/admin/search-engine': '搜索引擎',
  '/admin/wallpaper': '壁纸管理',
  '/admin/settings': '系统设置',
  '/admin/files': '文件管理'
}

const currentPageTitle = computed(() => pageTitles[route.path] || '管理后台')

function handleCommand(command) {
  if (command === 'logout') {
    userStore.logout()
  }
}
</script>

<style lang="scss" scoped>
.admin-layout {
  width: 100%;
  height: 100vh;
  background: var(--bg-color);
}

.admin-aside {
  background: var(--bg-secondary);
  border-right: 1px solid var(--border-color);
  transition: width $transition-normal;
  overflow: hidden;
}

.admin-logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom: 1px solid var(--border-color);

  h1 {
    font-size: 24px;
    font-weight: 700;
    background: linear-gradient(135deg, $primary-color, #667eea);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
  }

  span {
    font-size: 24px;
    font-weight: 700;
    color: $primary-color;
  }
}

.admin-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid var(--border-color);
  background: var(--bg-secondary);
  padding: 0 $spacing-lg;

  &__left {
    display: flex;
    align-items: center;
    gap: $spacing-md;
  }

  &__right {
    display: flex;
    align-items: center;
    gap: $spacing-md;
  }

  &__user {
    display: flex;
    align-items: center;
    gap: 8px;
    cursor: pointer;
    color: var(--text-primary);
  }
}

.admin-main {
  background: var(--bg-color);
  overflow-y: auto;
}
</style>
