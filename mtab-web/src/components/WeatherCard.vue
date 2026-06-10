<template>
  <div class="weather-card glass-card">
    <div class="weather-card__header">
      <el-icon><Sunny /></el-icon>
      <span>天气</span>
    </div>
    <div v-if="weather" class="weather-card__content">
      <div class="weather-card__temp">{{ weather.temperature }}°</div>
      <div class="weather-card__desc">{{ weather.description }}</div>
      <div class="weather-card__location">{{ weather.city }}</div>
    </div>
    <div v-else class="weather-card__empty">
      <span>天气数据加载中...</span>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Sunny } from '@element-plus/icons-vue'

const weather = ref(null)

onMounted(() => {
  // 天气数据需要后端接口支持，此处为占位
  // 实际使用时通过 API 获取
})
</script>

<style lang="scss" scoped>
.weather-card {
  padding: $spacing-md;
  text-align: center;

  &__header {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
    margin-bottom: $spacing-md;
    color: $warning-color;
    font-weight: 600;
  }

  &__content {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  &__temp {
    font-size: 42px;
    font-weight: 300;
    color: var(--text-primary);
    line-height: 1;
  }

  &__desc {
    font-size: $font-size-md;
    color: var(--text-secondary);
    margin-top: $spacing-xs;
  }

  &__location {
    font-size: $font-size-sm;
    color: var(--text-secondary);
    margin-top: $spacing-xs;
  }

  &__empty {
    padding: $spacing-lg;
    color: var(--text-secondary);
    font-size: $font-size-sm;
  }
}
</style>
