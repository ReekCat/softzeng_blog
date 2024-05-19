package cn.softzeng.service.impl;

import cn.softzeng.domain.entity.ArticleTag;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.softzeng.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;
import cn.softzeng.service.ArticleTagService;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2024-03-26 00:27:33
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}

