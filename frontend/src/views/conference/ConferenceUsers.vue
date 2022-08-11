<template>
  <div id="ConferenceUsers">
    <p>유저 목록입니다.</p>
    <div><p>{{ clientData.clientData }}</p></div>
  </div>
</template>

<script>
import { computed } from 'vue'

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
  setup(props) {

	async function getConnectionData() {
		const { connection } = await props.publisher.stream
		console.log('ddddddddddd')
		console.log(connection)
		return  JSON.parse(connection.data.split('%')[0])
		}

   const clientData = computed(() => {
			const clientData  = getConnectionData()
			return clientData
	})
  
  return {
    getConnectionData,
    clientData
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
  height: 50%;
}

</style>
