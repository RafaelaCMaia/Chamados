
package br.com.sistemas.chamados.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tecnicos")
public class Tecnico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 120)
    private String email;

    public Tecnico() {}

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String valor) {
        this.nome = valor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String valor) {
        this.email = valor;
    }
}
