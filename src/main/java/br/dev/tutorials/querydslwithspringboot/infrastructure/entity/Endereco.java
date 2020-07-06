package br.dev.tutorials.querydslwithspringboot.infrastructure.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Endereco {

    @Id
    private Long id;

    private String logradouro;

    private String bairro;

    private String cidade;

    private String estado;
}
