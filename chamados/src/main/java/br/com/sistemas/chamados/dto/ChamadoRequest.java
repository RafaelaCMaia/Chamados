
package br.com.sistemas.chamados.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ChamadoRequest(

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 120)
    String titulo,

    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 1000)
    String descricao,

    @NotNull(message = "Cliente é obrigatório")
    Long clienteId,

    @NotNull(message = "Categoria é obrigatória")
    Long categoriaId,

    Long tecnicoId

) {}
