package com.liqun.dilidili.api;

import com.liqun.dilidili.api.support.UserSupport;
import com.liqun.dilidili.domain.*;
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
    public JsonResponse<String> addVideos(@RequestBody Video video) {
        Long userId = userSupport.getCurrentUserId();
        video.setUserId(userId);
        videoService.addVideos(video);
        return JsonResponse.success();
    }

    @GetMapping("/videos")
    public JsonResponse<PageResult<Video>> getListVideos(Integer size, Integer no, String area) {
        PageResult<Video> result = videoService.getListVideos(size, no, area);
        return new JsonResponse<>(result);
    }

    @GetMapping("/videos-slices")
    public void viewVideoOnlineBySlices(HttpServletRequest request,
                                        HttpServletResponse response,
                                        String url) throws Exception {
        videoService.viewVideoOnlineBySlices(request, response, url);
    }

    //点赞视频
    @PostMapping("/videos-likes")
    public JsonResponse<String> addVideoLikes(@RequestParam Long videoId) {
        Long userId = userSupport.getCurrentUserId();
        videoService.addVideoLikes(videoId, userId);
        return JsonResponse.success();
    }

    //取消点赞视频
    @DeleteMapping("/videos-likes")
    public JsonResponse<String> deleteVideoLikes(@RequestParam Long videoId) {
        Long userId = userSupport.getCurrentUserId();
        videoService.deleteVideoLikes(videoId, userId);
        return JsonResponse.success();
    }

    //获取视频点赞数
    @GetMapping("/videos-likes")
    public JsonResponse<Map<String, Object>> getVideoLikes(@RequestParam Long videoId) {
        Long userId = null;
        try {
            userId = userSupport.getCurrentUserId();
        } catch (Exception ignored) {
            //未登录
        }
        Map<String, Object> result = videoService.getVideoLikes(videoId, userId);
        return new JsonResponse<>(result);
    }

    //收藏视频
    @PostMapping("/videos-collections")
    public JsonResponse<String> addVideoCollections(@RequestParam VideoCollection videoCollection) {
        Long userId = userSupport.getCurrentUserId();
        videoService.addVideoCollections(videoCollection, userId);
        return JsonResponse.success();
    }

    //取消收藏视频
    @DeleteMapping("/videos-collections")
    public JsonResponse<String> deleteVideoCollections(@RequestParam Long videoId) {
        Long userId = userSupport.getCurrentUserId();
        videoService.deleteVideoCollections(videoId, userId);
        return JsonResponse.success();
    }

    //获取视频收藏数
    @GetMapping("/videos-collections")
    public JsonResponse<Map<String, Object>> getVideoCollections(@RequestParam Long videoId) {
        Long userId = null;
        try {
            userId = userSupport.getCurrentUserId();
        } catch (Exception ignored) {
            //未登录
        }
        Map<String, Object> result = videoService.getVideoCollections(videoId, userId);
        return new JsonResponse<>(result);
    }

    //视频投币
    @PostMapping("/videos-coins")
    public JsonResponse<String> addVideoCoins(@RequestBody VideoCoin videoCoin) {
        Long userId = userSupport.getCurrentUserId();
        videoService.addVideoCoins(videoCoin, userId);
        return JsonResponse.success();
    }

    //获取视频投币数
    @GetMapping("/videos-coins")
    public JsonResponse<Map<String, Object>> getVideoCoins(@RequestParam Long videoId) {
        Long userId = null;
        try {
            userId = userSupport.getCurrentUserId();
        } catch (Exception ignored) {
            //未登录
        }
        Map<String, Object> result = videoService.getVideoCoins(videoId, userId);
        return new JsonResponse<>(result);
    }

    //添加视频评论
    @PostMapping("/videos-comments")
    public JsonResponse<String> addVideoComments(@RequestBody VideoComment videoComment) {
        Long userId = userSupport.getCurrentUserId();
        videoService.addVideoComments(videoComment, userId);
        return JsonResponse.success();
    }

    //分页查询视频评论
    @GetMapping("/videos-comments")
    public JsonResponse<PageResult<VideoComment>> pageListVideoComments(
            @RequestParam Integer size,
            @RequestParam Integer no,
            @RequestParam Long videoId) {
        PageResult<VideoComment> result = videoService.pageListVideoComments(size, no, videoId);
        return new JsonResponse<>(result);
    }

    //获取视频详情
    @GetMapping("/videos-detail")
    public JsonResponse<Map<String,Object>> getVideoDetails(@RequestParam Long videoId) {
        Map<String,Object> result = videoService.getVideoDetails(videoId);
        return new JsonResponse<>(result);
    }





}
