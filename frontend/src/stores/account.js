import { defineStore } from "pinia";
import axios from 'axios'
import api from "@/api/api";
import router from "@/router";

export const useCounterStore = defineStore("account", {
  state: () => ({
    token: localStorage.getItem('token') || '' ,
    currentUser: {},
    profile: {},
    authError: null,
    projects: [],
  }),
  getters: {
    isLoggedIn: state => !!state.token,
    currentUser: state => state.currentUser,
    profile: state => state.profile,
    authError: state => state.authError,
    authHeader: state => ({ Authorization: `Token ${state.token}`}),
    projects: state => state.projects
  },
  actions: {
    saveToken({ state }, token) {
      state.token = token
      localStorage.setItem('token', token)
    },

    removeToken({ state }) {
      state.token = ''
      localStorage.setItem('token', '')
    },
    // 로그인
    login({ dispatch, state }, credentials) {
      axios({
        url: api.accounts.login(),
        method: 'post',
        data: credentials
      })
        .then(res => {
          const token = res.data.key
          dispatch('saveToken', token)
          // 회원일때랑 비회원일 때 나눠서 진행
          
          router.push({ name: 'MainView' })
        })
        .catch(err => {
          console.error(err.response.data)
          state.authError = err.response.data
        })
    },

    // 로그아웃
    logout({ dispatch }) {
      dispatch('removeToken')
      router.push({ name: 'LoginView'})
    },

    // 비밀번호 수정
    changePassword({ dispatch },credentials) {
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

    // 회원가입 + // 유저 이메일 인증 메일 전송 + 자동로그인
    signup({ dispatch, state }, signdata) {
      axios({
        url: api.accounts.checkemail(),
        method: 'get',
        //딕셔너리 key갑에 따라 변경
        data: signdata.email
      })
        .then(() => {
          axios({
            url: api.accounts.signup_userlist(),
            method: 'post',
            data: signdata
          })
            .then(res => {
              const token = res.data.key
              dispatch('saveToken', token)
              router.push({ name: 'MainView' })
            })
            .catch(err => {
              console.error(err.response.data)
              state.authError = err.response.data
            })
        })
        .catch(err => {
          //추후에 변경
          console.error(err.response.data)
          state.authError = err.response.data
        })
    },

    // 회원 탈퇴
    signout({ dispatch, getters }) {
      axios({
        url: api.accounts.profile_signout(),
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
    // 유저 프로필
    fetchProfile({ state, getters }, { user_pk }) {

      axios({
        url: api.accounts.profile(user_pk),
        method: 'get',
        headers: getters.authHeader,
      })
        .then(res => {
          state.profile = res.data
        })
    },
    // 유저 닉네임 변경
    changeName({ state, getters }, user_pk, namedata ) {
      axios({
        url: api.accounts.nickname_update(user_pk),
        method: 'patch',
        data: namedata,
        // 백엔드 완성하면 테스트(postman)후 변경
        headers: getters.authHeader
      })
       .then(res => {
        state.profile = res.data
        router.push({name: 'ProfileView' , params: user_pk})
        //router.push({name: 'ProfileView' , params: {user_pk: this.user_pk})
       })
       .catch(err => {
        console.error(err.response)
      })
    },
    // 유저 프로필 이미지 변경
    changeimage({ state, getters }, user_pk, image ) {
      axios({
        url: api.accounts.profileimage_update(user_pk),
        method: 'patch',
        data: image,
        // 백엔드 완성하면 테스트(postman)후 변경
        headers: getters.authHeader
      })
       .then(res => {
        state.profile = res.data
        router.push({name: 'ProfileView' , params: user_pk})
        //router.push({name: 'ProfileView' , params: {user_pk: this.user_pk})
       })
       .catch(err => {
        console.error(err.response)
      })
    },
    // 유저가 속한 프로젝트 조회
    fetchProjects({ state ,getters }) {
      axios({
        url: api.movies.list(),
        method: 'get',
        headers: getters.authHeader,
      })
        .then(res => state.projects = res.data)
        .catch(err => console.error(err.response))
    },

  }
})