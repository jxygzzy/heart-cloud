<template>
  <div class="login" >
    <div class="main">
      <div
        class="mainBox"
      >
				<div class="logoContainer">
					<div class="logo"><img src="../assets/logo.png" alt="" /></div>
					<div class="name">心跳企业云盘</div>
				</div>
        <el-tabs
          v-model="activeName"
          type="card"
          stretch
        >
          <el-tab-pane label="登录" name="first">
            <div class="loginInput">
              <el-form ref="form" :model="login" label-width="80px">
                <el-form-item>
                  <el-input
                    v-model="login.username"
                    placeholder="请输入账号"
                  ></el-input>
                </el-form-item>
                <el-form-item>
                  <el-input
                    v-model="login.password"
                    type="password"
                    placeholder="请输入密码"
                  ></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="onSubmit">登录</el-button>
                </el-form-item>
              </el-form>
            </div>
          </el-tab-pane>
          <el-tab-pane label="注册" name="second">
            <div class="registeredInput">
              <el-form ref="form" :model="login" label-width="80px">
                <el-form-item>
                  <el-input
                    v-model="registered.username"
                    placeholder="请输入账号"
                  ></el-input>
                </el-form-item>
                <el-form-item>
                  <el-input
                    v-model="registered.password"
                    placeholder="请输入密码"
                    type="password"
                  ></el-input>
                </el-form-item>
                <el-form-item>
                  <el-input
                      v-model="registered.name"
                      placeholder="请输入昵称"
                  ></el-input>
                </el-form-item>
                <el-form-item>
                  <el-input
                    v-model="registered.email"
                    placeholder="请输入邮箱"
                  ></el-input>
                </el-form-item>
                <el-form-item class="codeContainer">
                  <el-input
                    v-model="registered.tel"
                    placeholder="请输入电话"
                  ></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="onRegistered"
                    >注册</el-button
                  >
                </el-form-item>
              </el-form>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>


<script>

import {login,register} from '@/api/login'
import {setToken} from '@/request/token'

export default {
	name: 'Login',
	data() {
	  return {
			activeName:'first',
			login: {
				username: "",
				password: "",
			},
			registered: {
				username: "",
				password: "",
				name: "",
				email: "",
				tel: null,
			},
	  }
	},
	methods: {
		//登录方法
	  onSubmit() {
			let that = this
			
				//发起登录请求
			login(that.login.username,that.login.password).then(res => {
				setToken(res.data.data.tokenValue)
				this.$router.push({ path: '/'})
				this.$message({type: 'success', message: '登录成功!', showClose: true})
			}).catch((error) => {
				if (error !== 'error') {
				this.$message({type: 'error', message: '登录失败！请检查用户名或密码是否错误！', showClose: true})
				}
			})
	  },
		//注册
		onRegistered(){
			let that = this
				//发起登录请求
			register(that.registered.username,that.registered.password,that.registered.name,that.registered.email,that.registered.tel).then(res => {
				this.$message({type: 'success', message: res.data.msg, showClose: true})
			}).catch((error) => {
				if (error !== 'error') {
				this.$message({type: 'error', message: '注册失败！请检查参数是否填写错误！', showClose: true})
				}
			})
		}
	}
}
</script>


<style scoped>
	
	.login {
	  background-image: url("../assets/login.jpg");
	  background-repeat: no-repeat;
		background-size:cover;
		height: 100vh;
	}
	
	.logoContainer {
	  display: flex;
	  align-items: center;
		padding-top: 10px;
	  margin-bottom: 10px;
	  justify-content: center;
	}
	
	.logo {
	  width: 50px;
	}
	
	.logo img {
	  width: 100%;
	}
	
	.name {
	  color: #25262b;
	  font-size: 20px;
	  letter-spacing: 4px;
	  font-weight: bold;
	  margin-left: 7px;
	}
	
	.main {
	  width: 350px;
	  height: 400px;
	  position: absolute;
	  right: 10vw;
	  top: 15vh;
	}
	
	.mainBox {
	  width: 350px;
	  background-color: #fff;
	  height: auto;
	  border-radius: 10px;
	}

	
	.el-form /deep/ .el-form-item__content {
	  margin: 0 !important;
	  padding: 0 20px;
	}
	
	.el-input /deep/ input {
	  border-radius: 10px;
	}
	
	.loginInput {
	  margin-top: 20px;
	}
	
	.el-tabs /deep/ .is-active,
	.el-tabs /deep/ div:hover {
	  color: #595bb3;
	}
	
	.el-tabs /deep/ .is-active {
	  background-color: #fff;
	}
	
	.el-tabs /deep/ .el-tabs__item {
	  border: none !important;
	  font-size: 18px;
	  height: 50px;
	  line-height: 50px;
	}
	
	.el-tabs /deep/ .el-tabs__nav {
	  border: none;
	}
	
	.el-tabs /deep/ .el-tabs__nav-scroll {
	  background-color: #f5f5f6;
	}
	
	.el-input /deep/ .el-input__inner {
	  height: 48px;
	  font-size: 15px;
	}
	
	.el-button {
	  width: 100%;
	  background-color: #4a4bf3;
	  border: none;
	  border-radius: 10px;
	  margin-top: 10px;
	  height: 45px;
	  font-size: 15px;
	}
	
	.el-button:hover {
	  background-color: #0e12cb;
	}
	
	.codeContainer {
	  position: relative;
	}
	
	.codeButtonContainer {
	  position: absolute;
	  top: 50%;
	  right: 30px;
	  transform: translateY(-50%);
	}
	
	.getcode {
	  background-color: #1b2fe5;
	  color: white;
	  height: 35px;
	  margin: 0;
	}
	
	.countDown {
	  color: rgb(141, 141, 141);
	}

</style>
