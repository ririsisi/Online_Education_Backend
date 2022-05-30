package com.ririsisi.eduservice.controller;

import com.ririsisi.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ririsisi
 * @Version 1.0
 */
@Api(description = "系统登录管理")
@RestController
@RequestMapping("eduservice/user")
@CrossOrigin //解决跨域
public class EduLoginController {

    // login
    @PostMapping("login")
    public R login() {
        return R.ok().data("token","admin");
    }

    // info
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","http://th.bing.com/th/id/R.dc1b3ababb75998d7abd937e683f53b0?rik=%2fCAVJVFDEyX%2bkA&riu=http%3a%2f%2fwww.qqtouxiang.com%2fd%2ffile%2ftupian%2fmx%2f20170801%2fjiygmqsmvdm1w.jpg&ehk=gguxcy2VCqZInaHhlBhhmVIbeW94e1TvvjTkPiwRr3o%3d&risl=&pid=ImgRaw&r=0");
    }
}
