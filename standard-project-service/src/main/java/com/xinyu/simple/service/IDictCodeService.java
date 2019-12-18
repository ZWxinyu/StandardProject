package com.xinyu.simple.service;

import com.xinyu.simple.service.dto.DictCodeDto;

import java.util.List;

/**
 *@Author xinyu
 *@Description  字典表业务实现类接口
 *@Date 10:30 2019/12/18
 **/
public interface IDictCodeService {

    /**
     *@Author xinyu
     *@Description  根据字典编码，查找字典数据
     *@Date 10:37 2019/12/18
     **/
    List<DictCodeDto> queryByCode(String dictCode);
}
