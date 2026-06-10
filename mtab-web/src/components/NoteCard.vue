<template>
  <div class="note-card glass-card" @click="$emit('click', note)">
    <div class="note-card__header">
      <el-icon><Document /></el-icon>
      <span class="note-card__title">{{ note.title || '未命名笔记' }}</span>
    </div>
    <div class="note-card__content">{{ note.text || '暂无内容' }}</div>
    <div class="note-card__footer">
      <span class="note-card__time">{{ formatTime(note.updateTime) }}</span>
    </div>
  </div>
</template>

<script setup>
import { Document } from '@element-plus/icons-vue'
import { formatDate } from '@/utils/common'

defineProps({
  note: {
    type: Object,
    required: true
  }
})

defineEmits(['click'])

function formatTime(time) {
  return formatDate(time, 'MM-dd HH:mm')
}
</script>

<style lang="scss" scoped>
.note-card {
  padding: $spacing-md;
  cursor: pointer;

  &__header {
    display: flex;
    align-items: center;
    gap: 8px;
    margin-bottom: $spacing-sm;
    color: $primary-color;
  }

  &__title {
    font-size: $font-size-md;
    font-weight: 600;
    color: var(--text-primary);
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  &__content {
    font-size: $font-size-sm;
    color: var(--text-secondary);
    line-height: 1.6;
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    overflow: hidden;
    margin-bottom: $spacing-sm;
  }

  &__footer {
    display: flex;
    justify-content: flex-end;
  }

  &__time {
    font-size: $font-size-xs;
    color: var(--text-secondary);
  }
}
</style>
