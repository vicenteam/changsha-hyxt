package com.stylefeng.guns.modular.main.service.impl;

import com.stylefeng.guns.modular.system.model.MemberCard;
import com.stylefeng.guns.modular.system.dao.MemberCardMapper;
import com.stylefeng.guns.modular.main.service.IMemberCardService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员卡关联关系表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-08-10
 */
@Service
public class MemberCardServiceImpl extends ServiceImpl<MemberCardMapper, MemberCard> implements IMemberCardService {

}
