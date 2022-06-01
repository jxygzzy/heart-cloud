<template>
	<div>
		<el-container>
		  <el-aside width="242px">
				<Aside/>
			</el-aside>
		  <el-main>
				<router-view/>
			</el-main>
		</el-container>
	</div>
</template>

<script>
import Aside from "../components/Aside"
	
export default {
  name: 'Home',
  components: {
		Aside
  }
}
</script>

<style>
</style>