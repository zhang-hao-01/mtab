<template>
  <div class="settings-page">
    <div class="page-header">
      <h2>系统设置</h2>
    </div>

    <el-tabs v-model="activeTab">
      <el-tab-pane label="基本设置" name="basic">
        <div class="glass-card" style="padding: 24px">
          <el-form :model="basicForm" label-width="120px">
            <el-form-item label="站点名称">
              <el-input v-model="basicForm.siteName" placeholder="mTab" />
            </el-form-item>
            <el-form-item label="站点描述">
              <el-input v-model="basicForm.siteDesc" type="textarea" :rows="3" placeholder="站点描述" />
            </el-form-item>
            <el-form-item label="开放注册">
              <el-switch v-model="basicForm.openRegister" />
            </el-form-item>
            <el-form-item label="默认主题">
              <el-select v-model="basicForm.defaultTheme">
                <el-option value="dark" label="暗色" />
                <el-option value="light" label="亮色" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="saveBasicSettings">保存设置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-tab-pane>

      <el-tab-pane label="邮件设置" name="mail">
        <div class="glass-card" style="padding: 24px">
          <el-form :model="mailForm" label-width="120px">
            <el-form-item label="SMTP服务器">
              <el-input v-model="mailForm.smtpHost" placeholder="smtp.example.com" />
            </el-form-item>
            <el-form-item label="SMTP端口">
              <el-input v-model="mailForm.smtpPort" placeholder="465" />
            </el-form-item>
            <el-form-item label="发件邮箱">
              <el-input v-model="mailForm.smtpUser" placeholder="noreply@example.com" />
            </el-form-item>
            <el-form-item label="邮箱密码">
              <el-input v-model="mailForm.smtpPass" type="password" show-password />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="saveMailSettings">保存设置</el-button>
              <el-button @click="testMail">发送测试邮件</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-tab-pane>

      <el-tab-pane label="系统维护" name="system">
        <div class="glass-card" style="padding: 24px">
          <el-form label-width="120px">
            <el-form-item label="刷新缓存">
              <el-button type="warning" @click="handleRefreshCache">刷新缓存</el-button>
            </el-form-item>
            <el-form-item label="修复数据">
              <el-button type="danger" @click="handleRepair">修复数据</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { saveSetting, getSetting, refreshCache, mailTest } from '@/api/setting'
import { repair } from '@/api/admin'
import { ElMessage, ElMessageBox } from 'element-plus'

const activeTab = ref('basic')

const basicForm = reactive({
  siteName: '',
  siteDesc: '',
  openRegister: true,
  defaultTheme: 'dark'
})

const mailForm = reactive({
  smtpHost: '',
  smtpPort: '',
  smtpUser: '',
  smtpPass: ''
})

async function loadSettings() {
  try {
    const res = await getSetting({})
    if (res.data) {
      Object.assign(basicForm, res.data)
    }
  } catch {
    // error handled by interceptor
  }
}

async function saveBasicSettings() {
  try {
    await saveSetting(basicForm)
    ElMessage.success('保存成功')
  } catch {
    // error handled by interceptor
  }
}

async function saveMailSettings() {
  try {
    await saveSetting(mailForm)
    ElMessage.success('保存成功')
  } catch {
    // error handled by interceptor
  }
}

async function testMail() {
  try {
    const { value: email } = await ElMessageBox.prompt('请输入收件邮箱', '测试邮件', {
      inputPattern: /@/,
      inputErrorMessage: '请输入有效的邮箱地址'
    })
    await mailTest({ email })
    ElMessage.success('测试邮件已发送')
  } catch {
    // cancelled or error
  }
}

async function handleRefreshCache() {
  try {
    await ElMessageBox.confirm('确定刷新缓存？', '提示', { type: 'warning' })
    await refreshCache()
    ElMessage.success('缓存已刷新')
  } catch {
    // cancelled or error
  }
}

async function handleRepair() {
  try {
    await ElMessageBox.confirm('确定修复数据？此操作可能需要一些时间。', '提示', { type: 'warning' })
    await repair()
    ElMessage.success('修复完成')
  } catch {
    // cancelled or error
  }
}

onMounted(() => {
  loadSettings()
})
</script>

<style lang="scss" scoped>
.settings-page {
  .page-header {
    margin-bottom: 20px;

    h2 {
      font-size: 20px;
      font-weight: 600;
      color: var(--text-primary);
    }
  }
}
</style>
