package com.liqun.dilidili.service;

import com.liqun.dilidili.dao.VideoDao;
import com.liqun.dilidili.domain.PageResult;
import com.liqun.dilidili.domain.Video;
import com.liqun.dilidili.domain.VideoLike;
import com.liqun.dilidili.domain.VideoTag;
import com.liqun.dilidili.domain.exception.ConditionException;
import com.liqun.dilidili.service.utils.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.service
 * @className: VideoService
 * @author: LiQun
 * @description: TODO
 * @data 2025/2/18 16:18
 */
@Service
public class VideoService {

    @Autowired
    private VideoDao videoDao;

    @Autowired
    private FastDFSUtil fastDFSUtil;

    @Transactional
    public void addVideos(Video video) {
        Date now = new Date();
        video.setCreateTime(new Date());
        videoDao.addVideos(video);
        Long videoId = video.getId();
        List<VideoTag> tagList = video.getVideoTagList();
        tagList.forEach(item->{
            item.setCreateTime(now);
            item.setVideoId(videoId);
        });
        videoDao.batchAddVideoTags(tagList);
    }

    public PageResult<Video> getListVideos(Integer size, Integer no, String area) {
        if(size == null || no == null) {
            throw new ConditionException("参数异常");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("start", (no - 1) * size);
        params.put("limit", size);
        params.put("area", area);
        List<Video> list = new ArrayList<>();
        Integer total = videoDao.pageCountVideos(params);
        if(total > 0) {
            list = videoDao.pageListVideos(params);
        }
        return new PageResult<>(total, list);
    }

    public void viewVideoOnlineBySlices(HttpServletRequest request,
                                        HttpServletResponse response,
                                        String url) throws Exception {
        fastDFSUtil.viewVideoOnlineBySlices(request, response, url);
    }

    public void addVideoLikes(Long videoId, Long userId) {
        Video video = videoDao.getVideoById(videoId);
        if(video == null) {
            throw new ConditionException("非法视频! ");
        }
        VideoLike videoLike = videoDao.getVideoLikeByVideoIdAndUserId(videoId, userId);
        if(videoLike != null) {
            throw new ConditionException("已点赞视频! ");
        }
        videoLike = new VideoLike();
        videoLike.setVideoId(videoId);
        videoLike.setUserId(userId);
        videoLike.setCreateTime(new Date());
        videoDao.addVideoLikes(videoLike);
    }

    public void deleteVideoLikes(Long videoId, Long userId) {
        videoDao.deleteVideoLikes(videoId, userId);
    }


    public Map<String, Object> getVideoLikes(Long videoId, Long userId) {
        Long count = videoDao.getVideoLikes(videoId);
        VideoLike videoLike = videoDao.getVideoLikeByVideoIdAndUserId(videoId, userId);
        boolean like = videoLike != null;
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("like", like);
        return result;
    }
}
