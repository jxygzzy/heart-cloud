<template>
  <div class="error-page">
      <div class="error-code">4<span>0</span>4</div>
      <div class="error-desc">啊哦~ 你所访问的页面走丢了</div>
      <div class="error-handle">
          <router-link to="/">
            <el-button class="error-btn" type="primary" size="large">返回首页</el-button>
          </router-link>
          <el-button class="error-btn" type="primary" size="large" @click="goBack">返回上一页</el-button>
      </div>
  </div>
</template>

<script>
export default {
	name: 'NotFound',
  methods: {
      goBack(){
          this.$router.go(-1);
      }
  }
}
</script>

<style>
.error-handle{
	margin: 10px;
}
.error-btn{
	margin: 10px 10px 10px 10px;
}
.error-code{
	font-size: 70px;
	color: aquamarine;
	margin-top: 100px;
	margin-bottom: 50px;
}
.error-desc{
	color: red;
}
</style>