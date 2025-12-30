package com.example.movietalk.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.movietalk.movie.dto.MovieDTO;
import com.example.movietalk.movie.dto.PageRequestDTO;
import com.example.movietalk.movie.dto.PageResultDTO;
import com.example.movietalk.movie.service.MovieService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MoiveController {

    private final MovieService movieService;

    @GetMapping("/create")
    public void getCreate(PageRequestDTO pageRequestDTO) {
        log.info("create form 要請");
    }

    @PostMapping("create")
    public String postCreate(MovieDTO dto, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("映画追加要請 {}", dto);

        String title = movieService.create(dto);

        rttr.addFlashAttribute("mno", title + "追加完了");
        rttr.addAttribute("page", "1");
        rttr.addAttribute("size", pageRequestDTO.getSize());
        return "redirect:/movie/list";
    }

    @GetMapping("/list")
    public void get(PageRequestDTO pageRequestDTO, Model model) {
        log.info("映画リストリクエスト{}", pageRequestDTO);

        PageResultDTO<MovieDTO> result = movieService.getMovieList(pageRequestDTO);
        model.addAttribute("result", result);
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(Long mno, PageRequestDTO pageRequestDTO, Model model) {
        log.info("read or modify {}", mno);
        MovieDTO dto = movieService.getRow(mno);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String postModify(MovieDTO dto, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("映画modify {}", dto);

        Long mno = movieService.updateRow(dto);

        rttr.addAttribute("mno", mno);
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        return "redirect:/movie/read";
    }

    @PostMapping("/remove")
    public String postRemove(Long mno, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("mno {}", mno);
        movieService.deleteRow(mno);

        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        return "redirect:/movie/list";
    }

}
