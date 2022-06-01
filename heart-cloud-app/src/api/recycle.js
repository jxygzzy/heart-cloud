import request from '../request'
import { getToken } from '../request/token'
//获取回收站列表
export function getReFile() {
  return request({
    headers: {'heart-token': getToken()},
    url: '/recycle/list',
    method: 'get'
  })
}
//彻底删除
export function Destroy(recycleId) {
  const data = {
    recycleId,
  }
  return request({
    headers: {'heart-token': getToken()},
    url: '/recycle/destroy',
    method: 'post',
		data
  })
}
//恢复文件
export function Recovery(recycleId) {
  const data = {
    recycleId,
  }
  return request({
    headers: {'heart-token': getToken()},
    url: '/recycle/recovery',
    method: 'post',
    data
  })
}
