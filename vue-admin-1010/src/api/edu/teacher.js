import request from '@/utils/request'

export default{
  getTeacherListPage(current, limit, teacherQuery) {
    return request({
      url: `/eduservice/teacher/pageTeacherCondition/${current}/${limit}`,
      method: 'post',
      // params
      data:teacherQuery
    })
  },
  deleteTeacherId(id){
    return request({
      url: `/eduservice/teacher/${id}`,
      method: 'delete'
      // params
    })
  },
  addTeacher(teacher){
    return request({
      url: `/eduservice/teacher/addTeacher`,
      method:'post',
      data:teacher
    })
  },
  getTeacherInfo(id){
    return request({
      url: `/eduservice/teacher/getTeacher/${id}`,
      method:'get'
    })
  },
  //修改讲师
  updateTeacherInfo(teacher){
    return request({
      url: `/eduservice/teacher/updateTeacher`,
      method:'post',
      data:teacher
    })
  }
}
