package br.dev.tutorials.querydslwithspringboot.infrastructure.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Pessoa {

    @Id
    private Long id;

    @NotNull
    private String nome;

}
