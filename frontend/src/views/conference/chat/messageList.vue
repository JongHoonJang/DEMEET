<template>
  <div>
    <div id="chat_list" class="container" >
      <ConferenceMessage
        v-for="(msg, index) in msgs"
        :key="index"
        :msg="msg"
        :myId="myId"
        :fromId="fromId"
      />
    </div>

  </div>
</template>

<script>
import ConferenceMessage from "./message";
export default {
  name: "MessageList",
  components: {
    ConferenceMessage
  },
props: {
    msgs: {
      type: Array,
    default: ()=> {
      return []
    }
    },
    myId: {
      type: String,
    default: ()=> {
      return ""
    }
    },
    fromId: {
      type: String,
    default: ()=> {
      return ""
    }
    }
  },
  data() {
    return {

    };
  },
  updated() {
    if (!this.isScrolled) {
      var objDiv = document.getElementById("chat_list");
      objDiv.scrollTop = objDiv.scrollHeight + 500;
    }
  },



};
</script>

<style scoped>
.container {
  height: 500px;
  background: #b4b4b4;
  overflow: scroll;
  border-radius: 10px 10px 0px 0px;
}

</style>
