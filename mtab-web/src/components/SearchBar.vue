<template>
  <div class="search-bar">
    <div class="search-bar__wrapper">
      <div class="search-bar__engine" @click="showEnginePicker = !showEnginePicker">
        <img v-if="currentEngine?.icon" :src="currentEngine.icon" :alt="currentEngine.name" />
        <span v-else>{{ currentEngine?.name || '搜索' }}</span>
        <el-icon><ArrowDown /></el-icon>
      </div>
      <input
        ref="inputRef"
        v-model="keyword"
        class="search-bar__input"
        :placeholder="`在 ${currentEngine?.name || '搜索引擎'} 中搜索...`"
        @keydown.enter="handleSearch"
        @focus="isFocused = true"
        @blur="isFocused = false"
      />
      <el-button class="search-bar__btn" :icon="Search" circle @click="handleSearch" />
    </div>
    <!-- 搜索引擎选择器 -->
    <Transition name="fade">
      <div v-if="showEnginePicker" class="search-bar__engine-picker">
        <div
          v-for="engine in searchEngines"
          :key="engine.id"
          class="search-bar__engine-item"
          :class="{ active: currentEngine?.id === engine.id }"
          @click="selectEngine(engine)"
        >
          <img v-if="engine.icon" :src="engine.icon" :alt="engine.name" />
          <span>{{ engine.name }}</span>
        </div>
      </div>
    </Transition>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { Search, ArrowDown } from '@element-plus/icons-vue'
import { useAppStore } from '@/stores/app'

const appStore = useAppStore()

const keyword = ref('')
const isFocused = ref(false)
const showEnginePicker = ref(false)
const inputRef = ref(null)
const selectedEngineId = ref(null)

const searchEngines = computed(() => appStore.searchEngines)

const currentEngine = computed(() => {
  if (selectedEngineId.value) {
    return searchEngines.value.find(e => e.id === selectedEngineId.value)
  }
  return searchEngines.value[0] || null
})

function handleSearch() {
  if (!keyword.value.trim()) return
  const engine = currentEngine.value
  if (engine?.url) {
    const url = engine.url.replace('%s', encodeURIComponent(keyword.value.trim()))
    window.open(url, '_blank')
  }
}

function selectEngine(engine) {
  selectedEngineId.value = engine.id
  showEnginePicker.value = false
  localStorage.setItem('mtab_search_engine', engine.id)
}

onMounted(() => {
  const savedEngineId = localStorage.getItem('mtab_search_engine')
  if (savedEngineId) {
    selectedEngineId.value = Number(savedEngineId)
  }
})
</script>

<style lang="scss" scoped>
.search-bar {
  position: relative;
  width: 100%;
  max-width: 680px;
  margin: 0 auto;

  &__wrapper {
    display: flex;
    align-items: center;
    background: var(--bg-card);
    backdrop-filter: $glass-blur;
    -webkit-backdrop-filter: $glass-blur;
    border: 1px solid var(--border-color);
    border-radius: 50px;
    padding: 4px 6px 4px 4px;
    transition: all $transition-normal;

    &:focus-within {
      border-color: $primary-color;
      box-shadow: 0 0 0 3px rgba(64, 158, 255, 0.15);
    }
  }

  &__engine {
    display: flex;
    align-items: center;
    gap: 4px;
    padding: 8px 12px;
    cursor: pointer;
    border-radius: 50px;
    transition: background $transition-fast;
    white-space: nowrap;
    color: var(--text-primary);
    font-size: $font-size-sm;

    &:hover {
      background: var(--bg-card-hover);
    }

    img {
      width: 20px;
      height: 20px;
      border-radius: 4px;
    }

    .el-icon {
      font-size: 12px;
      color: var(--text-secondary);
    }
  }

  &__input {
    flex: 1;
    border: none;
    outline: none;
    background: transparent;
    color: var(--text-primary);
    font-size: $font-size-md;
    padding: 8px 12px;
    min-width: 0;

    &::placeholder {
      color: var(--text-secondary);
    }
  }

  &__btn {
    background: $primary-color;
    border: none;
    color: #fff;
    flex-shrink: 0;

    &:hover {
      background: #5aabff;
    }
  }

  &__engine-picker {
    position: absolute;
    top: calc(100% + 8px);
    left: 0;
    right: 0;
    background: var(--bg-secondary);
    backdrop-filter: $glass-blur;
    -webkit-backdrop-filter: $glass-blur;
    border: 1px solid var(--border-color);
    border-radius: $border-radius-md;
    padding: $spacing-sm;
    z-index: 100;
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
    gap: $spacing-xs;
    box-shadow: var(--shadow);
  }

  &__engine-item {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 8px 12px;
    border-radius: $border-radius-sm;
    cursor: pointer;
    transition: background $transition-fast;
    color: var(--text-primary);
    font-size: $font-size-sm;

    &:hover {
      background: var(--bg-card-hover);
    }

    &.active {
      background: rgba(64, 158, 255, 0.15);
      color: $primary-color;
    }

    img {
      width: 18px;
      height: 18px;
      border-radius: 3px;
    }
  }
}
</style>
