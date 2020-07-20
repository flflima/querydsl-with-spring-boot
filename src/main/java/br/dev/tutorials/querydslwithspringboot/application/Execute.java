package br.dev.tutorials.querydslwithspringboot.application;

import br.dev.tutorials.querydslwithspringboot.infrastructure.entity.Endereco;
import br.dev.tutorials.querydslwithspringboot.infrastructure.entity.QEndereco;
import br.dev.tutorials.querydslwithspringboot.infrastructure.repository.EnderecoRepository;
import com.google.common.collect.Streams;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class Execute implements CommandLineRunner {

  private final EnderecoRepository enderecoRepository;
  private final EntityManager entityManager;

  @Override
  public void run(String... args) {
    QEndereco qEndereco = QEndereco.endereco;

    log.info("Buscando todos os endereços com o id >= 3");
    BooleanExpression booleanExpression = qEndereco.id.goe(3L);
    Streams.stream(this.enderecoRepository.findAll(booleanExpression))
        .forEach(t -> log.info("{}", t));
    log.info(" ");

    log.info("Buscando todos os endereços com o id >= 2 e bairro terminado com 3");
    long t1 = System.currentTimeMillis();
    BooleanBuilder builder = new BooleanBuilder();
    builder.and(qEndereco.id.goe(2L));
    builder.and(qEndereco.bairro.endsWith("3"));
    Streams.stream(this.enderecoRepository.findAll(builder)).forEach(t -> log.info("{}", t));
    long t2 = System.currentTimeMillis();
    log.info("Tempo {}", t2 - t1);
    log.info(" ");

    log.info("Buscando todos os endereços com o id >= 2 e bairro terminado com 3");
    t1 = System.currentTimeMillis();
    BooleanBuilder builder2 = new BooleanBuilder();
    builder2.and(qEndereco.id.goe(2L));
    builder2.and(qEndereco.bairro.endsWith("3"));
    new JPAQueryFactory(this.entityManager)
        .selectFrom(qEndereco)
        .where(builder2)
        .fetch()
        .forEach(t -> log.info("{}", t));
    t2 = System.currentTimeMillis();
    log.info("Tempo {}", t2 - t1);
    log.info(" ");

    log.info("Buscando todos os endereços com o id >= 2 e bairro terminado com 3");
    t1 = System.currentTimeMillis();
    CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
    CriteriaQuery<Endereco> q = criteriaBuilder.createQuery(Endereco.class);
    Root<Endereco> root = q.from(Endereco.class);

    List<Predicate> predicates = new ArrayList<>();
    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("id"), 2)));
    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("bairro"), "%3")));
    q.where(predicates.toArray(new Predicate[0]));

    this.entityManager.createQuery(q).getResultList().forEach(t -> log.info("{}", t));
    t2 = System.currentTimeMillis();
    log.info("Tempo {}", t2 - t1);
    log.info(" ");

    System.exit(0);
  }
}
