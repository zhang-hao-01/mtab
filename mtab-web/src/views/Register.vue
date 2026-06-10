<template>
  <div class="register-page">
    <div class="register-page__bg"></div>
    <div class="register-page__container glass-card">
      <div class="register-page__header">
        <h1>mTab</h1>
        <p>创建新账号</p>
      </div>
      <el-form ref="formRef" :model="formData" :rules="rules" label-width="0" size="large">
        <el-form-item prop="username">
          <el-input
            v-model="formData.username"
            placeholder="用户名"
            :prefix-icon="User"
          />
        </el-form-item>
        <el-form-item prop="email">
          <el-input
            v-model="formData.email"
            placeholder="邮箱"
            :prefix-icon="Message"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="formData.password"
            type="password"
            placeholder="密码"
            :prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input
            v-model="formData.confirmPassword"
            type="password"
            placeholder="确认密码"
            :prefix-icon="Lock"
            show-password
            @keydown.enter="handleRegister"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            :loading="loading"
            class="register-page__btn"
            @click="handleRegister"
          >
            注 册
          </el-button>
        </el-form-item>
      </el-form>
      <div class="register-page__footer">
        <span>已有账号？</span>
        <router-link to="/login" class="register-page__link">立即登录</router-link>
      </div>
      <div class="register-page__back">
        <router-link to="/" class="register-page__link">← 返回首页</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { User, Lock, Message } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()

const formRef = ref(null)
const loading = ref(false)

const formData = reactive({
  username: '',
  email: '',
  password: '',
  confirmPassword: ''
})

const validateConfirmPassword = (rule, value, callback) => {
  if (value !== formData.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度为3-20个字符', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入有效的邮箱地址', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码至少6个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

async function handleRegister() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return

  loading.value = true
  try {
    await userStore.register({
      username: formData.username,
      email: formData.email,
      password: formData.password
    })
  } catch {
    // error handled by interceptor
  } finally {
    loading.value = false
  }
}
</script>

<style lang="scss" scoped>
.register-page {
  width: 100%;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;

  &__bg {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: linear-gradient(135deg, #0c0c1d 0%, #1a1a2e 50%, #16213e 100%);
    z-index: 0;
  }

  &__container {
    position: relative;
    z-index: 1;
    width: 400px;
    max-width: 90vw;
    padding: $spacing-xl;
    background: rgba(255, 255, 255, 0.06);
    backdrop-filter: blur(20px);
    -webkit-backdrop-filter: blur(20px);
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: $border-radius-xl;
  }

  &__header {
    text-align: center;
    margin-bottom: $spacing-xl;

    h1 {
      font-size: 36px;
      font-weight: 700;
      background: linear-gradient(135deg, $primary-color, #667eea);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      margin-bottom: $spacing-sm;
    }

    p {
      color: var(--text-secondary);
      font-size: $font-size-md;
    }
  }

  &__btn {
    width: 100%;
    border-radius: $border-radius-md;
  }

  &__footer {
    text-align: center;
    margin-top: $spacing-md;
    color: var(--text-secondary);
    font-size: $font-size-sm;
  }

  &__link {
    color: $primary-color;
    text-decoration: none;
    font-size: $font-size-sm;

    &:hover {
      text-decoration: underline;
    }
  }

  &__back {
    text-align: center;
    margin-top: $spacing-lg;
  }
}
</style>
