import { defineStore } from "pinia"
import axios from 'axios'
import api from "@/api/api"
import router from "@/router"


export const useProjectStore = defineStore("project", {
  state: () => ({
    project: {},
    projects: [],
  }),

  getters: {
    project: state => state.project,
    projects: state => state.projects,

  },
  actions: {
    // 프로젝트 상세 조회
    fetchProject({ state ,getters }, project_pk) {
      axios({
        url: api.projects.project_detail_update(project_pk),
        method: 'get',
        headers: getters.authHeader,
      })
        .then(res => {
          state.project = res.data
        })
        .catch(err => console.error(err.response))
    }, 

    // 유저가 속한 프로젝트 조회
    fetchProjects({ state, getters }, user_pk) {
      axios({
        url: api.projects.projectslist(user_pk),
        method: 'get',
        headers: getters.authHeader,
      })
        .then(res => {
          state.projects = res.data
        })
        .catch(err => console.error(err.response))
    },

      

    // 유저 초대
    addUser( {state, getters}, {project_pk, user_pk}) {
      axios({
        url: api.projects.add_user(project_pk, user_pk),
        method: 'post',
        data: {},
        headers: getters.authHeader,
      })
        .then(res => {
          state.project = res.data
        })
        .catch(err => console.error(err.response))
    },

    // 프로젝트 이미지 리스트


    // 프로젝트 이미지 삭제
    deleteImage({ state, getters },project_pk) {
      if (confirm('정말 삭제하시겠습니까?')) {
        axios({
          url: api.projects.image_list_delete(project_pk),
          method: 'delete',
          data: {},
          headers: getters.authHeader,
        })
          .then(res => {
            state.project = res.data
            router.go({ name: 'DetailView' })
          })
          .catch(err => console.error(err.response))
      }
    },

    // 프로젝트 이미지 저장
    saveImage({ state, getters }, project_pk) {
      axios({
        url: api.projects.image_save(project_pk),
        method: 'post',
        headers: getters.authHeader,
      })
        .then(res => {
          state.project = res.data
          router.go({ name: 'DetailView' })
        })
        .catch(err => console.error(err.response))
    }
  },
})