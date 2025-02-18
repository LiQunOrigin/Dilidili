package com.liqun.dilidili.dao;


import com.liqun.dilidili.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface VideoDao {

    Integer addVideos(Video video);

    Integer batchAddVideoTags(List<VideoTag> videoTags);

    Integer pageCountVideos(Map<String, Object> params);

    List<Video> pageListVideos(Map<String, Object> params);

    Video getVideoById(Long id);

    VideoLike getVideoLikeByVideoIdAndUserId(@Param("videoId") Long videoId, @Param("userId") Long userId);

    void addVideoLikes(VideoLike videoLike);

    //为什么用Param注解标识? 因为mybatis会自动封装参数，如果直接使用参数名，mybatis会报错
    //自动封装参数是什么? mybatis会自动封装参数，如果参数是简单类型，mybatis会自动封装为map，如果参数是复杂类型，mybatis会自动封装为bean
    void deleteVideoLikes(@Param("videoId") Long videoId, @Param("userId") Long userId);

    Long getVideoLikes(Long videoId);

    Long getVideoCollections(Long videoId);

    void addVideoCollections(VideoCollection videoCollection);

    void deleteVideoCollections(@Param("videoId") Long videoId, @Param("userId") Long userId);

    VideoCollection getVideoCollectionsByVideoIdAndUserId(@Param("videoId") Long videoId, @Param("userId") Long userId);

    VideoCoin getVideoCoinsByVideoIdAndUserId(
            @Param("videoId") Long videoId,
            @Param("userId") Long userId
    );

    void addVideoCoins(VideoCoin videoCoin);

    void updateVideoCoins(VideoCoin videoCoin);


    Long getVideoCoinsAmount(@Param("videoId") Long videoId);

    Integer addVideoComments(VideoComment videoComment);

    Integer pageCountVideoComments(Map<String, Object> params);

    List<VideoComment> pageListVideoComments(Map<String, Object> params);

    List<VideoComment> batchGetVideoCommentsByRootIds(List<Long> parentIdList);

    Video getVideoDetails(Long videoId);
}
