package com.liqun.dilidili.dao.repository;

import com.liqun.dilidili.domain.UserInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserInfoRepository extends ElasticsearchRepository<UserInfo,Long> {
}
