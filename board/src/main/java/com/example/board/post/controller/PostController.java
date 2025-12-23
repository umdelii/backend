package com.example.board.post.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.board.post.dto.BoardDTO;
import com.example.board.post.dto.PageRequestDTO;
import com.example.board.post.dto.PageResultDTO;
import com.example.board.post.service.BoardService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
@RequestMapping("/board")
@RequiredArgsConstructor
public class PostController {

    private final BoardService boardService;

    @GetMapping("/create")
    public void getCreate(BoardDTO dto) {
        log.info("create 호출");
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String postCreate(@Valid BoardDTO dto, BindingResult result, RedirectAttributes rttr) {
        log.info("create board {}", dto);
        if (result.hasErrors()) {
            return "/board/create";
        }

        Long bno = boardService.insert(dto);

        rttr.addFlashAttribute("msg", "Book Number : " + bno + ", Create Complete");
        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public void getList(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list 호출 {}", pageRequestDTO);
        PageResultDTO<BoardDTO> result = boardService.getList(pageRequestDTO);
        model.addAttribute("result", result);
    }

    @GetMapping({ "/read", "/modify" })
    public void getReadOrModify(Long bno, Model model, PageRequestDTO pageRequestDTO) {
        log.info("read or modify {}", bno);

        BoardDTO dto = boardService.getRow(bno);
        model.addAttribute("dto", dto);
    }

    @PreAuthorize("authentication.name == #dto.writerEmail")
    @PostMapping("/modify")
    public String postModify(PageRequestDTO pageRequestDTO, BoardDTO dto, RedirectAttributes rttr) {
        log.info("modify {} {}", dto, pageRequestDTO);

        boardService.update(dto);

        rttr.addAttribute("bno", dto.getBno());
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addFlashAttribute("msg", "Update Complete");
        return "redirect:/board/read";
    }

    @PreAuthorize("authentication.name == #dto.writerEmail")
    @PostMapping("/remove")
    public String postDelete(BoardDTO dto, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("remove 호출 {} {}", dto, pageRequestDTO);

        boardService.delete(dto);
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addFlashAttribute("msg", "Delete Complete");

        return "redirect:/board/list";
    }

}
