package com.jhipster.demo.web.rest.vm;

/**
 * 接受 MicroServiceB 的 Person 视图模型
 */
public class PersonVM {

    /**
     * 对应 MicroServiceB 的 name 字段
     */
    private String name;

    /**
     * 对应 MicroServiceB 的 age 字段
     */
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
