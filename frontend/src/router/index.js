import { createRouter, createWebHistory } from 'vue-router'
import MainView from '@/views/main/MainView'
import DetailView from '@/views/main/DetailView'
import ConferenceView from '@/views/conference/ConferenceView'
import ProfileView from '@/views/account/ProfileView'
import LoginView from '@/views/account/LoginView'
import SignupView from '@/views/account/SignupView'
const routes = [
  {
    path: '/',
    name: 'MainView',
    component: MainView
  },
  {
    path: '/project/:project_pk',
    name: 'DetailView',
    component: DetailView
  },
  {
    path: '/conference',
    name: 'ConferenceView',
    component: ConferenceView
  },
  {
    path: '/profile/:user_pk',
    name: 'ProfileView',
    component: ProfileView
  },
  {
    path: '/login',
    name: 'LoginView',
    component: LoginView
  },
  {
    path: '/signup',
    name: 'SignupView',
    component: SignupView
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// router 가드
// 회원인테 맴버인경우
// 회원인데 맴버가 아닌경우
// 비회원인경우


export default router
