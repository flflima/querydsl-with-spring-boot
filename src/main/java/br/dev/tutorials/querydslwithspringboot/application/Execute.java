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
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

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
    log.info("Buscando pessoas com o id = 1");
    QPessoa qPessoa = QPessoa.pessoa;
    BooleanExpression booleanExpression = qPessoa.id.eq(1L);
    Iterable<Pessoa> pessoas = this.pessoaRepository.findAll(booleanExpression);
    Streams.stream(pessoas).forEach(p -> log.info("{}", p));
    log.info(" ");

    log.info("Buscando todos os endereços das pessoas com o id = 1");
    QEndereco qEndereco = QEndereco.endereco;
    BooleanExpression booleanExpression1 = qEndereco.pessoa.id.eq(2L);
    Iterable<Endereco> enderecos = this.enderecoRepository.findAll(booleanExpression1);
    Streams.stream(enderecos).forEach(e -> log.info("{}", e));
    log.info(" ");

    log.info("Buscando todos os telefones das pessoas com o id >= 3");
    QTelefone qTelefone = QTelefone.telefone;
    BooleanExpression booleanExpression2 = qTelefone.pessoa.id.goe(3L);
    Iterable<Telefone> telefones = this.telefoneRepository.findAll(booleanExpression2);
    Streams.stream(telefones).forEach(t -> log.info("{}", t));
    log.info(" ");

    log.info("Buscando todos os telefones das pessoas com o id >= 2 e ddd terminado com 3");
    BooleanBuilder builder = new BooleanBuilder();
    builder.and(qTelefone.pessoa.id.goe(2L));
    builder.and(qTelefone.ddd.endsWith("3"));
    new JPAQueryFactory(this.entityManager)
        .selectFrom(qTelefone).where(builder).fetch().forEach(t -> log.info("{}", t));
    log.info(" ");

    System.exit(0);
  }
}
