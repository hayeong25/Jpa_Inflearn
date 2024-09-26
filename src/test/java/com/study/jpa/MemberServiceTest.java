package com.study.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.study.jpa.entity.Member;
import com.study.jpa.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Test
    public void member_join_Success() {
        // given
        Member member = new Member();
        member.setName("member1");

        // when
        Long saveId = memberService.join(member);
        Member findMember = memberService.findMember(saveId);

        // then
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void member_name_duplicate_IllegalStateException() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            // given
            Member member1 = new Member();
            member1.setName("member1");

            Member member2 = new Member();
            member2.setName("member1");

            // when
            memberService.join(member1);
            memberService.join(member2);
        });
    }
}