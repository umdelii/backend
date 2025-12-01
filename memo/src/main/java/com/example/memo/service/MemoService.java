package com.example.memo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.memo.dto.MemoDTO;
import com.example.memo.entity.Memo;
import com.example.memo.repository.MemoRepository;

import groovyjarjarantlr4.v4.parse.ANTLRParser.v3tokenSpec_return;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service // 나 service 역할이야 알려주기
@Log4j2
@RequiredArgsConstructor // final은 무조건 초기화(new)를 해야되는데 이 어노테이션을 부르면 선언만 해도됨 그럼 @Autowired 노필요
public class MemoService {
    // @Autowired
    // private MemoRepository memoRepository;
    
    // 전체 조회
    // public List<MemoDTO> readAll(){
        // List<Memo> memos =memoRepository.findAll();

        // Entity는 service -> repository or repository -> repository하는 상황에서만 쓸거야
        // service에서 controller, 주고받을때는? : ~~DTO

        // 리턴하기 전에 entity를 dto로 바꿔서 controller에 리턴시킬거임
        // List<MemoDTO> list = new ArrayList<>();
        // for (Memo memo : memos) {
        //     MemoDTO dto = MemoDTO.builder()
        //     .mno(memo.getMno())
        //     .memoText(memo.getMemoText())
        //     .createDate(memo.getCreateDate())
        //     .updateDate(memo.getUpdateDate())
        //     .build();
        //     list.add(dto);
        // }
        // return list;}

        // @Autowired
        // private MemoRepository memoRepository;
        // @Autowired
        // private ModelMapper modelMapper;
        private final MemoRepository memoRepository;
        private final ModelMapper modelMapper;

        public List<MemoDTO> readAll(){
        List<Memo> memos = memoRepository.findAll();
        List<MemoDTO> list = new ArrayList<>();
        for (Memo memo : memos) {
            MemoDTO dto = modelMapper.map(memo, MemoDTO.class);
            list.add(dto);
        }
        return list;
    }
        
        // 하나의 행 조회
        public MemoDTO read(Long id){
            // Memo memo = memoRepository.findById(id).get();

            Optional<Memo> result = memoRepository.findById(id);
            // Memo memo = null;
            // if (result.isPresent()) {
            //     memo = result.get();
            // }

            //NoSuchElementException ,안에 값 없으면 이렇게 메세지나 날려라
            Memo memo = memoRepository.findById(id).orElseThrow();
            // entity => dto 변환 후 리턴
            return modelMapper.map(memo, MemoDTO.class);
        }

        // 메모 하나 수정
        public Long modify(MemoDTO dto){
            // 수정할 대상 찾고
            Memo memo = memoRepository.findById(dto.getMno()).orElseThrow();
            // 변경
            memo.changeMemoText(dto.getMemoText());
            // 변경 후 save
            // memo = memoRepository.save(memo);
            // return memo.getMno();

            // save하고 return하는거 짧게
            return memoRepository.save(memo).getMno();
        }

        // 메모 하나 삭제
        public void remove(Long id){
            memoRepository.deleteById(id);
        }

        // 새로운 메모 삽입
        public Long insert(MemoDTO dto){
            // save에는 dto가 아닌 entity로 넣어야 하므로 다시 dto => entity 변환
            Memo memo = modelMapper.map(dto, Memo.class);
            return memoRepository.save(memo).getMno();
        }
    }
