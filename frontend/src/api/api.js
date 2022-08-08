// 로컬호스트 테스트 용 주소
const HOST = 'http://127.0.0.1:8080/'
// 배포용 주소 - push 하기전에 변경해줄것
// const HOST = 'https://i7b309.p.ssafy.io/api'

const ACCOUNTS = 'users/'
const PROJECTS = 'projects/'
const IMAGES = 'images/'
const CONFERENCE = 'api-sessions/'

export default {
  accounts: {
    signup_userlist_signout: () => HOST + ACCOUNTS, //get, post delete
    login: () => HOST + ACCOUNTS + 'login/', //post
    currentUserInfo: () => HOST + ACCOUNTS + 'me/' , // get
    checkemail: email => HOST + ACCOUNTS + `${email}/`, //get
    nickname_update: () => HOST + ACCOUNTS + 'nickname/', //patch
    password_update: () => HOST + ACCOUNTS + 'password/', //patch
    profileimage_update: () => HOST + ACCOUNTS + 'image/', //patch
  },
  projects: {
    projects_create_update: () => HOST + PROJECTS, //get, patch
    projects_list: () => HOST + PROJECTS + 'activate/joind/',
    project_detail: pid => HOST + PROJECTS + `${pid}/`, //get, patch
    image_list_delete: pid => HOST + PROJECTS + `${pid}/`+ IMAGES, // get, delete
    image_save: (pid,user_pk) => HOST + PROJECTS + `${pid}/` + `${user_pk}/` + IMAGES, //post
    add_delete_user: () => HOST + PROJECTS + 'user/', //post, delete
  },
  conferences: {
    conference: () => HOST + CONFERENCE,
  }
}
