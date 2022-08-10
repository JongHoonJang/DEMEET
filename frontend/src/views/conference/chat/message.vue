<template>
  <div id="chatbox">
    <div id="chat" v-if="!ismy">
      <span id="msgs">[{{ sendname }}]:{{ chat }}</span>
    </div>
    </div>
  <div id="mychatbox">
    <div id="mychat" v-if="ismy">
      <span id="msgs">{{ chat }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "ConferenceMessage",
  props: {
    msg: {
      type: String,
			default: () => {
				return ""
			}      
    },
    myId: {
      type: String,
			default: () => {
				return ""
			}      
    },
    fromId: {
      type: String,
			default: () => {
				return ""
			}      
    }
  },
  data() {
    return {
      ismy: this.equal(),
      sendname: "",
      chat: ""
    };
  },
  mounted() {
    this.sendname = JSON.parse(this.msg).userName;
    this.chat = JSON.parse(this.msg).msg;
  },
  methods: {
    equal() {
      if (this.myId == this.fromId) {
        return true;
      } else {
        return false;
      }
    }
  }
};
</script>

<style scoped>

#msgs {
  font-size: 15px;
  margin: 2px;
  padding-left: 5px;
  padding-right: 5px;
}
#chat {

  margin: 5px;
  padding: 5px;
  border-radius: 15px;
  width: fit-content;
  text-align: left;
  display: inline-block;
}
#mychat {

  margin: 5px;
  padding: 5px;
  border-radius: 15px;
  width: fit-content;
  text-align: left;
  display: inline-block;
}
#mychatbox {
  float: right;
}
#chatbox {
  background-color: #b4b4b4;
  width: fit-content;
  text-align: left;
}
</style>

