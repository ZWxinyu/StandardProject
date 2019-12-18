package com.xinyu.simple.web.controller;

import com.xinyu.simple.common.constant.WebConstants;
import com.xinyu.simple.common.vo.ResponseVo;
import com.xinyu.simple.service.IDictCodeService;
import com.xinyu.simple.service.dto.DictCodeDto;
import com.xinyu.simple.web.controller.form.QueryForm;
import com.xinyu.simple.web.controller.vo.DictCodeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *@Author xinyu
 *@Description  数据字典controller类
 *@Date 10:38 2019/12/18
 **/
@RestController
@RequestMapping(WebConstants.API_PREFIX+"/dictCode")
@Api("数据字典controller类")
public class DictCodeController {
    private static final Logger logger = LoggerFactory.getLogger(DictCodeController.class);
    @Autowired
    private IDictCodeService dictCodeService;

    @PostMapping("/queryDictBycode")
    @ResponseBody
    @ApiOperation("根据编码查询字典数据")
    public ResponseVo<List<DictCodeVo>> queryDictBycode(@RequestBody QueryForm form, HttpServletRequest request){
        //先对参数进行校验
        if(form==null || StringUtils.isBlank(form.getDictCode()))
            return ResponseVo.failResponse("409","参数异常");

        //调用业务层
        List<DictCodeDto> dictCodeDtos = dictCodeService.queryByCode(form.getDictCode());
        List<DictCodeVo> voList = new ArrayList<>();
        if(dictCodeDtos!=null && dictCodeDtos.size()>0){
            dictCodeDtos.forEach(dto -> {
                DictCodeVo vo = new DictCodeVo();
                BeanUtils.copyProperties(dto,vo);
                voList.add(vo);
            });
        }
        return ResponseVo.successResponse(voList);
    }
}
