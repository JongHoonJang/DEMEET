<template>
  <div id="chatbox">
    <div id="chat" v-if="!ismy">
      <span id="msgs">[{{ sendname }}]:{{ chat }}</span>
    </div>
02  </div>
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
      type: String
    },
    myId: {
      type: String
    },
    fromId: {
      type: String
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
    this.sendname = JSON.parse(this.msg).nickName;
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
  background-color: rgb(255, 255, 255);
  margin: 5px;
  padding: 5px;
  border-radius: 15px;
  width: fit-content;
  text-align: left;
  display: inline-block;
}
#mychat {
  background-color: rgb(243, 227, 3);
  margin: 5px;
  padding: 5px;
  border-radius: 15px;
  width: fit-content;
  text-align: left;
  display: inline-block;
}
#mychatbox {
  text-align: right;
}
#chatbox {
  background-color: rgb(243, 227, 3);
  text-align: left;
}
</style>
