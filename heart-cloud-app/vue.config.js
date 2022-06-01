module.exports = {
	devServer:{
		proxy:{
			'/api':{
				//目标路径
				target:'http://localhost:8082',
				//允许跨域
				changOrigin:true,
				//重写路径
				pathRewrite: {
				  '^/api': ''
				}
			}
		}
	},
	runtimeCompiler:true,
}