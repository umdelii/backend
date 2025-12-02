package com.example.student.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.student.dto.StudentDTO;
import com.example.student.entity.constant.Grade;
import com.example.student.service.StudentService;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
@Log4j2
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    // 학생 등록 : /student/register
    // 학생 정보 수정 : /student/modify?id=?
    // 학생 정보 조회 : /student/read?id=? 
    // 학생 전체 조회도
    // 학생 데이터 삭제(탈퇴) : /student/remove
    private final StudentService studentService;

    @GetMapping("/register")
    public void getRegister() {
        log.info("call register page");
    }

    // // 내가 만든 html
    // @GetMapping("/myregister")
    // public void getMyRegister() {
    //     log.info("call register page");
    // }

    @PostMapping("/register")
    public String postRegister(StudentDTO dto) {
        log.info("post register {}",dto);
        String name = studentService.insert(dto);
        return "redirect:/student/list";
    }

    // // 내가 만든 html
    // @PostMapping("/myregister")
    // public void postMyRegister(StudentDTO dto) {
    //     log.info("post register {}",dto);
    // }
    
    @GetMapping({"/modify","/read"})
    public void getModify(@RequestParam Long id,Model model) {
        log.info("get id {}",id);

        StudentDTO dto = studentService.read(id);
        model.addAttribute("dto", dto);
    }
    
    @PostMapping("/modify")
    public String postModify(StudentDTO dto,RedirectAttributes rttr) {
        log.info("modify {}",dto);
        Long id = studentService.update(dto);
        // 수정 후 read로 이동
        rttr.addAttribute("id",id);   
        return "redirect:/student/read";
    }
    
    @PostMapping("/remove")
    public String postRemove(StudentDTO dto) {
        log.info("delete {}",dto);
        studentService.delete(dto.getId());
        // delete 후 list로 이동
        return "redirect:/student/list";
    }
    
    @GetMapping("/list")
    public void getList(Model model) {
        log.info("call all student list");
        List<StudentDTO> list = studentService.readAll();
        model.addAttribute("list", list);
    }
}
