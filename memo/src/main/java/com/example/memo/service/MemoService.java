package com.example.memo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.memo.dto.MemoDTO;
import com.example.memo.entity.Memo;
import com.example.memo.repository.MemoRepository;

import lombok.extern.log4j.Log4j2;

@Service // 나 service 역할이야 알려주기
@Log4j2
public class MemoService {
    @Autowired
    private MemoRepository memoRepository;
    
    // 전체 조회
    public List<MemoDTO> readAll(){
        List<Memo> memos =memoRepository.findAll();

        // Entity는 service -> repository or repository -> repository하는 상황에서만 쓸거야
        // service에서 controller, 주고받을때는? : ~~DTO

        // 리턴하기 전에 entity를 dto로 바꿔서 controller에 리턴시킬거임
        List<MemoDTO> list = new ArrayList<>();
        for (Memo memo : memos) {
            MemoDTO dto = MemoDTO.builder()
            .mno(memo.getMno())
            .menoText(memo.getMenoText())
            .createDate(memo.getCreateDate())
            .updateDate(memo.getUpdateDate())
            .build();
            list.add(dto);
        }
        return list;
    }
}
