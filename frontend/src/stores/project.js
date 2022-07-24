import { defineStore } from "pinia"
import axios from 'axios'
import api from "@/api/api"
//import router from "@/router"


export const useCounterStore = defineStore("project", {
  state: () => ({
    project: {},
    projects: [],
    endprojects: [],
  }),

  getters: {
    project: state => state.project,
    projects: state => state.projects,
    endprojects: state => state.endprojects
  },
  actions: {
    // 프로젝트 상세 조회
    fetchProject({ state ,getters }, project_pk) {
      axios({
        url: api.movies.project_detail_update(project_pk),
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
        url: api.movies.projectslist(user_pk),
        method: 'get',
        headers: getters.authHeader,
      })
        .then(res => {
          state.projects = res.data
        })
        .catch(err => console.error(err.response))
    },


    // 유저 목록조회 + 유저 초대


    // 프로젝트 이미지 리스트


    // 프로젝트 이미지 삭제


    // 프로젝트 이미지 저장

  },
})