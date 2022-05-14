package com.dhao.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

/**
 * @Author: dhao
 */
public class TestVod {
    public static void main(String[] args) throws  Exception{
        String accessKeyId="LTAI5tF9u1v4S3mVAruKx3Hm";
        String accessKeySecret="ZUS6rHCnXRqLeAXvdAClrtqyKo1Lb7";
        String title="test1";
        String fileName="E:/mypicture/demo.mp4";
        upload(accessKeyId,accessKeySecret,title,fileName);
    }

    public static void upload(String accessKeyId, String accessKeySecret, String title, String fileName){
           UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
            /* 可指定分片上传时每个分片的大小，默认为2M字节 */
//            request.setPartSize(2 * 1024 * 1024L);
            /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
            request.setTaskNum(1);

//             request.setEcsRegionId("cn-shanghai");
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

    public static void getPlayUrl()throws  Exception{
        DefaultAcsClient defaultAcsClient = InitObject.initVodClient("LTAI5tF9u1v4S3mVAruKx3Hm", "ZUS6rHCnXRqLeAXvdAClrtqyKo1Lb7");
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response;
        request.setVideoId("92ca722218bc4270b765524a7713b909");
        response=defaultAcsClient.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        for (GetPlayInfoResponse.PlayInfo info : playInfoList) {
            System.out.println("PlayInfo.PlayURL = " +info.getPlayURL());
        }
        System.out.println("VideoBase.Title = " +response.getVideoBase().getTitle());
    }

    public static void getPlayAuth()throws Exception{
        DefaultAcsClient defaultAcsClient = InitObject.initVodClient("LTAI5tF9u1v4S3mVAruKx3Hm", "ZUS6rHCnXRqLeAXvdAClrtqyKo1Lb7");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        request.setVideoId("92ca722218bc4270b765524a7713b909");
        response=defaultAcsClient.getAcsResponse(request);
        System.out.println(response.getPlayAuth());
    }
}
