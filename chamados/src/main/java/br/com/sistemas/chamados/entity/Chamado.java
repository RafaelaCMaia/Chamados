
package br.com.sistemas.chamados.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chamados")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(nullable = false, length = 1000)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusChamado status = StatusChamado.ABERTO;

    @Column(nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    public Chamado() {}

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String valor) {
        this.titulo = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String valor) {
        this.descricao = valor;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public void setStatus(StatusChamado valor) {
        this.status = valor;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime valor) {
        this.criadoEm = valor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente valor) {
        this.cliente = valor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria valor) {
        this.categoria = valor;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico valor) {
        this.tecnico = valor;
    }
}
