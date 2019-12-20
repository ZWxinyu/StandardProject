package com.xinyu.simple.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyu.simple.dao.entity.DictCode;
import com.xinyu.simple.dao.mapper.DictCodeMapper;
import com.xinyu.simple.service.IDictCodeService;
import com.xinyu.simple.service.dto.DictCodeDto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *@Author xinyu
 *@Description 字典表业务实现类
 *@Date 10:31 2019/12/18
 **/
@Service
public class DictCodeServiceImpl implements IDictCodeService {
    private static final Logger logger = LoggerFactory.getLogger(DictCodeServiceImpl.class);

    @Autowired
    private DictCodeMapper dictCodeMapper;

    @Override
    @Transactional
    @Cacheable(cacheNames = {"dictCode"},key = "#dictCode")
    public List<DictCodeDto> queryByCode(String dictCode) {
        if(StringUtils.isBlank(dictCode))
            return null;
        QueryWrapper wrapper = new QueryWrapper();
        DictCode dict = new DictCode();
        dict.setDictCode(dictCode);
        wrapper.setEntity(dict);
        List<DictCode> list = dictCodeMapper.selectList(wrapper);
        List<DictCodeDto> dtoList = new ArrayList<>();
        if(list!=null && list.size()>0){
            list.forEach(entity->{
                DictCodeDto dto = new DictCodeDto();
                BeanUtils.copyProperties(entity,dto);
                dtoList.add(dto);
            });
        }
        return dtoList;
    }
}
