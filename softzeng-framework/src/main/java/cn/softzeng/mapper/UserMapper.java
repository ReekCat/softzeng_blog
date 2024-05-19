package cn.softzeng.mapper;

import cn.softzeng.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-16 11:10:33
 */
@Component
public interface UserMapper extends BaseMapper<User> {

}
