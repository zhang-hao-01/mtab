<template>
  <div class="todo-card glass-card">
    <div class="todo-card__header">
      <el-icon><List /></el-icon>
      <span>待办事项</span>
    </div>
    <div class="todo-card__list">
      <div
        v-for="(item, index) in todos"
        :key="index"
        class="todo-card__item"
        :class="{ done: item.done }"
      >
        <el-checkbox v-model="item.done" @change="saveTodos" />
        <span class="todo-card__text">{{ item.text }}</span>
        <el-icon class="todo-card__del" @click="removeTodo(index)"><Close /></el-icon>
      </div>
      <div v-if="todos.length === 0" class="todo-card__empty">暂无待办事项</div>
    </div>
    <div class="todo-card__add">
      <el-input
        v-model="newTodo"
        placeholder="添加待办事项..."
        size="small"
        @keydown.enter="addTodo"
      >
        <template #append>
          <el-button :icon="Plus" @click="addTodo" />
        </template>
      </el-input>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { List, Close, Plus } from '@element-plus/icons-vue'

const todos = ref([])
const newTodo = ref('')

const STORAGE_KEY = 'mtab_todos'

function loadTodos() {
  const data = localStorage.getItem(STORAGE_KEY)
  if (data) {
    try {
      todos.value = JSON.parse(data)
    } catch {
      todos.value = []
    }
  }
}

function saveTodos() {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(todos.value))
}

function addTodo() {
  if (!newTodo.value.trim()) return
  todos.value.push({ text: newTodo.value.trim(), done: false })
  newTodo.value = ''
  saveTodos()
}

function removeTodo(index) {
  todos.value.splice(index, 1)
  saveTodos()
}

onMounted(() => {
  loadTodos()
})
</script>

<style lang="scss" scoped>
.todo-card {
  padding: $spacing-md;

  &__header {
    display: flex;
    align-items: center;
    gap: 8px;
    margin-bottom: $spacing-md;
    color: $warning-color;
    font-weight: 600;
  }

  &__list {
    max-height: 200px;
    overflow-y: auto;
    margin-bottom: $spacing-md;
  }

  &__item {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 6px 0;
    border-bottom: 1px solid var(--border-color);

    &:last-child {
      border-bottom: none;
    }

    &.done .todo-card__text {
      text-decoration: line-through;
      color: var(--text-secondary);
    }
  }

  &__text {
    flex: 1;
    font-size: $font-size-sm;
    color: var(--text-primary);
  }

  &__del {
    cursor: pointer;
    color: var(--text-secondary);
    opacity: 0;
    transition: opacity $transition-fast;

    &:hover {
      color: $danger-color;
    }
  }

  &__item:hover &__del {
    opacity: 1;
  }

  &__empty {
    text-align: center;
    padding: $spacing-lg;
    color: var(--text-secondary);
    font-size: $font-size-sm;
  }

  &__add {
    :deep(.el-input-group__append) {
      padding: 0;
    }
  }
}
</style>
