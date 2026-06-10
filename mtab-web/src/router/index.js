import { createRouter, createWebHistory } from 'vue-router'
import { isLoggedIn } from '@/utils/auth'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/Home.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/Register.vue')
  },
  {
    path: '/noteApp',
    name: 'NoteApp',
    component: () => import('@/views/NoteApp.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/admin',
    name: 'Admin',
    component: () => import('@/components/AdminLayout.vue'),
    meta: { requiresAuth: true, requiresAdmin: true },
    redirect: '/admin/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'AdminDashboard',
        component: () => import('@/views/admin/Dashboard.vue')
      },
      {
        path: 'users',
        name: 'AdminUsers',
        component: () => import('@/views/admin/Users.vue')
      },
      {
        path: 'settings',
        name: 'AdminSettings',
        component: () => import('@/views/admin/Settings.vue')
      },
      {
        path: 'link-store',
        name: 'AdminLinkStore',
        component: () => import('@/views/admin/LinkStore.vue')
      },
      {
        path: 'search-engine',
        name: 'AdminSearchEngine',
        component: () => import('@/views/admin/SearchEngine.vue')
      },
      {
        path: 'wallpaper',
        name: 'AdminWallpaper',
        component: () => import('@/views/admin/Wallpaper.vue')
      },
      {
        path: 'files',
        name: 'AdminFiles',
        component: () => import('@/views/admin/Files.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth && !isLoggedIn()) {
    next({ name: 'Login', query: { redirect: to.fullPath } })
    return
  }
  if (to.meta.requiresAdmin) {
    const userStr = localStorage.getItem('mtab_user_info')
    if (userStr) {
      try {
        const userInfo = JSON.parse(userStr)
        if (userInfo.isAdmin !== 1) {
          next({ name: 'Home' })
          return
        }
      } catch {
        next({ name: 'Login' })
        return
      }
    } else {
      next({ name: 'Login' })
      return
    }
  }
  next()
})

export default router
