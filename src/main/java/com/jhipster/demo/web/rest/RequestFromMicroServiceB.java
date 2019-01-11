package com.jhipster.demo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jhipster.demo.client.RequestMicroServiceClient;
import com.jhipster.demo.client.RequestUaaClient;
import com.jhipster.demo.web.rest.vm.PersonVM;
import com.jhipster.demo.web.rest.vm.UserVM;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demo")
public class RequestFromMicroServiceB {

    private final RequestMicroServiceClient client;
    private final RequestUaaClient uaaClient;

    public RequestFromMicroServiceB(RequestMicroServiceClient client, RequestUaaClient uaaClient) {
        this.client = client;
        this.uaaClient = uaaClient;
    }

    // 调用 MicroService A
    @GetMapping("/uaa/account")
    @Timed
    public UserVM getAccount() {
        return uaaClient.getAccount();
    }

    @GetMapping("/uaa/authenticate")
    @Timed
    public String getUaaAuthenticate() {
        return uaaClient.getAuthenticate();
    }


    // 调用 MicroService B
    @GetMapping("/login")
    @Timed
    public String getLogin() {
        return client.getLogin();
    }


    // 调用 MicroService A
    @GetMapping("/string")
    @Timed
    public String getString() {
        return " Hello World From MicroService A ! ";
    }

    // 调用 MicroService A
    @PostMapping("/set-var/{var}")
    @Timed
    public String setString(@PathVariable String var) {
        return " Hello World ," + var + "From MicroService A ! ";
    }

    // GateWay 下 MicroService A 请求 MicroService B
    @GetMapping("/get-string-from-b")
    @Timed
    public String getStringFromB() {
        return client.getStringFromB();
    }

    // GateWay 下 MicroService A 请求 MicroService B
    @PutMapping("/set-string-to-b/{var}")
    @Timed
    public String getStringFromB(@PathVariable String var) {
        return client.setStringToB(var);
    }

    // GateWay 下 MicroService A 请求 MicroService B 实体数据
    @GetMapping("/person-list-from-b")
    @Timed
    public List<PersonVM> getPersonListFromB() {
        List<PersonVM> list = client.getPersonListFromB();
        for (PersonVM personVM : list) {
            System.out.println("-----------------------");
            System.out.println(personVM.toString());
            System.out.println("-----------------------");
        }
        return client.getPersonListFromB();
    }

    /** 配置权限 ***********************************************/
    @GetMapping("/secured/get-string-from-b")
    @Timed
    public String getStringSecured() {
        return client.getStringSecuredToB();
    }

    @PutMapping("/secured/set-string-from-b/{var}")
    @Timed
    public String addStringSecured(@PathVariable String var) {
        return client.setStringSecuredToB(var);
    }

}
