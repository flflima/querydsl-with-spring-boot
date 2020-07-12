package br.dev.tutorials.querydslwithspringboot.infrastructure.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Telefone {

    @Id
    private Long id;

    private String ddd;

    private String numero;

    @ManyToOne
    private Pessoa pessoa;
}
