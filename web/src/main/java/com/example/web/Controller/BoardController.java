package com.example.web.Controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Log4j2
public class BoardController {

    @GetMapping("/board/list")
    public void getList() {
        log.info("list 요청");
    }    

}
