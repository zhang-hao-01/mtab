import { defineStore } from 'pinia'

export const useSettingStore = defineStore('setting', {
  state: () => ({
    theme: localStorage.getItem('mtab_theme') || 'dark',
    sidebarCollapsed: false,
    showSidePanel: false,
    sidePanelType: '',
    sidePanelData: null
  }),

  getters: {
    isDark: (state) => state.theme === 'dark'
  },

  actions: {
    toggleTheme() {
      this.theme = this.theme === 'dark' ? 'light' : 'dark'
      localStorage.setItem('mtab_theme', this.theme)
      document.documentElement.setAttribute('data-theme', this.theme)
    },

    setTheme(theme) {
      this.theme = theme
      localStorage.setItem('mtab_theme', theme)
      document.documentElement.setAttribute('data-theme', theme)
    },

    toggleSidebar() {
      this.sidebarCollapsed = !this.sidebarCollapsed
    },

    openSidePanel(type, data = null) {
      this.showSidePanel = true
      this.sidePanelType = type
      this.sidePanelData = data
    },

    closeSidePanel() {
      this.showSidePanel = false
      this.sidePanelType = ''
      this.sidePanelData = null
    }
  }
})
