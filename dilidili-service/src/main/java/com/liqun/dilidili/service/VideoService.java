package com.liqun.dilidili.service;

import com.liqun.dilidili.dao.VideoDao;
import com.liqun.dilidili.domain.Video;
import com.liqun.dilidili.domain.VideoTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
}
