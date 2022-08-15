<template>
<div v-if="streamManager">
	<main-ov-video :streamManager="streamManager" :isDrawing="isDrawing"/>
	<!-- <div id="mainVideoFrame"><p>"{{ mainUserNickName.clientData }}" 님의 비디오</p></div> -->
</div>
</template>

<script>
import MainOvVideo from './MainOvVideo'
import { onMounted, ref } from 'vue'

export default {
	name: 'UserVideo',

	components: {
		MainOvVideo,
	},

	props: {
		streamManager: {
			type:Object,
			default: () => {
				return {}
			}
		},
		isDrawing:{
			type:Boolean
		}
	},

	setup(props){
		const mainUserNickName = ref(Object)
		
	async function getConnectionData() {
		const { connection } = await props.streamManager.stream
		return  JSON.parse(connection.data.split('%')[0])
		}

	const clientData = onMounted(async() => {
			const clientData = await getConnectionData()
			console.log(clientData)
			mainUserNickName.value = clientData
			return clientData
	})
		return {
			clientData,
			mainUserNickName
		}
	}
}
</script>

<style scoped>
	#mainVideoFrame{
		background-color: #394867c4;
	}

	#mainVideoFrame p {
		color: #f1f6f9bd;
		margin: 0;
	}

	@media all and (max-width: 1024px){
	#mainVideoFrame{
		background-color: #394867c4;
	}

	#mainVideoFrame p {
		color: #f1f6f9bd;
		margin: 0;
	}
}
</style>>