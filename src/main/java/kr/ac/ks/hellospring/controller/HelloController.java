package kr.ac.ks.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello"; //ctrl키 누른 채로 hello에 마우스 올리면 또는 마우스 올리고 f3키 누르면 hello.html로 링크된다..!
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        /* http://localhost:8080/hello-mvc?name=kim 이렇게 파라미터를 줘야한다 */
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody   //http 응답 body 부분에 return 값을 직접 넣어주겠다는 뜻 (html의 body가 아니다!)
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; //view 없이 (html 없이) 그대로 hello + name값 출력해준다
    }

    /* API 방식 (ResponseBody가 기본으로 JSON 방식으로 반환한다) */
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello(); //ctrl + shift + enter 치면 자동완성
        hello.setName(name);
        return hello;
        /* 문자가 아니라 객체를 반환한다 ?
        => spring 에서는 data를 json 방식으로 만들어서 http 응답에 반환한다. */
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
