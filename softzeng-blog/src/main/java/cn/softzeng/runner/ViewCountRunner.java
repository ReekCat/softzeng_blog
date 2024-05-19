package cn.softzeng.runner;

import cn.softzeng.domain.entity.Article;
import cn.softzeng.mapper.ArticleMapper;
import cn.softzeng.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void run(String... args) throws Exception {
        //查询博客信息 id 和viewCount
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article1 -> article1.getId().toString(), article -> article.getViewCount().intValue()));
        //存储到redis中
        redisCache.setCacheMap("article:viewCount",viewCountMap);

    }
}
