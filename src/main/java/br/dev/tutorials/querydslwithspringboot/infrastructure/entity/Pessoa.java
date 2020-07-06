package br.dev.tutorials.querydslwithspringboot.infrastructure.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Pessoa {

    @Id
    private Long id;

    private String nome;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private List<Endereco> enderecos;
}
