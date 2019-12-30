package com.xinyu.simple.web.controller;

import com.alibaba.fastjson.JSON;
import com.xinyu.simple.common.constant.NoLogin;
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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
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

    //redis存储key，使用冒号隔开，方便按照文件夹的方式管理key
    private static final String REDIS_KE_PREF = "standardProject:dictCOde:";

    @Autowired
    private IDictCodeService dictCodeService;

    /*redis引入步骤：①添加redis依赖；②配置application文件；③注入StringRedisTemplate对象*/
    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     *@Description
     *      ①测试redis缓存方法功能，@Cacheable
     *      ②测试自定义注解使用，在拦截器中注解解释器，@NoLogin
     *      ③测试特殊类型的入参转换问题，@RequestBody QueryForm中的LocalDateTime属性接收数据（MappingConverterAdapter.java）
     **/
    @PostMapping("/queryDictBycode")
    @ResponseBody
    @ApiOperation("根据编码查询字典数据")
    //@Cacheable(cacheNames = {"queryDictBycode"},key = "#form.dictCode")
    @NoLogin
    public ResponseVo<List<DictCodeVo>> queryDictBycode(@RequestBody QueryForm form, HttpServletRequest request){
        //先对参数进行校验
        if(form==null || StringUtils.isBlank(form.getDictCode()))
            return ResponseVo.failResponse("409","参数异常");

        String value = redisTemplate.opsForValue().get(REDIS_KE_PREF + form.getDictCode());
        if(StringUtils.isBlank(value)){
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
            //数据缓存至redis
            value = JSON.toJSONString(voList);
            redisTemplate.opsForValue().set(REDIS_KE_PREF + form.getDictCode(), value);
        }
        List<DictCodeVo> dictCodeVos = JSON.parseArray(value, DictCodeVo.class);
        return ResponseVo.successResponse(dictCodeVos);
    }


    /**
     *@Description  测试自定义Converter类型转换器（MappingConverterAdapter.java），转换Local时间类参数接收
     **/
    @GetMapping("/queryDictBycode2")
    @ResponseBody
    @ApiOperation("根据编码查询字典数据2")
    @NoLogin
    public ResponseVo<List<DictCodeVo>> queryDictBycode2(QueryForm form,
                                                         @RequestParam("createDate1") LocalDateTime createDate1,
                                                         @RequestParam("createDate2") LocalDate createDate2,
                                                         @RequestParam("createDate3") LocalTime createDate3,
                                                         @RequestParam("createDate4") Date createDate4,
                                                         @RequestParam("code") String code){
        //先对参数进行校验
        if(StringUtils.isBlank(code))
            return ResponseVo.failResponse("409","参数异常");

        //调用业务层
                List<DictCodeDto> dictCodeDtos = dictCodeService.queryByCode(code);
        List<DictCodeVo> voList = new ArrayList<>();
        if(dictCodeDtos!=null && dictCodeDtos.size()>0)
            dictCodeDtos.forEach(dto -> {
                DictCodeVo vo = new DictCodeVo();
                BeanUtils.copyProperties(dto,vo);
                voList.add(vo);
            });
        return ResponseVo.successResponse(voList);
    }
}
