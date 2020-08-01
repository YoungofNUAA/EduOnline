import request from '@/utils/request'

export default{
  addCourseInfo(courseInfo) {
    return request({
      url: '/eduservice/course/addCourseInfo',
      method: 'post',
      data:courseInfo
    })
  },
  getListTeacher(){
    return request({
      url:'/eduservice/teacher/findAll',
      method:'get'
    })
  },
  getCourseInfo(id){
    return request({
      url:'/eduservice/course/getCourseInfo/'+id,
      method:'get'
    })
  },
  updateCourseInfo(courseInfo){
    return request({
      url:'/eduservice/course/updateCourseInfo',
      method:'post',
      data:courseInfo
    })
  },
  //课程确认信息
  getPublishCourseInfo(courseId){
    return request({
      url:'/eduservice/course/getPublishCourseInfo/'+courseId,
      method:'get',
    })
  },
  //课程最终发布
  publishCourse(courseId){
    return request({
      url:'/eduservice/course/publishCourse/'+courseId,
      method:'post',
    })
  },
  //查询所有课程
  getListCourse(){
    return request({
      url:'/eduservice/course',
      method:'get',
    })
  },
  //删除课程
  deleteCourseById(courseId){
    return request({
      url:'/eduservice/course/'+courseId,
      method:'delete',
    })
  }

}
