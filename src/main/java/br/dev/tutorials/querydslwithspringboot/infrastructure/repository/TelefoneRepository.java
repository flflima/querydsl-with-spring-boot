package br.dev.tutorials.querydslwithspringboot.infrastructure.repository;

import br.dev.tutorials.querydslwithspringboot.infrastructure.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository
    extends JpaRepository<Telefone, Long>, QuerydslPredicateExecutor<Telefone> {}
