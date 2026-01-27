package com.example.todo.dto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResultDTO<E> {
    // 화면에 보여줄 목록
    private List<E> dtoList;

    // 페이지 번호 목록
    private List<Integer> pageNumList;

    private PageRequestDTO pageRequestDTO;

    private boolean prev, next;

    private int prevPage, nextPage, totalPage, current;

    private long totalCount;

    // 멤버변수가 아닌 이 메소드만 직접 부르고 싶을때 따로 지정, 이름 별도로 지정가능
    @Builder(builderMethodName = "withAll")
    public PageResultDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long totalCount) {
        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = totalCount;

        // [1 2 3 4 5 6 7 8 9 10]
        // [11 12 13 14 15 16 17 18 19 20] 이렇게 화면에 페이지 목록 변하는거 계산하는 코드

        // page=1&siza=10 이라면
        int end = (int) (Math.ceil(pageRequestDTO.getPage() / 10.0) * 10);
        // 10.0으로 나누어서 1~10 페이지는 무조건 start=1, end=10으로 만듬
        int start = end - 9;

        // 실제 마지막 페이지가 몇 페이지인지 구함
        int last = (int) (Math.ceil(totalCount / (double) pageRequestDTO.getSize()));

        end = end > last ? last : end;

        // ------- start, end 결정

        // 1 > 1 (이전 페이지 없음) / 11 > 1 (이전 페이지 있음)
        this.prev = start > 1;
        this.next = totalCount > end * pageRequestDTO.getSize();
        // 이전, 다음 페이지 번호
        if (prev) {
            this.prevPage = start - 1;
        }
        if (next) {
            this.nextPage = end + 1;
        }

        // boxed() => int 12345... -> Integer 12345...
        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
        totalPage = this.pageNumList.size();

        // 현재 사용자가 선택한 페이지
        this.current = pageRequestDTO.getPage();
    }
}
