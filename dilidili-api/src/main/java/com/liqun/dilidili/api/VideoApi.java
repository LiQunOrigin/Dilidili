package com.liqun.dilidili.api;

import com.liqun.dilidili.api.support.UserSupport;
import com.liqun.dilidili.domain.JsonResponse;
import com.liqun.dilidili.domain.PageResult;
import com.liqun.dilidili.domain.Video;
import com.liqun.dilidili.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.api
 * @className: VideoApi
 * @author: LiQun
 * @description: 视频接口
 * @data 2025/2/18 16:17
 */

@RestController
public class VideoApi {

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserSupport userSupport;

    @PostMapping("/videos")
    public JsonResponse<String> addVideos(@RequestBody Video video){
        Long userId = userSupport.getCurrentUserId();
        video.setUserId(userId);
        videoService.addVideos(video);
        return JsonResponse.success();
    }

    @GetMapping("/videos")
    public JsonResponse<PageResult<Video>> getListVideos(Integer size, Integer no, String area){
        PageResult<Video> result = videoService.getListVideos(size, no, area);
        return new JsonResponse<>(result);
    }

    @GetMapping("/videos-slices")
    public void viewVideoOnlineBySlices(HttpServletRequest request,
                                        HttpServletResponse response,
                                        String url) throws Exception {
        videoService.viewVideoOnlineBySlices(request,response,url);
    }

    //点赞视频
    @PostMapping("/videos-likes")
    public JsonResponse<String> addVideoLikes(@RequestParam Long videoId){
        Long userId = userSupport.getCurrentUserId();
        videoService.addVideoLikes(videoId,userId);
        return JsonResponse.success();
    }

    //取消点赞视频
    @DeleteMapping("/videos-likes")
    public JsonResponse<String> deleteVideoLikes(@RequestParam Long videoId){
        Long userId = userSupport.getCurrentUserId();
        videoService.deleteVideoLikes(videoId,userId);
        return JsonResponse.success();
    }

    //获取视频点赞数
    @GetMapping("/videos-likes")
    public JsonResponse<Map<String,Object>> getVideoLikes(@RequestParam Long videoId){
        Long userId = null;
        try {
            userId = userSupport.getCurrentUserId();
        }catch (Exception ignored){
            //未登录
        }
        Map<String,Object> result = videoService.getVideoLikes(videoId,userId);
        return new JsonResponse<>(result);
    }

}
