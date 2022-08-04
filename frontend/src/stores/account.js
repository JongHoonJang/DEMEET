import { defineStore } from "pinia";
import axios from 'axios'
import api from "@/api/api"
import router from "@/router"

export const useAccountStore = defineStore("account", {
  state: () => ({
    token: localStorage.getItem('token') || '' ,
    profile: {},
    userList: [],
    project: {},
    projects: [],
    authError: null,
  }),
  getters: {
    isLoggedIn: state => !!state.token,
    authHeader: state => ({ Authorization: `Bearer ${state.token}`}),

  },
  actions: {
    //token값 저장
    saveToken(token) {
      this.token = token
      localStorage.setItem('token', token)
    },
    //token값 삭제
    removeToken() {
      this.token = ''
      localStorage.setItem('token', '')
    },
    // 유저 프로필
    fetchProfile() {
      axios({
        url: api.accounts.currentUserInfo(),
        method: 'get',
        headers: this.authHeader,
      })
        .then(res => {
          this.profile = res.data
        })
    },

    // 로그인 (이메일,패스워드)
    login(credentials) {
      axios({
        url: api.accounts.login(),
        method: 'post',
        data: credentials
      })
        .then(res => {
          const token = res.data.accessToken
          this.saveToken(token)        
          router.push({ name: 'MainView' })
        })
        .catch(err => (
          console.error(err.response),
          alert('이메일 혹은 비밀번호가 잘못되었습니다.')
        ))
    },

    // 로그아웃
    logout() {
      confirm('로그아웃 하기겠습니까?')
      this.removeToken()
      router.push({ name: 'LoginView'})
    },

    // 비밀번호 수정
    changePassword(namedata) {
      console.log(namedata)
      axios({
        url: api.accounts.password_update(),
        method: 'patch',
        data: namedata,
        headers: this.authHeader,
      })
        .then(() => {
          confirm('비밀번호를 변경하시겠습니까?')
          alert('비밀번호가 변경되었습니다. \n 다시 로그인 해주세요.')
          this.removeToken()
          router.push({ name: 'LoginView'})
        })
        .catch(err => {
          alert('현재 비밀번호가 다릅니다.')
          console.error(err.response)
        })
    },

    // 회원가입
    signup(signdata) {
      console.log(signdata)
      axios({
        url: api.accounts.checkemail(),
        method: 'get',
        data: signdata.email
      })
        .then(() => {
          axios({
            url: api.accounts.signup_userlist_signout(),
            method: 'post',
            data: signdata
          })
            .then(() => {
              router.push({ name: 'LoginView' })
            })
            
        })
        .catch(err => (
          console.error(err.response),
          alert('중복된 메일입니다.')
        ))

    },

    // 회원 탈퇴
    signout() {
      axios({
        url: api.accounts.signup_userlist_signout(),
        method: 'delete',
        headers: this.authHeader
      })
        .then(() => {
          alert('회원탈퇴 되었습니다.')
          this.removeToken()
          router.push({ name: 'LoginView'})
        })
        .catch(err => {
          console.error(err.response)
        })
    },
    // 유저 닉네임 변경
    changeName(namedata) {
      console.log(namedata)
      axios({
        url: api.accounts.nickname_update(),
        method: 'patch',
        data: { nickname: namedata },
        // 백엔드 완성하면 테스트(postman)후 변경
        headers: this.authHeader
      })
       .then(res => {
        this.profile = res.data
        router.go({name: 'ProfileView'})
       })
       .catch(err => {
        console.error(err.response)
      })
    },
    // 유저 프로필 이미지 변경
    changeImage( image ) {
      axios({
        url: api.accounts.profileimage_update(),
        method: 'patch',
        data: image,
        headers: this.authHeader
      })
       .then(res => {
        this.profile = res.data
        router.go({name: 'ProfileView'})
       })
       .catch(err => {
        console.error(err.response)
      })
    },

    //유저 목록조회
    userList() {
      axios({
        url: api.accounts.signup_userlist_signout(),
        method: 'get',
        headers: this.authHeader,
      })
        .then(res => this.userList = res.data)
        .catch(err => {
          console.log(err.response)
        })
      
    },
//////////////////////////////////////////////////////////////project
    // 프로젝트 상세 조회
    fetchProject(project_pk) {
      axios({
        url: api.projects.project_detail_update(project_pk),
        method: 'get',
        headers: this.authHeader,
      })
        .then(res => {
          this.project = res.data
        })
        .catch(err => console.error(err.response))
    }, 

    // 유저가 속한 프로젝트 조회
    fetchProjects(user_pk) {
      axios({
        url: api.projects.projects_list_create(user_pk),
        method: 'get',
        headers: this.authHeader,
      })
        .then(res => {
          this.projects = res.data
        })
        .catch(err => console.error(err.response))
    },
    // 프로젝트 생성
    createProject(projectData) {
      axios({
        url: api.projects.projects_list_create(),
        method: 'post',
        data: projectData,
        headers: this.authHeader,
      })
        .then(res => {
          this.project = res.data
        })
        .catch(err => console.error(err.response))
    },

    // 유저 초대
    addUser({project_pk, user_pk}) {
      axios({
        url: api.projects.add_user(project_pk, user_pk),
        method: 'post',
        data: {},
        headers: this.authHeader,
      })
        .then(res => {
          this.project = res.data
        })
        .catch(err => console.error(err.response))
    },

    // 프로젝트 이미지 리스트


    // 프로젝트 이미지 삭제
    deleteImage(project_pk) {
      if (confirm('정말 삭제하시겠습니까?')) {
        axios({
          url: api.projects.image_list_delete(project_pk),
          method: 'delete',
          data: {},
          headers: this.authHeader,
        })
          .then(res => {
            this.project = res.data
            //router.go({ name: 'DetailView' })
          })
          .catch(err => console.error(err.response))
      }
    },

    // 프로젝트 이미지 저장
    saveImage(project_pk) {
      axios({
        url: api.projects.image_save(project_pk),
        method: 'post',
        headers: this.authHeader,
      })
        .then(res => {
          this.project = res.data
          //router.go({ name: 'DetailView' })
        })
        .catch(err => console.error(err.response))
    }
  }
})