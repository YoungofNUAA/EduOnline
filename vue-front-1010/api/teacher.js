import request from '@/utils/request'

export default {
  //分页讲师查询  前端
  getTeacherList(page,limit) {
    return request({
      url: `/eduservice/teacherfront/getTeacherFrontList/${page}/${limit}`,
      method: 'post'
    })
  },

  //讲师详情
  getTeacherInfo(id){
    return request({
      url:`/eduservice/teacherfront/getTeacherFrontInfo/${id}`,
      method: 'get'
    })
  }
}