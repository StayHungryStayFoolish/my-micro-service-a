package com.jhipster.demo.client;

import com.jhipster.demo.web.rest.vm.PersonVM;
import com.jhipster.demo.web.rest.vm.UserVM;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 加 @AuthorizedFeignClient 注解，在 GateWay 下调用微服务 MicroServiceB
 * name 是 MicroServiceB 注册在[注册中心]的实例名字
 */
@AuthorizedUserFeignClient(name = "MyUaa")
public interface RequestUaaClient {

    @GetMapping("/api/account")
    public UserVM getAccount();

    @GetMapping("/api/authenticate")
    public String getAuthenticate();

}
