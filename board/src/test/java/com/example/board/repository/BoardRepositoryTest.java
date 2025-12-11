package com.example.board.repository;

import static org.mockito.ArgumentMatchers.isNull;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    // querydsl 테스트
    @Test
    @Transactional(readOnly = true)
    public void listTest() {
        List<Object[]> result = boardRepository.list();
        System.out.println(result);
    }
}
