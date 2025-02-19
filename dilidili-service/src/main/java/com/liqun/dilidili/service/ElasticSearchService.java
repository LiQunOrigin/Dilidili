package com.liqun.dilidili.service;

import com.liqun.dilidili.dao.repository.UserInfoRepository;
import com.liqun.dilidili.dao.repository.VideoRepository;
import com.liqun.dilidili.domain.UserInfo;
import com.liqun.dilidili.domain.Video;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.service
 * @className: ElasticSearchService
 * @author: LiQun
 * @description: es 服务
 * @data 2025/2/19 15:17
 */
@Service
public class ElasticSearchService {
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public void addUserInfo(UserInfo userInfo){
        userInfoRepository.save(userInfo);
    }

    public void addVideo(Video video){
        videoRepository.save(video);
    }


    public List<Map<String, Object>> getContent(String keyword,Integer pageNo,Integer pageSize) throws IOException {
        String[] indices = {"videos", "user-infos"};
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //分页
        searchSourceBuilder.from(pageNo - 1);
        searchSourceBuilder.size(pageSize);
        MultiMatchQueryBuilder multiMatchQueryBuilder =
                QueryBuilders.multiMatchQuery(keyword, "title","nick", "description");
        searchSourceBuilder.query(multiMatchQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //高亮显示
        String[] array = {"title","nick","description"};
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        for(String key : array) {
            highlightBuilder.fields().add(new HighlightBuilder.Field(key));
        }
        highlightBuilder.requireFieldMatch(false);//多个字段高亮显示时,要设置为false
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);
        //搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        List<Map<String,Object>> arrayList = new ArrayList<>();
        for(SearchHit hit : searchResponse.getHits()) {
            //获取高亮显示的内容
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            Map<String, Object> stringObjectMap = hit.getSourceAsMap();
            for(String key : array) {
                HighlightField field = highlightFields.get(key);
                if(field != null) {
                    Text[] fragments = field.fragments();
                    String str = Arrays.toString(fragments);
                    str = str.substring(1, str.length() - 1);
                    stringObjectMap.put(key, str);
                }
            }
            arrayList.add(stringObjectMap);
        }
        return arrayList;

    }

    public Video getVideos(String keyword) {
        return videoRepository.findByTitleLike(keyword);
    }

    public void deleteVideos() {
        videoRepository.deleteAll();
    }
}
