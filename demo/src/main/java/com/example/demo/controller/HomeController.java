package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.Info;

import jakarta.servlet.http.HttpServletRequest;







@Log4j2
@Controller 
public class HomeController {

    //8080에 응답하는 컨트롤러
    @GetMapping("/")
    public String getMethodName(RedirectAttributes rttr) {
        // return "index";

        // 8080에 액세스하면 home.html이 보이게하기
        // redirect:/경로(컨트롤러가 가지고 있는 경로여야함)

        // "/"컨트롤러의 값을 "/home"컨트롤러에 보내는 방법 : RedirectAttributes 객체
        // ?key=value 보내고싶을때 addAttribute
        rttr.addAttribute("bno",10); // http://localhost:8080/home?bno=10
        rttr.addAttribute("name","John");
        // 
        rttr.addFlashAttribute("money",1000);
        return "redirect:/home";
    }
    
    //
    @GetMapping("/home")
    public void getHome(int bno, String name) {
       log.info(("home 요청"));  // System.out.pirntIn()

    // "/"에서 보낸 값을 여기서 담아내는 방법
    log.info("8080요청 {} {}",bno,name);
    }

    @GetMapping("/add")
    public String getAdd(@RequestParam int num1, @RequestParam String op,@RequestParam int num2, Model model) {
        log.info("사칙연산 요청 {} {} {}",num1,op,num2);

        // 여기에 결과 
        int result = 0;
        switch (op) {
            case "+":
                result = num1+num2;
                break;
                case "-":
                result = num1-num2;   
                break;
                case "*":
                result = num1*num2;
                break;
                case "/":
                result = num1/num2;
                break;
                default:
                    break;
        }
        log.info("result = {}",result);

        model.addAttribute("num1", num1);
        model.addAttribute("op", model);
        model.addAttribute("num2", num2);
        model.addAttribute("result", result);
        return "exam3";
    }
    
    @GetMapping("/calc")
    public void getCalc() {
        log.info("getCalc");
    }
    
    @PostMapping("/calc")
    public void postCalc(@RequestParam(required = false, defaultValue = "0") int num1,@RequestParam(required = false, defaultValue = "0") int num2) {
        log.info("postCalc {} {}",num1,num2);
    }
    
    // http://localhost:8080/info
    @GetMapping("/info")
    public void getInfo() {
        log.info("info.html 호출");
    }

    // 클라이언트가 입력한 값 가져오기 
    // 방법1
    // 개별 처리
    // @PostMapping("/info")
    // public void postInfo(String username, int age, String addr, String tel) {
    //     log.info("info post");
    //     log.info("{}, {}, {}, {}",username,age,addr,tel);
    // }

    // 방법2 (클래스를 따로 만들어서 부르자)
    // DTO 이용
    // @PostMapping("/info") 
    // public void postInfo(Info info) {
    //     log.info("info post");
    //     log.info("{}, {}, {}, {}",info.getUsername(),info.getAge(),info.getAddr(),info.getTel());
    // }

    // 방법3 (HttpServletRequest 객체 사용)
    // 사용자가 요청할 때 사용하는 모든 정보를 가지고 올 수 있음 : 브라우저정보, 사용자ip, 경로 추출,...
    @PostMapping("/info") 
    public void postInfo(HttpServletRequest request) {
        log.info("info post");
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        String addr = request.getParameter("addr");
        String tel = request.getParameter("tel");
        log.info("{}, {}, {}, {}",username,age,addr,tel);
    }
}
