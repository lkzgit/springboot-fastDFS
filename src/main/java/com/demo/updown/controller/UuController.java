package com.demo.updown.controller;


import com.demo.updown.entity.NjgbOtherDic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author root
 * @since 2020-04-29
 */
@Controller
@RequestMapping("/uu")
public class UuController {

    @Autowired
    private RestTemplate restTemplate;

        //测试跨服务
    @RequestMapping("/findBylist")
    public String find(){

        String url = "http://localhost:8500/api/dic/findlist";
        // return restTemplate.postForObject(url, paramMap, String.class);
        return restTemplate.getForObject(url,String.class);


    }
}
