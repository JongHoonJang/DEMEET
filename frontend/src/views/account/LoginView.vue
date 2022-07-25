<template>
  <div class='backdrop'>
    <div class='account-box'>
      <div class='account-left'>
        <div>
          <h1 class='sign-head'>Sign In</h1>
        </div>
        <div class='account-info'>
          <p><input type="text" placeholder="ID" class="input-prop"></p>
          <p><input type="text" placeholder="password" class="input-prop"></p>
        </div>
        <div>
          <button>Sign In</button>
        </div>
        <div style='display:flex; justify-content: space-around'>
          <div>
            <p>social1</p>
          </div>
          <div>
            <p>social2</p>
          </div>
          <div>
            <p>social3</p>
          </div>
        </div>
      </div>
      <div class='account-right'>
        <div class='image-box'>
          <h1 class="logo">DEMEET</h1>
          <!-- <img src="@/assets/DEMEET_logo.png" alt=""> -->
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
  components: {

  }
}
</script>

<style>

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
  padding: 10vw 15vw;
  width: 50vw;
  height: 60vh;
  border: solid;
  flex-grow: 1;
  justify-content: space-evenly;
}

.backdrop {
  position: relative;
  background:
  linear-gradient( to bottom,  rgba(255, 1, 214, 0.3), rgba(7, 219, 254, 0.3));
  width: 100%;
  height: 100vh;
}

.sign-head {
  /* position: absolute; */
  /* width: 692px;  */
  /* height: 120px; */
  color:rgba(255, 1, 214, 0);
  text-align: center;

  -webkit-text-stroke: 0.125rem rgb(255, 255, 255);
  letter-spacing: -0.25rem;
  word-spacing: -0.75rem;
  
  font-family: 'Poppins';
  font-style: italic;
  /* transform:rotate(5deg); */
  font-weight: 900;
  font-size: 4rem;
  line-height: 9rem;
  display: flex;
  align-items: center;
  text-align: center;
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