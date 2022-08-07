<template>
	<video autoplay/>
</template>

<script>
export default {
	name: 'OvVideo',

	props: {
		streamManager:{
			type:Object,
			default: () => {
				return {}
			}
		},
	},

	mounted () {
		this.streamManager.addVideoElement(this.$el);
	},
};
</script>
