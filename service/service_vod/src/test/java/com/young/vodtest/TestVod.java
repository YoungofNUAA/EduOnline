package com.young.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class TestVod {
    public static void main(String[] args) throws Exception{
        String accessKeyId = "LTAI4GFeVgwgc1f5WgvWMcWZ";
        String accessKeySecret = "KiSuaKwRruJuWFbEo5lOPk1zJ0PTVB";
        String title = "Young_TestVideo";
        String fileName = "F:/尚硅谷Java项目/在线教育--谷粒学院/项目资料/1-阿里云上传测试视频/6 - What If I Want to Move Faster.mp4";
        testUploadVideo(accessKeyId,accessKeySecret,title,fileName);
    }

    //本地文件上传
    public static void testUploadVideo(String accessKeyId, String accessKeySecret, String title, String fileName){
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    public static void getPlayerAthu() throws Exception{
        DefaultAcsClient client = InitObject.initVodClient("LTAI4GFeVgwgc1f5WgvWMcWZ", "KiSuaKwRruJuWFbEo5lOPk1zJ0PTVB");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        request.setVideoId("6b9bc2100ea643e997543441ca477b58");
        response = client.getAcsResponse(request);
        System.out.println("PlayerAthu: = "+ response.getPlayAuth());
    }

    public static void getPlayURL() throws Exception{
        //获取视频播放地址
        DefaultAcsClient client = InitObject.initVodClient("LTAI4GFeVgwgc1f5WgvWMcWZ","KiSuaKwRruJuWFbEo5lOPk1zJ0PTVB");

        GetPlayInfoRequest request = new GetPlayInfoRequest();

        GetPlayInfoResponse response = new GetPlayInfoResponse();

        request.setVideoId("6b9bc2100ea643e997543441ca477b58");

        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();

        for(GetPlayInfoResponse.PlayInfo playInfo:playInfoList){
            System.out.print("PlayInfo.PlayURL=" + playInfo.getPlayURL()+'\n');
        }
        System.out.println("Video.title = " + response.getVideoBase().getTitle()+'\n');
    }
}
