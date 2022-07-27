<template>
  <div class='backdrop'>
    <div class='account-box'>
      <div class='account-left'>
        <MemberLogin></MemberLogin>
        <!-- <GuestLogin></GuestLogin> -->
        <DefaultSignup></DefaultSignup>
      </div>
      <div class='account-right'>
        <CheckCamera></CheckCamera>
        <!-- <SocialSignup></SocialSignup> -->
      </div>
    </div>
  </div>
</template>

<script>
// import MemberLogin from '@/views/account/MemberLogin';
// import GuestLogin from '@/views/account/GuestLogin';
import DefaultSignup from '@/views/account/DefaultSignup';
import CheckCamera from '@/views/account/CheckCamera';
// import SocialSignup from '@/views/account/SocialSignup';

export default {
  components: {
    // MemberLogin,
    // GuestLogin,
    DefaultSignup,
    CheckCamera,
    // SocialSignup,
  }
}
</script>

<style scoped>


.backdrop {
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  background:
  linear-gradient( to bottom,  rgba(255, 1, 214, 0.3), rgba(7, 219, 254, 0.3));
  width: 100%;
  height: 100vh;
}

.account-box {
  position: absolute;
  background-color : rgba(26, 15, 31, 0.3);
  background-clip: content-box;
  display: flex;
  width: 50vw;
  height: 60vh;
  flex-grow: 1;
  justify-content: space-evenly;
}

.account-left {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.account-right {
  display: flex;
  justify-content: center;
}

/* .account-right {

} */

</style>