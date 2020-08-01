import request from '@/utils/request'

export default{
  getVideo(id) {
    return request({
      url: '/eduservice/video/getVideoInfo/'+id,
      method: 'get'
    })
  },

  //添加小节
  addVideo(video) {
    return request({
      url: '/eduservice/video/addVideo/',
      method: 'post',
      data:video
    })
  },
  //修改小节
  updateVideo(video) {
    return request({
      url: '/eduservice/chapter/updateVideo/',
      method: 'post',
      data:video
    })
  },
  //删除
  deleteVideo(id) {
    return request({
      url: '/eduservice/video/'+id,
      method: 'delete'
    })
  },
  deleteAliyunVideo(id) {
    return request({
      url: '/eduvod/video/removeAliyunVideo/'+id,
      method: 'delete'
    })
  }
}