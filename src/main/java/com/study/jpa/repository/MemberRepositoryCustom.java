package com.study.jpa.repository;

import com.study.jpa.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepositoryCustom {
    List<Member> findByName(String name);
}