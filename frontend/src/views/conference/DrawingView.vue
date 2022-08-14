<template>
  <canvas width="640" height="480" id="canvas" style="border: 1px solid #ccc"></canvas>
  <div class="item-box">
    <div class="drawing-box">
      <button id="clear-canvas">clear</button>
      <button id="drawing-mode">펜 사용</button>
      <label class="width-label" for="drawing-line-width">굵기: </label>
      <span class="info">1</span>
      <input type="range" value="1" min="1" max="20" id="drawing-line-width">
      <label class="color-label" for="drawing-color">색깔: </label>
      <input type="color" value="#000000" id="drawing-color">
    </div>
    <div id="drawing-mode-options">
      <button id="itext">text</button>
      <button id="circle"><span class="material-symbols-outlined">circle</span></button>
      <button id="rect"><span class="material-symbols-outlined">check_box_outline_blank</span></button>
      <button id="triangle"><span class="material-symbols-outlined">change_history</span></button>
      <button id="delete"><span class="material-symbols-outlined">delete</span></button>
    </div>
      <button id="save"><span class="material-symbols-outlined">save</span></button>
  </div>

</template>

<script>
import { onMounted,ref } from 'vue'
import { fabric } from 'fabric'
import { useAccountStore } from "@/stores/account"
import _ from 'lodash'
export default {
  props:['openviduSessionId'],
  setup(props) {
    const init = () => {
      const sessionData = ref(props)
      const demeet = useAccountStore()
      // $ = id를 통해 적용할 태그를 설정
      let $ = function(id){return document.getElementById(id)}
      // 캔버스 생성
      let canvas = new fabric.Canvas('canvas',{
        backgroundColor: 'rgb(240,240,240)'
      })
      fabric.Object.prototype.transparentCorners = true
      // 드로잉모드 on/off element
      const drawingModeEl = $('drawing-mode'),
            // 도구상자 숨김/보이기 => 추후에 적용
            drawingOptionsEl = $('drawing-mode-options'),
            // 펜 색상 설정 element
            drawingColorEl = $('drawing-color'),
            // 펠 굵기 설정 element
            drawingLineWidthEl = $('drawing-line-width'),
            // 캔버스 초기화 element
            clearEl = $('clear-canvas'),
            // 원형 텍스트박스
            drawingCircleEl = $('circle'),
            // 사각형 텍스트 박스
            drawingRectEl = $('rect'),
            // 삼각형 텍스트 박스
            drawingTriangleEl = $('triangle'),
            // 수정 버튼
            ITextEl = $('itext'),
            // 삭제 버튼
            deleteEl = $('delete'),
            //보기모드 및 백엔드로 보낼 데이터
            saveImage = $('save')

      // 캔버스 초기화
      clearEl.onclick = function() { 
        canvas.clear() 
        canvas.backgroundColor = 'rgb(250,250,250)'
      }
      // 클릭할때마다 드로잉모드 true/false
      drawingModeEl.onclick = function() {
        canvas.isDrawingMode = !canvas.isDrawingMode
        if (canvas.isDrawingMode) {
          drawingModeEl.innerText = '중지'
          drawingOptionsEl.style.display = 'none'
        }
        else {
          drawingModeEl.innerText = '펜 사용'
          drawingOptionsEl.style.display = ''
        }
      }
      // 펜 굵기 설정
      drawingLineWidthEl.onchange = function() {
        canvas.freeDrawingBrush.width = parseInt(this.value, 10) || 1
        this.previousSibling.innerText = this.value
      }
      // 펜 색상 설정
      drawingColorEl.onchange = function() {
        var brush = canvas.freeDrawingBrush
        brush.color = this.value
        if (brush.getPatternSrc) {
          brush.source = brush.getPatternSrc.call(brush)
        }
      }

      // 원 텍스트 박스 설정
      drawingCircleEl.onclick = function () {
        
        const color = _.sample(['red', 'blue','green','gray','orange','pink','plum','gold'])
        let circle = new fabric.Circle({
          left: 150,
          top: 100,
          radius: 100,
          fill: color,
          scaleY: 0.5,
          originX: 'center',
          originY: 'center'
        })
        canvas.add(circle)
      }
      //사각형 텍스트 박스
      drawingRectEl.onclick = function() {
        const color = _.sample(['red', 'blue','green','gray','orange','pink','plum','gold'])
        let rect = new fabric.Rect({
          left: 150,
          top: 100,
          width: 200,
          height: 100,
          fill: color,
          originX: 'center',
          originY: 'center'
        })

        canvas.add(rect)
      }
      // 삼각형 텍스트 박스
      drawingTriangleEl.onclick = function() {
        const color = _.sample(['red', 'blue','green','gray','orange','pink','plum','gold'])
        let triangle = new fabric.Triangle({
          left: 150,
          top: 100,
          width: 200,
          height: 100,
          fill: color,
          originX: 'center',
          originY: 'center'
        })
        canvas.add(triangle)
      }


      // 선택된 그림 삭제
      deleteEl.onclick = function () {
        canvas.remove(canvas.getActiveObject())
      }

      ITextEl.onclick = function () {
        let text = new fabric.IText('textbox',{
          left: 100,
          top: 100,
          fontSize: 30,
          originX: 'center',
          originY: 'center'
        })
        canvas.add(text)
      }

      // 보기용 그림띄우는 법
      saveImage.onclick = () => {
        const dataURLtoFile = (dataurl, fileName) => {
  
          var arr = dataurl.split(','),
              mime = arr[0].match(/:(.*?);/)[1],
              bstr = atob(arr[1]), 
              n = bstr.length, 
              u8arr = new Uint8Array(n)
              
          while(n--){
              u8arr[n] = bstr.charCodeAt(n)
          }
          
          return new Blob([u8arr], fileName, {type:mime})
        }
      
      //Usage example:
        let file = dataURLtoFile(canvas.toDataURL({format: 'png', quality: 0.8}),'image.png')
        const imageData = ref({
          openviduSessionId: sessionData.value.openviduSessionId,
          multipartFile: file
        })
        // 백엔드로 갈 데이터 형식
        if (confirm('저장하시겠습니까?')) {
          demeet.saveImage(imageData.value)
        }
      }
    }
    onMounted(() => {
      init()
    })
  }
}

</script>
<style>
.drawing-box button {
  margin: 4px;
  border-radius: 5px;
  background: #2097F7;
  color: white;
}
.drawing-box button span{
  margin-top: 4px;
}
.drawing-box button span:hover{
  transform: scale(1.4);
}

.color-label {
  margin: 4px;
  color: white;
}
.width-label {
  margin-top: 4px;
  color: white;
}
.info{
  color: white;
  margin: 8px;
}
#imageview svg{
  width: 300px;
  height: auto;
}
.item-box {
  margin: 8px;
  display: flex;
  flex-direction : column;
}
.drawing-box{
  display: flex;
  justify-content: flex-start;
  margin: 8px;
}
#drawing-mode-options {
  display: flex;
  justify-content: flex-start;
}
#drawing-mode-options button {
  margin: 4px;
  border-radius: 5px;
  background: #2097F7;
  color: white;
}
#drawing-mode-options button span:hover{
  transform: scale(1.4);
}

#drawing-mode-options button span{
  margin-top: 4px;
}
</style>