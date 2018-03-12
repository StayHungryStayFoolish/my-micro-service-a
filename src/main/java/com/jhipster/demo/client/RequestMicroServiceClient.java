package com.jhipster.demo.client;

import com.jhipster.demo.web.rest.vm.PersonVM;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 加 @AuthorizedFeignClient 注解，在 GateWay 下调用微服务 MicroServiceB
 * name 是 MicroServiceB 注册在[注册中心]的实例名字
 */
@AuthorizedFeignClient(name = "MyMicroServiceB")
public interface RequestMicroServiceClient {

    /**
     * @RequestMapping 注解，路由到 MicroServiceB 上，method 对应 MicroServiceB 的方法
     * value 对应 MicroServiceB 的 RestAPI
     */
    @RequestMapping(method = RequestMethod.GET, value = "/api/demo/string-b")
    public String getStringFromB();

    @RequestMapping(method = RequestMethod.PUT, value = "/api/demo/string-b/{var}")
    public String setStringToB(@PathVariable("var") String var);

    /**
     * MicroServiceB 返回的是 List<> , 则需要在 MicroServiceA 新建一个 VM 来接受 MicroServiceB 返回的数据
     */
    @RequestMapping(method = RequestMethod.GET, value = "/api/demo/person-list")
    public List<PersonVM> getPersonListFromB();
}
