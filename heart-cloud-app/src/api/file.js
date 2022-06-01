import request from '../request'
import { getToken } from '../request/token'


//获取用户信息
export function getDirFile(dirId) {
  return request({
    headers: {'heart-token': getToken()},
    url: '/file/list?dirId='+dirId,
    method: 'get'
  })
}

//新建目录
export function createFolder(name,parentId) {
  const data = {
    name,
    parentId
  }
  return request({
		headers: {'heart-token': getToken()},
    url: '/file/dir',
    method: 'post',
    data
  })
}

//删除目录
export function delFolder(dirId) {
  return request({
    headers: {'heart-token': getToken()},
    url: '/file/dir/'+dirId,
    method: 'delete'
  })
}

//下载文件
export function getFileUrl(id) {
  return request({
    headers: {'heart-token': getToken()},
    url: '/file/url/'+id,
    method: 'get'
  })
}

//删除文件
export function delFile(dirId) {
  return request({
    headers: {'heart-token': getToken()},
    url: '/file/'+dirId,
    method: 'delete'
  })
}

//移动文件
export function fileMove(fileId,dirId) {
  const data = {
    fileId,
    dirId
  }
  return request({
		headers: {'heart-token': getToken()},
    url: '/file/move',
    method: 'post',
    data
  })
}