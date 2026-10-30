
package br.com.sistemas.chamados.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequest(

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100)
    String nome

) {}
