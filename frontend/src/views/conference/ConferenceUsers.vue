<template>
  <div id="ConferenceUsers">
    <p>유저 목록입니다.</p>
    <div v-for="(user, index) in users" :key="index" :users="users">{{user.clientData.slice(15,-2)}}</div>
  </div>
</template>

<script>

export default {
  name: 'ConferenceUsers',
  components: {},
    props:{ 
		publisher:{
			type:Object,
			default: () => {
				return {}
			}
		},
		subscribers:{
			type:Array,
			default: () => {
				return []
			}
		},
		users:{
			type:Array,
			default: () => {
				return []
			}
		}
	},
  setup() {


  
  return {

  }
  },
  create() {},
  mounted() {},
  unmounted() {},
  methods: {},
}
</script>

<style scoped>
#ConferenceUsers {
  background-color: rgb(207, 134, 134);
  height: 30%;
}

</style>
