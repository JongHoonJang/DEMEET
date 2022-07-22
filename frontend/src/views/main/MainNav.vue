<template>
  <nav>
    <img src="@/assets/DEMEET_logo.png" alt="">
    <div>
      <form action="">
        <input type="text" placeholder="Search">
        
      </form>
    </div>
    <div>
      <a href="">Logout</a>
      <a href="">Profile</a>
    </div>
  </nav>
</template>

<script>
export default {

}
</script>

<style>
nav {
  height: 70px;
  display: flex;
  justify-content: space-between;
  background: #1A1D1F;
}

nav img {
  margin-top: 20px;
  margin-left: 20px;
  width: 110px;
  height: 50px;
}

nav input {
  width: 525px;
  height: 50px;
  margin-top: 16px;
  background: #111315;
  border-radius: 10px;
  font-family: 'Poppins';
  font-style: normal;
  font-weight: 600;
  font-size: 32px;
  line-height: 48px;
  text-align: start;
  color: white;
}

nav div a {
  font-weight: bold;
  color: #4C4E50;
  text-decoration-line: none;
  margin-top: 20px;
  margin-right: 20px;
  font-family: 'Poppins';
  font-style: normal;
  font-weight: 600;
  font-size: 32px;
  line-height: 48px;
  text-align: center;
  color: #4C4E50;
}

nav a.router-link-exact-active {
  color: #2978c2;
}

</style>