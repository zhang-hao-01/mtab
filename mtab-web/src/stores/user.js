import { defineStore } from 'pinia'
import { login as loginApi, register as registerApi, loginOut, get as getUserInfo, updateInfo as updateInfoApi } from '@/api/user'
import { setToken, setUserId, clearAuth, isLoggedIn as checkLoggedIn } from '@/utils/auth'
import { ElMessage } from 'element-plus'
import router from '@/router'

export const useUserStore = defineStore('user', {
  state: () => ({
    userInfo: JSON.parse(localStorage.getItem('mtab_user_info') || 'null'),
    isLoggedIn: checkLoggedIn(),
    isAdmin: false
  }),

  getters: {
    username: (state) => state.userInfo?.username || '',
    email: (state) => state.userInfo?.email || '',
    avatar: (state) => state.userInfo?.avatar || ''
  },

  actions: {
    async login(data) {
      const res = await loginApi(data)
      setToken(res.data.token)
      setUserId(res.data.userId)
      this.isLoggedIn = true
      await this.getUserInfo()
      ElMessage.success('登录成功')
      const redirect = router.currentRoute.value.query.redirect || '/'
      router.push(redirect)
    },

    async register(data) {
      await registerApi(data)
      ElMessage.success('注册成功，请登录')
      router.push('/login')
    },

    async logout() {
      try {
        await loginOut()
      } catch (e) {
        // ignore
      }
      clearAuth()
      localStorage.removeItem('mtab_user_info')
      this.userInfo = null
      this.isLoggedIn = false
      this.isAdmin = false
      router.push('/login')
    },

    async getUserInfo() {
      const res = await getUserInfo()
      this.userInfo = res.data
      this.isAdmin = res.data?.isAdmin === 1
      localStorage.setItem('mtab_user_info', JSON.stringify(res.data))
    },

    async updateInfo(data) {
      await updateInfoApi(data)
      ElMessage.success('更新成功')
      await this.getUserInfo()
    }
  }
})
