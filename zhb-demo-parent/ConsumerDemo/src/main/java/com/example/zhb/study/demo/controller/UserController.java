package com.example.zhb.study.demo.controller;
 
import com.alibaba.dubbo.config.annotation.Reference;
import com.example.zhb.study.demo.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 


@RestController
@RequestMapping("/testBoot")
public class UserController {
 

    /**
     * 使用dubbo的注解 com.alibaba.dubbo.config.annotation.Reference。进行远程调用service
     */
    @Reference
    private UserService userService;

    /**
     * http://localhost:8062/testBoot/getUser/1
     * @param id
     * @return
     */
    @RequestMapping("getUser/{id}")
    public String GetUser(@PathVariable int id){
        return userService.SelectById(id).toString();
    }
}