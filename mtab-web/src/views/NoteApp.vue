<template>
  <div class="note-app">
    <div class="note-app__header">
      <el-button :icon="ArrowLeft" text @click="$router.push('/')">返回</el-button>
      <h2>我的笔记</h2>
      <el-button type="primary" :icon="Plus" @click="handleAdd">新建笔记</el-button>
    </div>

    <div class="note-app__content">
      <div class="note-app__sidebar">
        <div
          v-for="note in notes"
          :key="note.id"
          class="note-app__item"
          :class="{ active: currentNote?.id === note.id }"
          @click="selectNote(note)"
        >
          <div class="note-app__item-title">{{ note.title || '未命名笔记' }}</div>
          <div class="note-app__item-time">{{ formatTime(note.updateTime) }}</div>
        </div>
        <div v-if="notes.length === 0" class="note-app__empty">
          暂无笔记，点击新建开始
        </div>
      </div>

      <div class="note-app__editor">
        <div v-if="currentNote" class="note-app__editor-inner">
          <el-input
            v-model="currentNote.title"
            placeholder="笔记标题"
            class="note-app__editor-title"
            size="large"
          />
          <el-input
            v-model="currentNote.text"
            type="textarea"
            placeholder="开始书写..."
            :autosize="{ minRows: 15 }"
            class="note-app__editor-text"
          />
          <div class="note-app__editor-actions">
            <el-button type="primary" @click="handleSave">保存</el-button>
            <el-button type="danger" @click="handleDelete">删除</el-button>
          </div>
        </div>
        <div v-else class="note-app__no-select">
          <el-icon :size="48"><Document /></el-icon>
          <p>选择或创建一个笔记</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ArrowLeft, Plus, Document } from '@element-plus/icons-vue'
import { get as getNotes, add as addNote, update as updateNote, del as deleteNote } from '@/api/note'
import { formatDate } from '@/utils/common'
import { ElMessage, ElMessageBox } from 'element-plus'

const notes = ref([])
const currentNote = ref(null)

function formatTime(time) {
  return formatDate(time, 'MM-dd HH:mm')
}

async function fetchNotes() {
  try {
    const res = await getNotes()
    notes.value = res.data || []
  } catch {
    // error handled by interceptor
  }
}

async function selectNote(note) {
  currentNote.value = { ...note }
}

async function handleAdd() {
  try {
    await addNote({ title: '新建笔记', text: '' })
    await fetchNotes()
    if (notes.value.length > 0) {
      currentNote.value = { ...notes.value[0] }
    }
    ElMessage.success('创建成功')
  } catch {
    // error handled by interceptor
  }
}

async function handleSave() {
  if (!currentNote.value) return
  try {
    await updateNote(currentNote.value)
    await fetchNotes()
    ElMessage.success('保存成功')
  } catch {
    // error handled by interceptor
  }
}

async function handleDelete() {
  if (!currentNote.value) return
  try {
    await ElMessageBox.confirm('确定删除该笔记？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await deleteNote(currentNote.value.id)
    currentNote.value = null
    await fetchNotes()
    ElMessage.success('删除成功')
  } catch {
    // cancelled or error
  }
}

onMounted(() => {
  fetchNotes()
})
</script>

<style lang="scss" scoped>
.note-app {
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  background: var(--bg-color);
  color: var(--text-primary);

  &__header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: $spacing-md $spacing-lg;
    border-bottom: 1px solid var(--border-color);
    background: var(--bg-secondary);

    h2 {
      font-size: $font-size-xl;
      font-weight: 600;
    }
  }

  &__content {
    flex: 1;
    display: flex;
    overflow: hidden;
  }

  &__sidebar {
    width: 260px;
    border-right: 1px solid var(--border-color);
    overflow-y: auto;
    padding: $spacing-sm;
    background: var(--bg-secondary);
  }

  &__item {
    padding: $spacing-md;
    border-radius: $border-radius-sm;
    cursor: pointer;
    transition: background $transition-fast;
    margin-bottom: $spacing-xs;

    &:hover {
      background: var(--bg-card-hover);
    }

    &.active {
      background: rgba(64, 158, 255, 0.1);
      border-left: 3px solid $primary-color;
    }

    &-title {
      font-size: $font-size-md;
      font-weight: 500;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }

    &-time {
      font-size: $font-size-xs;
      color: var(--text-secondary);
      margin-top: 4px;
    }
  }

  &__empty {
    text-align: center;
    padding: $spacing-xl;
    color: var(--text-secondary);
    font-size: $font-size-sm;
  }

  &__editor {
    flex: 1;
    display: flex;
    flex-direction: column;
    overflow: hidden;

    &-inner {
      flex: 1;
      display: flex;
      flex-direction: column;
      padding: $spacing-lg;
      overflow-y: auto;
    }

    &-title {
      margin-bottom: $spacing-md;

      :deep(.el-input__wrapper) {
        font-size: $font-size-xl;
        font-weight: 600;
      }
    }

    &-text {
      flex: 1;

      :deep(.el-textarea__inner) {
        font-size: $font-size-md;
        line-height: 1.8;
        min-height: 400px !important;
      }
    }

    &-actions {
      display: flex;
      gap: $spacing-sm;
      margin-top: $spacing-md;
      padding-top: $spacing-md;
      border-top: 1px solid var(--border-color);
    }
  }

  &__no-select {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    color: var(--text-secondary);
    gap: $spacing-md;
  }
}

@media (max-width: $breakpoint-md) {
  .note-app {
    &__sidebar {
      width: 200px;
    }
  }
}
</style>
