<template>
<div v-if="streamManager">
	<main-ov-video :streamManager="streamManager" :isDrawing="isDrawing"/>
	<div id="mainVideoFrame"><p>{{ clientData.clientData }}</p></div>
</div>
</template>

<script>
import MainOvVideo from './MainOvVideo'
import { computed } from 'vue'

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
		
	async function getConnectionData() {
		const { connection } = await props.streamManager.stream
		return  JSON.parse(connection.data.split('%')[0])
		}

	const clientData = computed(() => {
			const clientData = getConnectionData()
			return clientData
	})
		return {
			clientData,
		}
	}
}
</script>