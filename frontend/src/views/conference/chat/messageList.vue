<template>
  <div>
    <div id="chat_list" class="container" v-on:scroll.passive="scrolled">
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
      type: Array
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
      isScrolled: false,
      recentmsg: 0
    };
  },
  watch: {
    msgs: (window.onload = function() {
      if (this.isScrolled) {
        this.recentmsg += 1;
      }
    })
  },
  updated() {
    if (!this.isScrolled) {
      var objDiv = document.getElementById("app_chat_list");
      objDiv.scrollTop = objDiv.scrollHeight + 500;
    }
  },
  methods: {
    scrolled() {
      var objDiv = document.getElementById("app_chat_list");
      if (objDiv.scrollTop >= objDiv.scrollHeight - 500) {
        this.isScrolled = false;
        this.recentmsg = 0;
      } else {
        this.isScrolled = true;
      }
    },
    scrolldown() {
      var objDiv = document.getElementById("app_chat_list");
      objDiv.scrollTop = objDiv.scrollHeight;
      this.isScrolled = false;
      this.recentmsg = 0;
    }
  }
};
</script>

<style scoped>
.container {
  height: 900px;
  background: #b4b4b4;
  overflow: scroll;
  border-radius: 10px 10px 0px 0px;
}

</style>
