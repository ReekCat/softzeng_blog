package cn.softzeng.service;

import cn.softzeng.domain.ResponseResult;
import cn.softzeng.domain.entity.Link;
import cn.softzeng.domain.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2024-03-16 10:29:05
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
    //分页查询友链
    PageVo selectLinkPage(Link link, Integer pageNum, Integer pageSize);
}

