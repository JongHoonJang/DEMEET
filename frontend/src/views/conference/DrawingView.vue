<template>
  <canvas width="600" height="600" id="canvas" style="border: 1px solid #ccc"></canvas>
  <button id="clear-canvas">clear</button>
  <button id="drawing-mode">Enter drawing mode</button>
  <label for="drawing-line-width">굵기: </label>
  <span class="info">1</span>
  <input type="range" value="1" min="1" max="20" id="drawing-line-width">
  <label for="drawing-color">색깔: </label>
  <input type="color" value="#000000" id="drawing-color">
  <input type="text" id="circle-text">
  <button id="circle"><span class="material-symbols-outlined">circle</span></button>
  <button id="rect"><span class="material-symbols-outlined">check_box_outline_blank</span></button>
  <button id="triangle"><span class="material-symbols-outlined">change_history</span></button>
  <button id="itext">text</button>
  <button id="delete"><span class="material-symbols-outlined">delete</span></button>
  <button id="line">선그리기</button>
  <button id="look"><span class="material-symbols-outlined">save</span></button>

</template>

<script>
import { onMounted } from 'vue'
import { fabric } from 'fabric'
import { useAccountStore } from "@/stores/account"
import _ from 'lodash'
export default {
  setup() {
    const init = () => {
      const demeet = useAccountStore()
      // $ = id를 통해 적용할 태그를 설정
      let $ = function(id){return document.getElementById(id)}
      // 캔버스 생성
      let canvas = new fabric.Canvas('canvas',{
        backgroundColor: 'rgb(255,255,255)'
      })
      fabric.Object.prototype.transparentCorners = true

      // 드로잉모드 on/off element
      const drawingModeEl = $('drawing-mode'),
            // 도구상자 숨김/보이기 => 추후에 적용
            // drawingOptionsEl = $('drawing-mode-options'),
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
            // 도형안에 들어갈 텍스트
            inputdata = $('circle-text'),
            // 선그리기
            drawingLineEl = $('line'),
            //보기모드 및 백엔드로 보낼 데이터
            lookImage = $('look')

      // 캔버스 초기화
      clearEl.onclick = function() { canvas.clear() }
      // 클릭할때마다 드로잉모드 true/false
      drawingModeEl.onclick = function() {
        canvas.isDrawingMode = !canvas.isDrawingMode
        if (canvas.isDrawingMode) {
          drawingModeEl.innerText = 'Cancel drawing mode'
          //drawingOptionsEl.style.display = ''
        }
        else {
          drawingModeEl.innerText = 'Enter drawing mode'
          //drawingOptionsEl.style.display = 'none'
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

        let text = new fabric.IText(`${inputdata.value}` || 'textbox',{
          left: 150,
          top: 100,
          fontSize: 30,
          originX: 'center',
          originY: 'center'
        })

        canvas.add(circle,text)
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

        let text = new fabric.IText(`${inputdata.value}` || 'textbox',{
          left: 150,
          top: 100,
          fontSize: 30,
          originX: 'center',
          originY: 'center'
        })

        canvas.add(rect,text)
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

        let text = new fabric.IText(`${inputdata.value}` || 'textbox',{
          left: 150,
          top: 100,
          fontSize: 30,
          originX: 'center',
          originY: 'center'
        })

        canvas.add(triangle,text)
      }


      // 선택된 그림 삭제
      deleteEl.onclick = function () {
        canvas.remove(canvas.getActiveObject())
      }

      ITextEl.onclick = function () {
        let text = new fabric.IText(`${inputdata.value}` || 'textbox',{
          left: 100,
          top: 100,
          fontSize: 30,
          originX: 'center',
          originY: 'center'
        })
        canvas.add(text)
      }

      // 직선 그리기
      let isLine = false
      let x1, y1
      drawingLineEl.onclick = () => {
        isLine = !isLine
        if (isLine) {
          drawingLineEl.innerText = '중지'
          canvas.getObjects('selectable', false)
          // 마우스 눌렀을 때 좌표 값 저장
          canvas.on('mouse:down', function(options) {
            x1 = options.e.clientX
            y1 = options.e.clientY
          })
          // 마우스 눌렀다가 땠을때 좌표 값 통해 선 그리기
          canvas.on('mouse:up', function(options) {
            let line = new fabric.Line([x1,y1,options.e.clientX,options.e.clientY],{ stroke: 'black' })
            canvas.add(line)
          })
        }
        else {
          canvas.getObjects('selectable', true)
          // 선그리기 종료
          drawingLineEl.innerText = '선그리기'
          // 마우스 함수 off
          canvas.off('mouse:down')
          canvas.off('mouse:up')
        }
      }
      // 보기용 그림띄우는 법
      lookImage.onclick = () => {
        // 백엔드로 갈 데이터 형식
        const imageData = {
          pid: demeet.project.pid,
          image: canvas.toSVG()
        }
        demeet.saveImage(imageData)
        //console.log(canvas.toSVG())
        //새로운 캔버스에 저장된 데이터 띄우기
        // canvasView.loadJoURL(JSON.stringify(canvas))
        // document.getElementById('imageview').innerHTML=canvas.toSVG()
        // 단일 객체 선택 불가
        // fabric.Object.prototype.selectable = false
      }


      // var imageSaver = document.getElementById('lnkDownload');
      // imageSaver.addEventListener('click', saveImage, false);

      // function saveImage() {
      //     let imageName = prompt( '파일명 입력', '' )
      //     this.href = canvas.toDataURL({
      //         format: 'png',
      //         quality: 0.8
      //     });
      //     this.download = `${imageName}.png`
      // }

    }
    onMounted(() => {
      init()
    })
  }
}

</script>
<style>
#imageview svg{
  width: 300px;
  height: auto;
}
</style>