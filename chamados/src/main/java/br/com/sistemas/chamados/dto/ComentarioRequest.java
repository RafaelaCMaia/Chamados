
package br.com.sistemas.chamados.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ComentarioRequest(

    @NotBlank(message = "Texto é obrigatório")
    @Size(max = 1000)
    String texto,

    @NotNull(message = "Chamado é obrigatório")
    Long chamadoId

) {}
