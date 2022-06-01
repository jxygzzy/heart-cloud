<template>
  <div id="app">
		<router-view/>
	  <go-top/>
  </div>
</template>

<script>
import GoTop from "./components/gotop/GoTop"

export default {
  name: 'app',
  components: {
		"go-top": GoTop,
  }
}
</script>

<style>
*{
	margin: 0px;
	padding: 0px;
}
html{
  height: 100%;
}
body {
  height: 100%;
}
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
.el-table__body-wrapper::-webkit-scrollbar {
        /*width: 0;宽度为0隐藏*/
        width: 2px;
    }

    .el-table__body-wrapper::-webkit-scrollbar-thumb {
        border-radius: 2px;
        height: 50px;
        background: #eee;
    }

    .el-table__body-wrapper::-webkit-scrollbar-track {
        box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
        border-radius: 2px;
        background: rgba(0, 0, 0, 0.4);
    }

    .el-table__empty-text {
        display: none !important;
    }

    .el-table__body {
        min-height: 0.5px;
    }

    .el-table__empty-block {
        width: 100% !important;
        margin: auto;
        height: 100% !important;
        min-height: 300px !important;
        line-height: 300px !important;
        box-sizing: border-box;
        background: url('~@/assets/empty.png') no-repeat center;
    }
</style>
