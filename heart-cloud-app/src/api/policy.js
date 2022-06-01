import request from "../request";
import {getToken} from "../request/token";

export function policy() {
    return request({
        headers: {'heart-token': getToken()},
        url: '/file/policy',
        method: 'get'
    })
}

export function check(key) {
    return request({
        headers: {'heart-token': getToken()},
        url: '/file/check/' + key,
        method: 'get'
    })
}
