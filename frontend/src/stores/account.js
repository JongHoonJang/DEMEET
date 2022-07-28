import { defineStore } from "pinia";
import axios from 'axios'
import api from "@/api/api"
import router from "@/router"

export const useAccountStore = defineStore("account", {
  state: () => ({
    token: localStorage.getItem('token') || '' ,
    userList: [],
    profile: {},
    authError: null,
  }),
  getters: {
    isLoggedIn: state => !!state.token,
    setUserList: state => state.userList,
    setprofile: state => state.profile,
    setauthError: state => state.authError,
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
          console.log(res)
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
          this.fetchProfile()
          // router.push({ name: 'MainView' })
        })
    },

    // 로그아웃
    logout() {
      this.removeToken()
      router.push({ name: 'LoginView'})
    },

    // 비밀번호 수정
    changePassword({ dispatch }, credentials) {
      axios({
        url: api.accounts.password_update(),
        method: 'patch',
        data: credentials
      })
        .then(() => {
          dispatch('removeToken')
          router.push({ name: 'LoginView'})
        })
        .catch(err => {
          console.error(err.response)
        })
    },

    // 회원가입 + 자동로그인
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

    },

    // 회원 탈퇴
    signout({ dispatch, getters }) {
      axios({
        url: api.accounts.signup_userlist_signout(),
        method: 'delete',
        headers: getters.authHeader
      })
        .then(() => {
          dispatch('removeToken')
          router.push({ name: 'LoginView' })
        })
        .catch(err => {
          console.error(err.response)
        })
    },
    // 유저 닉네임 변경
    changeName({ state, getters }, namedata ) {
      axios({
        url: api.accounts.nickname_update(),
        method: 'patch',
        data: namedata,
        // 백엔드 완성하면 테스트(postman)후 변경
        headers: getters.authHeader
      })
       .then(res => {
        state.profile = res.data
        router.push({name: 'ProfileView'})
       })
       .catch(err => {
        console.error(err.response)
      })
    },
    // 유저 프로필 이미지 변경
    changeImage({ state, getters }, image ) {
      axios({
        url: api.accounts.profileimage_update(),
        method: 'patch',
        data: image,
        // 백엔드 완성하면 테스트(postman)후 변경
        headers: getters.authHeader
      })
       .then(res => {
        state.profile = res.data
        router.push({name: 'ProfileView'})
       })
       .catch(err => {
        console.error(err.response)
      })
    },

    //유저 목록조회
    userList({ state, getters }) {
      /*
      GET: 사용자가 로그인 했다면(토큰이 있다면)
        currentUserInfo URL로 요청보내기
          성공하면
            state.cuurentUser에 저장
          실패하면(토큰이 잘못되었다면)
            기존 토큰 삭제
            LoginView로 이동
      */
      if (getters.isLoggedIn) {
        axios({
          url: api.accounts.signup_userlist_signout(),
          method: 'get',
          headers: getters.authHeader,
        })
          .then(res => state.userList = res.data)
          .catch(err => {
            console.log(err.response)
          })
      }
    },
  }
})