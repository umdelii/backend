package com.example.memo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.memo.dto.MemoDTO;
import com.example.memo.service.MemoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





@Controller
@Log4j2
@RequestMapping("/memo")
@RequiredArgsConstructor
public class MemoController {
    private final MemoService memoService;

    @GetMapping("/list")
    public void getList(Model model) {
        log.info("전체 메모 요청");    
        List<MemoDTO> list = memoService.readAll();
        model.addAttribute("list", list);
    }

    @GetMapping({"/read","/modify"})
    public void getRead(@RequestParam Long id,Model model) {
        log.info("read memo id {}",id);
        MemoDTO dto = memoService.read(id);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/remove")
    public String postRemove(@RequestParam("mno") Long id, RedirectAttributes rttr) {
        log.info("memo remove id {}",id);
        memoService.remove(id);        
        // 삭제 후 List 페이지로 이동
        rttr.addFlashAttribute("msg","Delete Complete");
        return "redirect:/memo/list";
    }
    
    @PostMapping("/modify")
    public String postModify(MemoDTO dto,RedirectAttributes rttr) {
    log.info("update memo {}",dto);
    Long id = memoService.modify(dto);

    // /memo/read?id=? 이동
    rttr.addAttribute("id",id);
    return "redirect:/memo/read";
    }

    @GetMapping("/create")
    public void getCreate(@ModelAttribute("dto") MemoDTO dto) {
        log.info("call create memo page");
    }

    @PostMapping("/create")
    public String postCreate(@ModelAttribute("dto") @Valid MemoDTO dto,BindingResult result,RedirectAttributes rttr) {
        log.info("insert new memo {}",dto);

        // 유효성 검증
        if (result.hasErrors()) {
            return "/memo/create";
        }

        Long id = memoService.insert(dto);

        rttr.addFlashAttribute("msg","Memo"+id+" Insert Complete");
        return "redirect:/memo/list";
    }
    
    
    
}
