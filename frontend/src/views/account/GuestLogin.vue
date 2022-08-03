<template>
  <div class='backdrop'>
    <div class='account-box'>
      <div class='account-left'>
        <div style='display:flex; justify-content:center;'>
          <h1 class='sign-head'>Guest</h1>
        </div>
        <form @submit="guest(guestData)" class='account-info'>
          <p><input type="email" v-model="guestData.eamil" placeholder="Email" class="input-prop"></p>
          <p><input type="text" v-model="guestData.name" placeholder="Full Name" class="input-prop"></p>
          <button class="guest-btn">Join</button>
        </form>
      </div>
      <div class='account-right'>
        <div class='image-box'>
          <h1 class="logo">DEMEET</h1>
        </div>
        <div>
          <h1>Camera</h1>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  setup() {
    const guestData = {
      name: '',
      email: ''
    }
    const guest = () => {
      console.log(guestData)
    }
    return {
      guestData,
      guest
    }
  }
}
</script>

<style scoped>

input::placeholder {color:white;}

.image-box {
  width: 20vw;
  height: 20vh;
  background-size: contain;
}

.account-info {
  width: 20vw;
  height: 20vh;

}

.guest-btn {
  width: 60px;
  height: 30px;
  background: linear-gradient(90deg, #FF00D6 8.81%, #00E0FF 94.11%);
  border-radius: 5px;
}

.input-prop {
  color: white;
  background: transparent;
  border-top: none;
  border-bottom: solid;
  border-left: none;
  border-right: none;
  border-color: rgba(255, 1, 214, 1);
  margin-bottom: 1rem;
  /* border-bottom: 1; */
  font-size : 1rem;
  line-height: 2;
  /* text-decoration: underline; */
}

.account-box {
  position: absolute;
  background-color : rgba(26, 15, 31, 0.3);
  background-clip: content-box;
  display: flex;
  width: 60vw;
  height: 60vh;
  flex-grow: 1;
  justify-content: space-evenly;
}

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

.account-left {
  display: flex;
  flex-direction: column;
  justify-content: center;

}

.sign-head {
  /* position: absolute; */
  /* width: 692px;  */
  /* height: 120px; */
  color:rgba(255, 1, 214, 0);

  -webkit-text-stroke: 0.125rem rgb(255, 255, 255);
  letter-spacing: -0.25rem;
  word-spacing: -0.75rem;
  margin: 2rem;
  
  font-family: 'Poppins';
  font-style: italic;
  /* transform:rotate(5deg); */
  font-weight: 900;
  font-size: 4rem;
  line-height: 5rem;
  display: flex;
}

.logo {
    /* width: 5rem;
    height: 5rem; */
    color: rgba(26, 15, 31, 0.3);
    font-size: 4rem;
    background: linear-gradient(to right, rgba(255, 0, 214, 0.9), rgba(0, 224, 255, 0.9));
    -webkit-text-stroke: transparent;
    /* stroke-width: 0.25rem; */
    -webkit-background-clip: text;
    letter-spacing: -0.25rem;
    }


</style>