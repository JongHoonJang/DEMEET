<template>
<div class="img-container">
  <h1>저장된 이미지</h1>
  <div class="window" v-if="isData">
    <div class="container">
      <img v-for="image in demeet.imageList"
      :key="image.dipid"
      :image="image"
      @dblclick="downloadDelete(image)" 
      :src="`${image.url}`" 
      alt="">
    </div>
  </div>
  <div class="image-btn" v-if="isImage">
    <button><a type="button" id="lnkDownload" href="">다운로드</a></button>
    <button><a type="button" >삭제</a></button>
    <button><a type="button" @click="isImage=false">취소</a></button>
  </div>
  <div class="window" v-if="!isData">
    <div class="content">
      <div class="slides">
        <img class="slide_item" src="@/assets/DEMEET.jpg" alt="">
        <img class="slide_item" src="@/assets/meeting-g8077ce66b_640.jpg" alt="">
        <img class="slide_item" src="@/assets/idea-g694f6ff46_640.jpg" alt="">
        <img class="slide_item" src="@/assets/video-call-gb0f0e6a82_640.jpg" alt="">
        <img class="slide_item" src="@/assets/lamp-g86bfbec92_640.jpg" alt="">
      </div>
    </div>
  </div>
  <div class="button-container" v-if="isData">
    <button class="prev">previous</button>
    <button class="next">next</button>
  </div>
</div>
</template>

<script>
import { ref } from 'vue'
import { useAccountStore } from "@/stores/account"
import { useRoute } from 'vue-router'
import axios from "axios"
export default {
  async setup() {
    const isData = ref(false),
          demeet = useAccountStore(),
          route = useRoute()  ,
          project_pk = ref(route.params.pid),
          isImage = ref(false),
          isDownload = ref(false),
          isDelete = ref(false)
  
    await axios({
        url: process.env.VUE_APP_API_URL + "projects/drawing/" + project_pk.value,
        method: 'get',
        headers: demeet.authHeader,
      })
        .then(res => {
          demeet.imageList = res.data.drawingPathList})

    const sliderOn = () => {
    const slides = document.querySelector('.slides'); // 슬라이드뼈대 감지
    const item = slides.getElementsByClassName('slide_item') // 슬라이드 아이템 획득
    const firstEle = item[0] // 첫번째 슬라이드 아이템
    firstEle.classList.add('ontheSlide') //첫번째 슬라이드 아이템에 ontheSlide 클래스 추가
      
    setInterval(sliderGo, 4000)
    
    function sliderGo () {
        
    const currentItem = document.querySelector('.ontheSlide'); // 현재 활성화된 슬라이드 아이템 감지

    currentItem.classList.remove('ontheSlide') //현재 활성화된 슬라이드 아이템 비활성화

      if (!currentItem.nextElementSibling) { // 만약 마지막 슬라이드 아이템이라면
          item[0].classList.add('ontheSlide') //첫번째 아이템을 활성화
      }else { // 그 외의 경우
        currentItem.nextElementSibling.classList.add('ontheSlide') //다음 엘리먼트를 활성화
      }   
    }
      
    }
    
    const init = () => {
      const container = document.querySelector(".container")
      const prevBtn = document.querySelector(".prev")
      const nextBtn = document.querySelector(".next");
      (function addEvent(){
        prevBtn.addEventListener('click', translateContainer.bind(this, 1))
        nextBtn.addEventListener('click', translateContainer.bind(this, -1))
      })()

      function translateContainer(direction){
        const selectedBtn = (direction === 1) ? 'prev' : 'next'
        container.style.transitionDuration = '500ms'
        // container.style.transform = `translateX(${direction * (100 / 100)}%)`;
        container.style.transform = `translateX(0%)`
        container.ontransitionend = () => reorganizeEl(selectedBtn)
      }

      function reorganizeEl(selectedBtn) {
        container.removeAttribute('style'); (selectedBtn === 'prev') ? container.insertBefore(container.lastElementChild, container.firstElementChild): container.appendChild(container.firstElementChild)
      }
    }
    const carousel = ref(null)
    if (demeet.imageList.length === 0) {
      carousel.value = sliderOn
      isData.value = false
    }else {
      carousel.value = init
      isData.value = true
    }    
    const downloadDelete = (setImageData) => {
      isImage.value = true
      if (isDownload.value) {
        var image = new Image()
        image.crossOrigin = "anonymous"
        image.src = setImageData.url
        var fileName = image.src.split("/").pop()
        image.onload = function() {
        var canvas = document.createElement('canvas')
        canvas.width = this.width
        canvas.height = this.height
        canvas.getContext('2d').drawImage(this, 0, 0)
        var link = document.createElement('a')
        link.href = canvas.toDataURL()
        link.download = fileName
        link.click()
        }
        isImage.value = false
      }else if (isDelete.value) {
        demeet.deleteImage({dipid: setImageData.dipid})
        isImage.value = false
      }
    }
    return {
      demeet,
      isData,
      isImage,
      carousel,
      downloadDelete
    }
  },
  mounted() {
    this.carousel()
  }
}
</script>

<style scoped>
@media (max-width: 1024px){
  .img-container{
		display: flex;
    flex-direction: column;
    align-items: center;
  }
}

.button-container {
  margin-left: 28px;
}
.img-container {
  display: flex;
  flex-direction: column;
}
body {
  width: 2000px;
  height: 500px;
}

h1 {
  font-size: 50px;
  margin-top: 50px;
  margin-left: 28px;
  color: white;
}

.window {
  overflow: hidden;  /*check out container's movement : command + */
  border: 4px solid; 
  width: 500px;
  height: 300px;
  margin-left: 28px;
  margin-bottom: 50px;
}

.container img {
  width: 500px;
  height: 300px;
}

button {
  font-size: 10px;
  height: 24px;
  width: 80px;
  background-color: #f6e58d;
  border: 4px solid #555;
  border-radius: 10px;
  margin: 20px;
}

button:hover {
  background-color: #E3B854;
}

.content{ /*컨텐츠 넓이지정*/
  position:relative;
  width:500px;
}
.slides { /*슬라이드 아이템이 나올 뼈대 지정*/
  width:500px;
  height:300px;
  position:relative;

}
.slide_item { /*슬라이드 아이템을 absolute로 겹쳐놓고 투명하게 하기*/
  position:absolute;
  width:500px;
  height:300px;
  left: 0%;
  opacity:0;
   transition:all 0.3s;
}
.ontheSlide { /*현재 아이템에 붙여줄 클래스*/
  opacity:1;
  transition:all 0.3s;
}


</style>
