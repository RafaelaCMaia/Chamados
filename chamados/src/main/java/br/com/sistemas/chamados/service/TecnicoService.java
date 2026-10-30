
package br.com.sistemas.chamados.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sistemas.chamados.entity.*;
import br.com.sistemas.chamados.dto.*;
import br.com.sistemas.chamados.repository.*;
import br.com.sistemas.chamados.exception.*;

@Service
public class TecnicoService {

    private final TecnicoRepository repository;

    public TecnicoService(TecnicoRepository repository) {
        this.repository = repository;
    }

    private Tecnico buscarEntidade(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new RecursoNaoEncontradoException(
                    "Técnico não encontrado: " + id
                ));
    }

    private TecnicoResponse converter(Tecnico item) {
        return new TecnicoResponse(
            item.getId(),
            item.getNome(),
            item.getEmail()
        );
    }

    @Transactional(readOnly = true)
    public List<TecnicoResponse> listar() {
        return repository.findAll()
            .stream()
            .map(this::converter)
            .toList();
    }

    @Transactional(readOnly = true)
    public TecnicoResponse buscar(Long id) {
        return converter(buscarEntidade(id));
    }

    @Transactional
    public TecnicoResponse criar(TecnicoRequest request) {
        Tecnico item = new Tecnico();
        preencher(item, request);

        return converter(repository.save(item));
    }

    @Transactional
    public TecnicoResponse atualizar(Long id, TecnicoRequest request) {
        Tecnico item = buscarEntidade(id);
        preencher(item, request);

        return converter(repository.save(item));
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(buscarEntidade(id));
    }

    private void preencher(Tecnico item, TecnicoRequest request) {
        item.setNome(request.nome());
        item.setEmail(request.email());
    }
}
