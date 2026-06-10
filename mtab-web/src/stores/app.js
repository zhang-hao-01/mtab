import { defineStore } from 'pinia'
import { get as getLinks } from '@/api/link'
import { get as getConfig } from '@/api/config'
import { get as getTabbar } from '@/api/tabbar'
import { searchEngine as getSearchEngines } from '@/api/searchEngine'
import { index as getCards } from '@/api/card'
import { isLoggedIn } from '@/utils/auth'

export const useAppStore = defineStore('app', {
  state: () => ({
    links: [],
    config: {},
    tabbar: [],
    searchEngines: [],
    cards: [],
    siteInfo: {},
    wallpaper: '',
    loading: false
  }),

  actions: {
    async fetchAllData() {
      this.loading = true
      try {
        const promises = [
          this.fetchConfig(),
          this.fetchSearchEngines()
        ]
        if (isLoggedIn()) {
          promises.push(this.fetchLinks())
          promises.push(this.fetchTabbar())
          promises.push(this.fetchCards())
        }
        await Promise.all(promises)
      } catch (e) {
        console.error('获取数据失败', e)
      } finally {
        this.loading = false
      }
    },

    async fetchLinks() {
      const res = await getLinks()
      this.links = res.data || []
    },

    async fetchConfig() {
      const res = await getConfig()
      this.config = res.data || {}
      if (this.config.wallpaper) {
        this.wallpaper = this.config.wallpaper
      }
    },

    async fetchTabbar() {
      const res = await getTabbar()
      this.tabbar = res.data || []
    },

    async fetchSearchEngines() {
      const res = await getSearchEngines()
      this.searchEngines = res.data || []
    },

    async fetchCards() {
      const res = await getCards()
      this.cards = res.data || []
    },

    setWallpaper(url) {
      this.wallpaper = url
    }
  }
})
