import request from '../request'
import { getToken } from '../request/token'


//分享文件
export function shareFile(shareDays,fileId) {
  const data = {
    shareDays,
		fileId
  }
  return request({
  	headers: {'heart-token': getToken()},
    url: '/file/share',
    method: 'post',
    data
  })
}

//获取分享文件列表
export function shareFileList() {
  return request({
    headers: {'heart-token': getToken()},
    url: '/file/share/list',
    method: 'get'
  })
}

//取消文件分享
export function cancelShareFile(Id) {
  return request({
    headers: {'heart-token': getToken()},
    url: '/file/share/undo/'+Id,
    method: 'post'
  })
}

//分享文件
export function saveShareFile(token,dirId) {
  const data = {
    token,
		dirId
  }
  return request({
  	headers: {'heart-token': getToken()},
    url: '/file/share/save',
    method: 'post',
    data
  })
}
