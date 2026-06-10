import request from './index'

export function editFolder(data) {
  return request.post('/wallpaper/editFolder', data)
}

export function delFolder(id) {
  return request.post('/wallpaper/delFolder', { id })
}

export function getFolder() {
  return request.get('/wallpaper/getFolder')
}

export function getFolderClient() {
  return request.get('/wallpaper/getFolderClient')
}

export function getFolderWallpaper(data) {
  return request.post('/wallpaper/getFolderWallpaper', data)
}

export function getFolderWallpaperClient(data) {
  return request.post('/wallpaper/getFolderWallpaperClient', data)
}

export function deleteWallpaper(id) {
  return request.post('/wallpaper/deleteWallpaper', { id })
}

export function addWallpaper(data) {
  return request.post('/wallpaper/addWallpaper', data)
}

export function randomWallpaper() {
  return request.get('/wallpaper/randomWallpaper')
}

export function sortFolder(data) {
  return request.post('/wallpaper/sortFolder', data)
}
