<template>
  <div class="dashboard-page">
    <el-row :gutter="20">
      <el-col :span="6" :xs="12">
        <div class="stat-card glass-card">
          <div class="stat-card__icon" style="background: linear-gradient(135deg, #409eff, #667eea)">
            <el-icon :size="28"><User /></el-icon>
          </div>
          <div class="stat-card__info">
            <div class="stat-card__value">{{ stats.userCount || 0 }}</div>
            <div class="stat-card__label">用户总数</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6" :xs="12">
        <div class="stat-card glass-card">
          <div class="stat-card__icon" style="background: linear-gradient(135deg, #67c23a, #85ce61)">
            <el-icon :size="28"><Link /></el-icon>
          </div>
          <div class="stat-card__info">
            <div class="stat-card__value">{{ stats.linkCount || 0 }}</div>
            <div class="stat-card__label">书签数量</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6" :xs="12">
        <div class="stat-card glass-card">
          <div class="stat-card__icon" style="background: linear-gradient(135deg, #e6a23c, #f5c942)">
            <el-icon :size="28"><Document /></el-icon>
          </div>
          <div class="stat-card__info">
            <div class="stat-card__value">{{ stats.noteCount || 0 }}</div>
            <div class="stat-card__label">笔记数量</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6" :xs="12">
        <div class="stat-card glass-card">
          <div class="stat-card__icon" style="background: linear-gradient(135deg, #f56c6c, #f89898)">
            <el-icon :size="28"><Picture /></el-icon>
          </div>
          <div class="stat-card__info">
            <div class="stat-card__value">{{ stats.wallpaperCount || 0 }}</div>
            <div class="stat-card__label">壁纸数量</div>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12" :xs="24">
        <div class="glass-card" style="padding: 20px">
          <h3 style="margin-bottom: 16px; color: var(--text-primary)">系统状态</h3>
          <div v-if="servicesStatus">
            <div v-for="(status, key) in servicesStatus" :key="key" class="service-item">
              <span>{{ key }}</span>
              <el-tag :type="status ? 'success' : 'danger'" size="small">
                {{ status ? '正常' : '异常' }}
              </el-tag>
            </div>
          </div>
          <el-empty v-else description="暂无状态数据" />
        </div>
      </el-col>
      <el-col :span="12" :xs="24">
        <div class="glass-card" style="padding: 20px">
          <h3 style="margin-bottom: 16px; color: var(--text-primary)">热门标签页</h3>
          <div v-if="hotTabs && hotTabs.length">
            <div v-for="(tab, index) in hotTabs" :key="index" class="hot-item">
              <span class="hot-item__rank">{{ index + 1 }}</span>
              <span class="hot-item__name">{{ tab.name || tab.title }}</span>
              <span class="hot-item__count">{{ tab.count || 0 }}</span>
            </div>
          </div>
          <el-empty v-else description="暂无数据" />
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { User, Link, Document, Picture } from '@element-plus/icons-vue'
import { getServicesStatus, getHotTab } from '@/api/admin'

const stats = ref({})
const servicesStatus = ref(null)
const hotTabs = ref([])

onMounted(async () => {
  try {
    const [statusRes, hotRes] = await Promise.allSettled([
      getServicesStatus(),
      getHotTab()
    ])
    if (statusRes.status === 'fulfilled') {
      servicesStatus.value = statusRes.value.data
    }
    if (hotRes.status === 'fulfilled') {
      hotTabs.value = hotRes.value.data || []
    }
  } catch {
    // ignore
  }
})
</script>

<style lang="scss" scoped>
.dashboard-page {
  .stat-card {
    display: flex;
    align-items: center;
    gap: 16px;
    padding: 20px;

    &__icon {
      width: 56px;
      height: 56px;
      border-radius: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      color: #fff;
      flex-shrink: 0;
    }

    &__value {
      font-size: 28px;
      font-weight: 700;
      color: var(--text-primary);
      line-height: 1;
    }

    &__label {
      font-size: 13px;
      color: var(--text-secondary);
      margin-top: 4px;
    }
  }

  .service-item {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 10px 0;
    border-bottom: 1px solid var(--border-color);
    color: var(--text-primary);
    font-size: 14px;
  }

  .hot-item {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 8px 0;
    border-bottom: 1px solid var(--border-color);

    &__rank {
      width: 24px;
      height: 24px;
      border-radius: 6px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 12px;
      font-weight: 600;
      background: var(--bg-card);
      color: var(--text-primary);
    }

    &:nth-child(1) &__rank {
      background: linear-gradient(135deg, #f56c6c, #e6a23c);
      color: #fff;
    }

    &:nth-child(2) &__rank {
      background: linear-gradient(135deg, #e6a23c, #f5c942);
      color: #fff;
    }

    &:nth-child(3) &__rank {
      background: linear-gradient(135deg, #67c23a, #85ce61);
      color: #fff;
    }

    &__name {
      flex: 1;
      font-size: 14px;
      color: var(--text-primary);
    }

    &__count {
      font-size: 13px;
      color: var(--text-secondary);
    }
  }
}
</style>
