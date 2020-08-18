package br.dev.tutorials.querydslwithspringboot.application;

import br.dev.tutorials.querydslwithspringboot.infrastructure.entity.Endereco;
import br.dev.tutorials.querydslwithspringboot.infrastructure.entity.Pessoa;
import br.dev.tutorials.querydslwithspringboot.infrastructure.entity.QEndereco;
import br.dev.tutorials.querydslwithspringboot.infrastructure.entity.QPessoa;
import br.dev.tutorials.querydslwithspringboot.infrastructure.entity.QTelefone;
import br.dev.tutorials.querydslwithspringboot.infrastructure.entity.Telefone;
import br.dev.tutorials.querydslwithspringboot.infrastructure.repository.EnderecoRepository;
import br.dev.tutorials.querydslwithspringboot.infrastructure.repository.PessoaRepository;
import br.dev.tutorials.querydslwithspringboot.infrastructure.repository.TelefoneRepository;
import com.google.common.collect.Streams;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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

  private final PessoaRepository pessoaRepository;
  private final EnderecoRepository enderecoRepository;
  private final TelefoneRepository telefoneRepository;
  private final EntityManager entityManager;

  @Override
  public void run(String... args) {
    QPessoa qPessoa = QPessoa.pessoa;
    BooleanExpression booleanExpression = qPessoa.id.eq(1L);
    Iterable<Pessoa> pessoas = this.pessoaRepository.findAll(booleanExpression);
    Streams.stream(pessoas).forEach(System.out::println);

    BooleanBuilder builder = new BooleanBuilder();
    builder.and(qPessoa.id.goe(2L));
    builder.and(qPessoa.nome.endsWithIgnoreCase("a"));
    pessoas = this.pessoaRepository.findAll(builder);
    Streams.stream(pessoas).forEach(System.out::println);

    QEndereco qEndereco = QEndereco.endereco;
    BooleanExpression booleanExpression1 = qEndereco.pessoa.id.eq(2L);
    Iterable<Endereco> enderecos = this.enderecoRepository.findAll(booleanExpression1);
    Streams.stream(enderecos).forEach(e -> log.info("{}", e));

    QTelefone qTelefone = QTelefone.telefone;
    BooleanExpression booleanExpression2 = qTelefone.pessoa.id.goe(3L);
    Iterable<Telefone> telefones = this.telefoneRepository.findAll(booleanExpression2);
    Streams.stream(telefones).forEach(t -> log.info("{}", t));

    builder.and(qTelefone.pessoa.id.goe(2L));
    builder.and(qTelefone.ddd.endsWith("3"));
    Streams.stream(this.telefoneRepository.findAll(builder)).forEach(t -> log.info("{}", t));

    Query query = this.entityManager.createQuery("Select e from Endereco e where e.bairro like '%a%'");
    List<Endereco> outrosEnderecos = (List<Endereco>) query.getResultList();
    outrosEnderecos.forEach(System.out::println);

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Endereco> criteriaQuery = criteriaBuilder.createQuery(Endereco.class);
    Root<Endereco> root = criteriaQuery.from(Endereco.class);

    List<Predicate> predicates = new ArrayList<>();
    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("id"), 2)));
    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("bairro"), "%3")));
    criteriaQuery.where(predicates.toArray(new Predicate[0]));

    this.entityManager.createQuery(criteriaQuery).getResultList().forEach(System.out::println);

    System.exit(0);
  }
}
