package br.dev.tutorials.querydslwithspringboot.infrastructure.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
