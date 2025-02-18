package com.liqun.dilidili.dao;


import com.liqun.dilidili.domain.Video;
import com.liqun.dilidili.domain.VideoTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoDao {

    Integer addVideos(Video video);

    Integer batchAddVideoTags(List<VideoTag> videoTags);
}
