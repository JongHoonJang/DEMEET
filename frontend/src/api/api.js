const HOST = 'http://127.0.0.1:8080/'

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
    // projectslist: () => HOST + ACCOUNTS + `${user_pk}/` + PROJECTS, //get
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
