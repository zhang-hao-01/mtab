import axios from 'axios'
import { getToken, getUserId, clearAuth } from '@/utils/auth'
import { ElMessage } from 'element-plus'
import router from '@/router'

const request = axios.create({
  baseURL: '/api',
  timeout: 30000
})

request.interceptors.request.use(config => {
  const token = getToken()
  const userId = getUserId()
  if (token && userId) {
    config.headers['Token'] = token
    config.headers['Userid'] = userId
  }
  return config
})

request.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code === 0) {
      ElMessage.error(res.msg || '请求失败')
      return Promise.reject(new Error(res.msg))
    }
    return res
  },
  error => {
    if (error.response?.status === 401) {
      clearAuth()
      router.push('/login')
    }
    ElMessage.error(error.message || '网络错误')
    return Promise.reject(error)
  }
)

export default request
