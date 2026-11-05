
package br.com.sistemas.chamados.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TecnicoRequest(

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100)
    String nome,

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 120)
    String email

) {}
    