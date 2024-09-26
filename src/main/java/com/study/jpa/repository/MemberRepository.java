package com.study.jpa.repository;

import com.study.jpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * @Repository : Spring Bean으로 등록. JPA 예외를 Spring Framework 예외로 변환
 * @PersistenceUnit : EntityManagerFactory 주입
 * @PersistenceContext : EntityManager 주입
 */

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

}