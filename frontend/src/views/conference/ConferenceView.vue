<template>
  <!-- <nav>

  </nav> -->
  <main>
    <section>
      <article>
        <!-- 영상, 드로잉 등 -->
        <ConferenceVideo/>
      </article>
      <footer>
        <!-- 동작 버튼 등,  -->
        <ConferenceFooter/>
      </footer>
    </section>
    <aside>
      <!-- 참자가 목록, 채팅 -->
      <ConferenceUsers
      :publisher="publisher"
      :subscribers="subscribers"
      />
      <ConferenceChat/>
    </aside>
  </main>
  
</template>

<script>
import ConferenceVideo from './ConferenceVideo'
import ConferenceUsers from './ConferenceUsers'
import ConferenceChat from './ConferenceChat'
import ConferenceFooter from './ConferenceFooter'

export default {
components: { ConferenceVideo, ConferenceUsers, ConferenceChat, ConferenceFooter }
}

</script>

<style scoped>
main{
  display: flex;
  margin: 0;
  height: 100vh;

}

section {
  width: 80%;
  display: flex;
  flex-direction: column;
  background-color: rgb(13, 19, 30);
}

article {
  height: 90%;
}

footer {
  background-color: rgb(21, 29, 42);
  height: 10%;
}

  aside{
    display: flex;
    flex-direction: column;
    height: 100%;
    width: 20%;
    background-color: #fff;
  }

</style>