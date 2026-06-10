<template>
  <div class="bookmark-group">
    <div class="bookmark-group__header">
      <div class="bookmark-group__title">
        <el-icon v-if="group.icon" class="bookmark-group__icon"><component :is="group.icon" /></el-icon>
        <span>{{ group.title }}</span>
      </div>
      <div class="bookmark-group__actions">
        <el-button :icon="Plus" circle size="small" @click="$emit('add', group)" />
        <el-button :icon="Setting" circle size="small" @click="$emit('edit', group)" />
      </div>
    </div>
    <div class="bookmark-group__list">
      <BookmarkCard
        v-for="item in group.links"
        :key="item.id"
        :bookmark="item"
        @click="$emit('itemClick', item)"
        @edit="$emit('itemEdit', item)"
        @delete="$emit('itemDelete', item)"
      />
      <div v-if="!group.links || group.links.length === 0" class="bookmark-group__empty">
        暂无书签，点击 + 添加
      </div>
    </div>
  </div>
</template>

<script setup>
import { Plus, Setting } from '@element-plus/icons-vue'
import BookmarkCard from './BookmarkCard.vue'

defineProps({
  group: {
    type: Object,
    required: true
  }
})

defineEmits(['add', 'edit', 'itemClick', 'itemEdit', 'itemDelete'])
</script>

<style lang="scss" scoped>
.bookmark-group {
  background: var(--bg-card);
  backdrop-filter: $glass-blur;
  -webkit-backdrop-filter: $glass-blur;
  border: 1px solid var(--border-color);
  border-radius: $border-radius-lg;
  padding: $spacing-md;
  transition: all $transition-normal;

  &:hover {
    box-shadow: var(--shadow);
  }

  &__header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: $spacing-md;
    padding-bottom: $spacing-sm;
    border-bottom: 1px solid var(--border-color);
  }

  &__title {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: $font-size-lg;
    font-weight: 600;
    color: var(--text-primary);
  }

  &__icon {
    color: $primary-color;
  }

  &__actions {
    display: flex;
    gap: 4px;
    opacity: 0;
    transition: opacity $transition-fast;

    .el-button {
      background: transparent;
      border: none;
      color: var(--text-secondary);

      &:hover {
        color: $primary-color;
        background: var(--bg-card-hover);
      }
    }
  }

  &:hover &__actions {
    opacity: 1;
  }

  &__list {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
    gap: $spacing-sm;
  }

  &__empty {
    grid-column: 1 / -1;
    text-align: center;
    padding: $spacing-xl;
    color: var(--text-secondary);
    font-size: $font-size-sm;
  }
}
</style>
