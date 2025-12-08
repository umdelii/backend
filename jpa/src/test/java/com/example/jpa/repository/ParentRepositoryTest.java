package com.example.jpa.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpa.entity.Child;
import com.example.jpa.entity.Parent;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@Transactional // 이 클래스 안의 테스트 메소드들이 실행될때 하나의 작업으로 처리해줘 + rollback
public class ParentRepositoryTest {
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildRepository childRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void persistenceStateTest() {

        // 1. 비영속 상태(new / transient)
        Parent p = Parent.builder().name("new 상태").build();
        System.out.println("1) 비영속 상태 : " + p);

        // 2. 영속(managed)
        em.persist(p); // insert 구문 실행
        System.out.println("2) 영속 상태 진입: " + p);

        // 3. 영속상태에 있는 엔티티 변경 => Dirty Checking
        p.setName("이름 변경"); // update 구문 실행
        System.out.println("3) 영속 상태에서 값 변경 : " + p);

        // 4. db에 반영 : flush
        em.flush();
        System.out.println("4) flush 후 db반영 완료");

        // 5. 준영속(detached)
        em.detach(p);
        p.setName("detached 상태에서 이름 변경"); // detached 상태에서는 update 구문이 안 일어남
        System.out.println("5) detach 상태에서 변경 : " + p);

        em.flush();

        // 6. 다시 영속성 상태로 병합(merge)
        Parent merged = em.merge(p);
        merged.setName("merge 후 다시 영속 상태");
        System.out.println("6) merge 결과 영속 엔티티 : " + merged);

        em.flush();
    }

    @Test
    @Commit // @Transactional은 롤백하니 롤백하지말고 커밋해줘라는 어노테이션
    public void testInsert() {
        Parent parent = Parent.builder().name("parent1").build();
        parentRepository.save(parent);

        IntStream.rangeClosed(1, 3).forEach(i -> {
            Child child = Child.builder().name("child" + i).parent(parent).build();
            childRepository.save(child);
        });
    }

    @Test
    @Transactional(readOnly = true) // dirty checking 하지말기
    public void testRead() {
        Parent parent = parentRepository.findById(1L).orElseThrow();
        parent.setName("변경 이름");
        System.out.println(parent);
        // 자식 조회
        parent.getChilds().forEach(c -> System.out.println(c));
    }

    @Test
    @Commit
    public void testUpdate() {
        Parent parent = parentRepository.findById(1L).get();
        parent.setName("변경 이름");
        // transactional&commit을 걸면 따로 save하지않아도 set할때 dirty checking이 일어나므로 update됨
    }

    // cascade
    @Test
    @Commit
    public void testCascadeInsert() {
        // 부모 저장 시 관련있는 자식들도 같이 저장해줘
        // entity의 관계성 어노테이션(@OneToMany등)에 cascade 속성 추가
        Parent parent = Parent.builder().name("parent2").build();
        parent.getChilds().add(Child.builder().name("child4").parent(parent).build());
        parent.getChilds().add(Child.builder().name("child5").parent(parent).build());
        parentRepository.save(parent);
    }

    // cascade
    @Test
    @Commit
    public void testCascadeInsert2() {
        // 부모 저장 시 관련있는 자식들도 같이 저장해줘
        // entity의 관계성 어노테이션(@OneToMany등)에 cascade 속성 추가
        Parent parent = Parent.builder().name("parent5").build();
        Child child1 = Child.builder().name("child6").build();
        child1.setParent(parent);
        Child child2 = Child.builder().name("child7").build();
        child2.setParent(parent);
        parentRepository.save(parent);
    }

    @Test
    @Commit
    public void testCascadeDelete() {
        // 부모 삭제 시 관련있는 자식들도 같이 삭제(db의 삭제 관련 개념과는 반대)
        // FK 제약조건에서 자식 삭제 -> 부모 삭제의 순서가 필수
        // 하지만 entity에서 cascadeType remove 설정하면 연관시켜 삭제시켜줌
        parentRepository.deleteById(5L);
    }

    @Test
    @Commit
    public void testOrphanDelete() {
        Parent parent = parentRepository.findById(3L).get();
        parent.getChilds().forEach(c -> System.out.println(c));
        parent.getChilds().remove(0); // list에서 0번 child 제거
        // 원래는 되지 않으나(왤까?), orphanRemoval 속성 설정에 따라 부모에서 자식 row만 제거도 가능
        parentRepository.save(parent);
    }
}
