package com.example.web.board.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.web.board.dto.BoardDTO;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@Log4j2
@RequestMapping("/board")
public class BoardController {

    @GetMapping("/list")
    public void getList(Model model) {
        log.info("list 요청");

        // BoardDTO dto = new BoardDTO(1L, "스프링 부트", "정이안", LocalDate.now());

        List<BoardDTO> list = new ArrayList<>();

        for (long i = 1L; i < 21; i++) {
            // Builder 패턴 적용 후
            BoardDTO dto = BoardDTO.builder()
            .id(i)
            .title("스프링 부트"+i)
            .writer("정이안")
            .regDate(LocalDateTime.now())
            .build();

            list.add(dto);
        }


        model.addAttribute("list", list);
    }

    @GetMapping("/read")
    public void getRead(@RequestParam long id) {
        log.info("read 요청 {}",id);
    }
    

}
