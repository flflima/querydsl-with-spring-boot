package br.dev.tutorials.querydslwithspringboot.application;

import br.dev.tutorials.querydslwithspringboot.infrastructure.entity.Endereco;
import br.dev.tutorials.querydslwithspringboot.infrastructure.entity.QEndereco;
import br.dev.tutorials.querydslwithspringboot.infrastructure.repository.EnderecoRepository;
import com.google.common.collect.Streams;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class Execute implements CommandLineRunner {

  private final EnderecoRepository enderecoRepository;
  private final EntityManager entityManager;

  @Override
  public void run(String... args) {
    QEndereco qEndereco = QEndereco.endereco;

    log.info("Com BooleanExpression");
    log.info("Buscando todos os endereços com o id >= 3");
    Streams.stream(this.enderecoRepository.findAll(qEndereco.id.goe(3L)))
        .forEach(t -> log.info("{}", t));
    log.info(" ");

    log.info("Com BooleanBuilder");
    log.info("Buscando todos os endereços com o id >= 2 e bairro terminado com 3");
    long t1 = System.currentTimeMillis();
    Streams.stream(
            this.enderecoRepository.findAll(
                new BooleanBuilder().and(qEndereco.id.goe(2L)).and(qEndereco.bairro.endsWith("3"))))
        .forEach(t -> log.info("{}", t));
    long t2 = System.currentTimeMillis();
    log.info("Tempo {} ms", t2 - t1);
    log.info(" ");

    log.info("Com BooleanBuilder e JPA API");
    log.info("Buscando todos os endereços com o id >= 2 e bairro terminado com 3");
    t1 = System.currentTimeMillis();
    new JPAQueryFactory(this.entityManager)
        .selectFrom(qEndereco)
        .where(new BooleanBuilder().and(qEndereco.id.goe(2L)).and(qEndereco.bairro.endsWith("3")))
        .fetch()
        .forEach(t -> log.info("{}", t));
    t2 = System.currentTimeMillis();
    log.info("Tempo {} ms", t2 - t1);
    log.info(" ");

    log.info("Com CriteriaBuilder");
    log.info("Buscando todos os endereços com o id >= 2 e bairro terminado com 3");
    t1 = System.currentTimeMillis();
    CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
    CriteriaQuery<Endereco> criteriaQuery = criteriaBuilder.createQuery(Endereco.class);
    Root<Endereco> root = criteriaQuery.from(Endereco.class);

    List<Predicate> predicates = new ArrayList<>();
    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("id"), 2)));
    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("bairro"), "%3")));
    criteriaQuery.where(predicates.toArray(new Predicate[0]));

    this.entityManager.createQuery(criteriaQuery).getResultList().forEach(t -> log.info("{}", t));
    t2 = System.currentTimeMillis();
    log.info("Tempo {} ms", t2 - t1);
    log.info(" ");

    System.exit(0);
  }
}
