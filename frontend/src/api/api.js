const HOST = 'http://127.0.0.1:8000/api/v1/'

const ACCOUNTS = 'users/'
const PROJECTS = 'projects/'
const IMAGES = 'images/'

export default {
  accounts: {
    signup_userlist_signout: () => HOST + ACCOUNTS, //get, post delete
    login: () => HOST + ACCOUNTS + 'login/', //post
    currentUserInfo: () => HOST + ACCOUNTS + 'user/me/' , // get
    checkemail: () => HOST + ACCOUNTS + 'email/', //get
    nickname_update: () => HOST + ACCOUNTS + 'nickname/', //patch
    password_update: () => HOST + ACCOUNTS + 'password/', //patch
    profileimage_update: () => HOST + ACCOUNTS + 'image/', //patch
    // projectslist: () => HOST + ACCOUNTS + `${user_pk}/` + PROJECTS, //get
  },
  projects: {
    projects_list_create: () => HOST + PROJECTS, //get, post
    project_detail_update: project_pk => HOST + PROJECTS + `${project_pk}/`, //get, patch
    image_list_delete: project_pk => HOST + PROJECTS + `${project_pk}/`+ IMAGES, // get, delete
    image_save: (project_pk,user_pk) => HOST + PROJECTS + `${project_pk}/` + `${user_pk}/` + IMAGES, //post
    add_user: (project_pk, user_pk) => HOST + PROJECTS + `${project_pk}/` + `${user_pk}/`, //post
  }
}
