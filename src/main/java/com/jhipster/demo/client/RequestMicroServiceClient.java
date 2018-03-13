package com.jhipster.demo.client;

import com.jhipster.demo.web.rest.vm.PersonVM;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 加 @AuthorizedFeignClient 注解，在 GateWay 下调用微服务 MicroServiceB
 * name 是 MicroServiceB 注册在[注册中心]的实例名字
 */
@AuthorizedFeignClient(name = "MyMicroServiceB")
public interface RequestMicroServiceClient {


    @GetMapping("/api/demo/string-b")
    public String getStringFromB();

    /**
     * var 对应 MicroServiceB 的 RestAPI
     */
    @PutMapping("/api/demo/string-b/{var}")
    public String setStringToB(@PathVariable("var") String var);

    /**
     * MicroServiceB 返回的是 List<Person> , 则需要在 MicroServiceA 新建一个 VM 来接受 MicroServiceB 返回的数据
     */
    @RequestMapping(method = RequestMethod.GET, value = "/api/demo/person-list")
    public List<PersonVM> getPersonListFromB();

    /** 不同访问权限测试 ***********************************************/
    @GetMapping("/api/demo/secured/string-b")
    public String getStringSecuredToB();

    @PutMapping("/api/demo/secured/string-b/{var}")
    public String setStringSecuredToB(@PathVariable("var") String var);

}
