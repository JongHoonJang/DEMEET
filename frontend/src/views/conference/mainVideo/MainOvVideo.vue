<template>
		<video id="mainVideoID" ref="vidoeRoot" autoplay/>
</template>

<script>
import { onMounted, onUpdated, ref } from 'vue';
export default {
	name: 'OvVideo',

	props: {
		streamManager: Object,
	},

	setup(props) {

	const vidoeRoot = ref(null)
	onMounted(() => {
			props.streamManager.addVideoElement(vidoeRoot.value)
		})

	onUpdated(()=>{
		props.streamManager.addVideoElement(vidoeRoot.value)
	})

		return {
			vidoeRoot,
		}
	},
};
</script>
<style scoped>
	#mainVideoID {
		width: 50VW;
		margin-top: 10vh;
	}
</style>