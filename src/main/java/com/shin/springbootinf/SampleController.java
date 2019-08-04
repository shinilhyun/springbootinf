package com.shin.springbootinf;

import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class SampleController {

    @GetMapping("/hello")
    @CrossOrigin(origins = "http://localhost:8180")
    public Resource<Hello> hello() {
        Hello hello = new Hello();
        hello.setPrefix("Heym,");
        hello.setName("ilhyun");

        //HATEOAS 를 만족하는 api ("_links":{"self":{"href":"localhost:8080/hello"}}} 추가
        Resource<Hello> helloResource = new Resource<>(hello);
        helloResource.add(linkTo(methodOn(SampleController.class).hello()).withSelfRel());
        return helloResource;
    }
}
