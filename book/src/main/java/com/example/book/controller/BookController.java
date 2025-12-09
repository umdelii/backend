package com.example.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.book.dto.BookDTO;
import com.example.book.service.BookService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/register")
    public void getRegister(BookDTO dto) {
        log.info("register.html 호출");
    }

    @GetMapping("/list")
    public void getList(Model model) {
        log.info("list.html 호출");
        model.addAttribute("list", bookService.readAll());
    }

    @PostMapping("/register")
    public String postRegister(@Valid BookDTO dto, BindingResult result, RedirectAttributes rttr) {
        log.info("등록 요청 {}", dto);

        if (result.hasErrors()) {
            return "/book/register";
        }
        bookService.create(dto);
        rttr.addFlashAttribute("msg", "Add complete " + dto.getTitle());
        return "redirect:/book/list";
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(@RequestParam("id") Long id, Model model) {
        log.info("read, modify.html 호출 ID : {}", id);
        BookDTO dto = bookService.readId(id);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String postModify(BookDTO dto, RedirectAttributes rttr) {
        log.info("수정 요청");
        Long id = bookService.update(dto);

        rttr.addAttribute("id", id);
        rttr.addFlashAttribute("msg", dto.getTitle() + "Update complete");
        return "redirect:/book/read";
    }

    @PostMapping("/remove")
    public String postRemove(@RequestParam Long id, RedirectAttributes rttr) {
        log.info("delete book {}", id);
        bookService.delete(id);

        rttr.addFlashAttribute("msg", "Delete complete");
        return "redirect:/book/list";
    }
}
