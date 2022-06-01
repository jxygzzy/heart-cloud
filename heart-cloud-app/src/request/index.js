import axios from 'axios'
import {Message} from 'element-ui'
import {getToken} from '../request/token'

//创建全局的axios对象
const service = axios.create({
  baseURL: '/api',
  timeout: 10000
})


export default service
