<template>
  <div class="bookmark-card" @click="handleClick" @contextmenu.prevent="handleRightClick">
    <div class="bookmark-card__icon">
      <img v-if="bookmark.icon" :src="bookmark.icon" :alt="bookmark.title" @error="onIconError" />
      <div v-else class="bookmark-card__icon-placeholder">
        {{ bookmark.title?.charAt(0)?.toUpperCase() || '?' }}
      </div>
    </div>
    <div class="bookmark-card__info">
      <div class="bookmark-card__title" :title="bookmark.title">{{ bookmark.title }}</div>
      <div class="bookmark-card__url" :title="bookmark.url">{{ displayUrl }}</div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  bookmark: {
    type: Object,
    required: true
  }
})

const emit = defineEmits(['click', 'edit', 'delete'])

const displayUrl = computed(() => {
  try {
    const u = new URL(props.bookmark.url)
    return u.hostname
  } catch {
    return props.bookmark.url || ''
  }
})

function handleClick() {
  if (props.bookmark.url) {
    window.open(props.bookmark.url, '_blank')
  }
  emit('click', props.bookmark)
}

function handleRightClick() {
  emit('edit', props.bookmark)
}

function onIconError(e) {
  e.target.style.display = 'none'
}
</script>

<style lang="scss" scoped>
.bookmark-card {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 10px 14px;
  border-radius: $border-radius-md;
  cursor: pointer;
  transition: all $transition-normal;
  background: var(--bg-card);
  backdrop-filter: $glass-blur;
  -webkit-backdrop-filter: $glass-blur;
  border: 1px solid var(--border-color);

  &:hover {
    background: var(--bg-card-hover);
    transform: translateY(-2px);
    box-shadow: var(--shadow);

    .bookmark-card__icon img {
      transform: scale(1.1);
    }
  }

  &__icon {
    width: 36px;
    height: 36px;
    border-radius: $border-radius-sm;
    overflow: hidden;
    flex-shrink: 0;
    display: flex;
    align-items: center;
    justify-content: center;

    img {
      width: 100%;
      height: 100%;
      object-fit: contain;
      transition: transform $transition-normal;
    }
  }

  &__icon-placeholder {
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    background: linear-gradient(135deg, $primary-color, #667eea);
    color: #fff;
    font-size: $font-size-lg;
    font-weight: 600;
    border-radius: $border-radius-sm;
  }

  &__info {
    flex: 1;
    min-width: 0;
  }

  &__title {
    font-size: $font-size-md;
    color: var(--text-primary);
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    line-height: 1.4;
  }

  &__url {
    font-size: $font-size-xs;
    color: var(--text-secondary);
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    line-height: 1.4;
    margin-top: 2px;
  }
}
</style>
