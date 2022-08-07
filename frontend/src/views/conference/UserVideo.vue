<template>
<div v-if="streamManager">
	<ov-video :streamManager="streamManager"/>
	<div><p>{{ clientData.clientData }}</p></div>
</div>
</template>

<script>
import OvVideo from './OvVideo';
import { computed } from 'vue'

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
	},

	setup(props){

	const getConnectionData = () => {
			const { connection } = props.streamManager.stream
			return JSON.parse(connection.data.split('%')[0])
		}
	
	const clientData = computed(() => {
			const clientData  = getConnectionData()
			return clientData
	})

		return {
			clientData,
			getConnectionData,
		}
	}



};
</script>
