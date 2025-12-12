package com.example.board.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.member.entity.Member;
import com.example.board.member.repository.MemberRepository;
import com.example.board.post.entity.Board;
import com.example.board.post.repository.BoardRepository;
import com.example.board.reply.entity.Reply;
import com.example.board.reply.repository.ReplyRepository;

@SpringBootTest
@Transactional
@Disabled
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    @Commit
    public void insertMemberTest() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .password(i + "hello")
                    .name("member" + i)
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    @Commit
    public void insertBoardTest() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            int idx = (int) (Math.random() * 10) + 1;
            Member member = Member.builder().email("user" + idx + "@gmail.com").build();
            Board board = Board.builder()
                    .title("title" + i)
                    .content("apple pie" + i)
                    .writer(member)
                    .build();
            boardRepository.save(board);
        });
    }

    @Test
    @Commit
    public void insertReplyTest() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            long idx = (long) (Math.random() * 100 + 1);
            Board board = Board.builder().bno(idx).build();

            Reply reply = Reply.builder()
                    .text("text" + i)
                    .replyer("guest" + i)
                    .board(board)
                    .build();
            replyRepository.save(reply);
        });
    }

    @Test
    @Transactional(readOnly = true)
    public void readBoardTest() {
        List<Board> list = boardRepository.findAll();
        list.forEach(b -> {
            System.out.println(b);
            System.out.println(b.getWriter());
        });
    }

    @Test
    @Transactional(readOnly = true)
    public void getBoardWithWriterTest() {
        List<Object[]> result = boardRepository.getBoardWithWriter();
        result.forEach(o -> {
            System.out.println(Arrays.toString(o));
        });
    }

    @Test
    @Transactional(readOnly = true)
    public void getBoardWithReplyWhereBnoTest() {
        // JPA
        Board board = boardRepository.findById(33L).get();
        System.out.println(board);
        // 댓글 가져오기
        System.out.println(board.getReplies());
    }

    @Test
    @Transactional(readOnly = true)
    public void getBoardWithReplyWhereBnoTest2() {
        // repository에 만든 임의 메소드들은 jpql이라고 부름
        boardRepository.getBoardWithReplyWhereBno(33L).forEach(result -> {
            System.out.println(Arrays.toString(result));
        });
    }

    @Test
    @Transactional(readOnly = true)
    public void getBoardWithReplyCount() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
        result.forEach(obj -> {
            // System.out.println(Arrays.toString(obj));
            Board board = (Board) obj[0];
            Member member = (Member) obj[1];
            Long replyCnt = (Long) obj[2];
            System.out.println(board);
            System.out.println(member);
            System.out.println(replyCnt);
        });
    }

    @Test
    @Transactional(readOnly = true)
    public void getBoardByBnoTest() {
        // boardRepository.getBoardByBno(49L).forEach(obj -> {
        // System.out.println(Arrays.toString(obj));
        // });

        Object result = boardRepository.getBoardByBno(49L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }

    @Test
    @Commit
    public void deleteByBnoTest() {
        replyRepository.deleteByBno(8L);
        boardRepository.deleteById(8L);
    }

    // querydsl 테스트
    @Test
    @Transactional(readOnly = true)
    public void listTest() {
        List<Object[]> result = boardRepository.list();
        System.out.println(result);
    }
}
