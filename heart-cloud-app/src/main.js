import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import axios from 'axios'
import VueAxios from 'vue-axios'
import 'element-ui/lib/theme-chalk/index.css';




Vue.config.productionTip = false
Vue.use(VueAxios,axios) //使用axios
Vue.use(ElementUI); //使用elemnetui

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
