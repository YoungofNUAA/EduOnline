package com.young.educms.service.impl;

import com.young.educms.entity.CrmBanner;
import com.young.educms.mapper.CrmBannerMapper;
import com.young.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-25
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public List<CrmBanner> selectAllBanner() {
        List<CrmBanner> banners = baseMapper.selectList(null);
        return banners;
    }
}
