<template>
<div id="frame" v-if="streamManager">
	<ov-video :streamManager="streamManager" :isDrawing="isDrawing"/>
	<div class="videoNickName"><p id="userNickName">{{ userNickName.clientData }}</p></div>
</div>
</template>

<script>
import OvVideo from './OvVideo'
import {  onMounted, ref } from 'vue'

export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	props: {
		streamManager: {
			type:Object,
			default: () => {
				return {}
			}
		},
		users:{
			type:Array,
			default: () => {
				return []
			}
		},
		isDrawing:{
			type:Boolean
		}
	},

	setup(props){
	const userNickName = ref(Object)
		
	async function getConnectionData() {
		const { connection } = await props.streamManager.stream
		return  connection.data.split('%')
		}

	const clientData = onMounted(async() => {
			const clientData = await getConnectionData()
			userNickName.value = JSON.parse(clientData[0])
			return clientData
	})
		return {
			clientData,
			userNickName,
		}
	}
}
</script>
<style scoped>
	#frame{
  width: 100%;
  overflow: hidden;
  margin: 0px auto;
  position: relative;
	}

	div.videoNickName {
		margin: 0;
		position: absolute;
		width: 10rem;
		top: 90%;
		left: 50%;
		transform: translate(-50%,-50%);
	}

	#userNickName{
		margin: 0;
		border-radius: 20%;
		font-size: 1rem;
		color: #f1f6f9cc;
		text-align: center;
		background-color: #14274e86;
	}
	@media all and (max-width: 1024px){
	#frame{
  width: 100%;
  overflow: hidden;
  margin: 0px auto;
  position: relative;
	}

	div.videoNickName {
		margin: 0;
		position: absolute;
		width: 10rem;
		top: 90%;
		left: 50%;
		transform: translate(-50%,-50%);
	}

	#userNickName{
		margin: 0;
		border-radius: 20%;
		font-size: 1rem;
		color: #f1f6f9cc;
		text-align: center;
		background-color: #14274e86;
	}
}
</style>>