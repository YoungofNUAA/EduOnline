import request from '@/utils/request'

export default {
  //分页讲师查询  前端
  getCourseList(page,limit,searchObj) {
    return request({
      url: `/eduservice/coursefront/getFrontCourseList/${page}/${limit}`,
      method: 'post',
      data:searchObj
    })
  },

  //二级联动
  getAllSubject(){
    return request({
      url:`/eduservice/subject/getAllSubject`,
      method: 'get'
    })
  },
  getCourseInfo(id){
    return request({
      url:`/eduservice/coursefront/getFrontCourseInfo/${id}`,
      method: 'get'
    })
  }
}