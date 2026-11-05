
package br.com.sistemas.chamados.controller;

import br.com.sistemas.chamados.dto.ChamadoRequest;
import br.com.sistemas.chamados.dto.ChamadoResponse;
import br.com.sistemas.chamados.entity.StatusChamado;
import br.com.sistemas.chamados.service.ChamadoService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chamados")
public class ChamadoController {

    private final ChamadoService service;

    public ChamadoController(ChamadoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ChamadoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ChamadoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChamadoResponse criar(
            @Valid @RequestBody ChamadoRequest request) {
        return service.criar(request);
    }

    @PutMapping("/{id}")
    public ChamadoResponse atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ChamadoRequest request) {
        return service.atualizar(id, request);
    }

    @PatchMapping("/{id}/status")
    public ChamadoResponse alterarStatus(
            @PathVariable Long id,
            @RequestParam StatusChamado status) {
        return service.alterarStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
