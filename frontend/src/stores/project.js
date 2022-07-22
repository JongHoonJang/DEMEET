import { defineStore } from "pinia"
import axios from 'axios'
import api from "@/api/api"
//import router from "@/router"


export const useCounterStore = defineStore("project", {
  state: () => ({ count: 0 }),

  getters: {
    doubleCount(state) {
      return state.count * 2;
    }
  },
  actions: {
    // 유저 목록조회 + 유저 초대

    // 유저가 속한 프로젝트 조회
    fetchProjects({ state ,getters}) {
      axios({
        url: api.movies.list(),
        method: 'get',
        headers: getters.authHeader,
      })
        .then(res => state.projects = res.data)
        .catch(err => console.error(err.response))
    },
  },
});