package br.dev.tutorials.querydslwithspringboot.application;

import br.dev.tutorials.querydslwithspringboot.infrastructure.entity.Pessoa;
import br.dev.tutorials.querydslwithspringboot.infrastructure.entity.QPessoa;
import br.dev.tutorials.querydslwithspringboot.infrastructure.repository.PessoaRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Execute implements CommandLineRunner {

  @Autowired private PessoaRepository pessoaRepository;

  @Override
  public void run(String... args) {
    QPessoa qPessoa = QPessoa.pessoa;
    BooleanExpression booleanExpression = qPessoa.id.eq(1L);
    Iterable<Pessoa> pessoas = this.pessoaRepository.findAll(booleanExpression);

    log.info("{}", pessoas.iterator().next());
  }
}
