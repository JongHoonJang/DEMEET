<template>
	<video id="videoID" autoplay/>
</template>

<script>
import {ref} from 'vue'

export default {
	name: 'OvVideo',

	props: {
		streamManager:{
			type:Object,
			default: () => {
				return {}
			}
		},
		isDrawing:{
			type:Boolean
		}
	},
	
	setup(props) {
		const data = ref(props)
		console.log(data.value.isDrawing)
	},

	mounted () {
		this.streamManager.addVideoElement(this.$el);
	},
};
</script>
