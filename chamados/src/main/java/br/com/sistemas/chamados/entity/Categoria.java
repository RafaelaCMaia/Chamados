
package br.com.sistemas.chamados.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    public Categoria() {}

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String valor) {
        this.nome = valor;
    }
}
