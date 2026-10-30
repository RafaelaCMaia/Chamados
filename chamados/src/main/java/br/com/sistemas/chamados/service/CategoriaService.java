
package br.com.sistemas.chamados.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sistemas.chamados.entity.*;
import br.com.sistemas.chamados.dto.*;
import br.com.sistemas.chamados.repository.*;
import br.com.sistemas.chamados.exception.*;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    private Categoria buscarEntidade(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new RecursoNaoEncontradoException(
                    "Categoria não encontrada: " + id
                ));
    }

    private CategoriaResponse converter(Categoria item) {
        return new CategoriaResponse(
            item.getId(),
            item.getNome()
        );
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponse> listar() {
        return repository.findAll()
            .stream()
            .map(this::converter)
            .toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResponse buscar(Long id) {
        return converter(buscarEntidade(id));
    }

    @Transactional
    public CategoriaResponse criar(CategoriaRequest request) {
        Categoria item = new Categoria();
        preencher(item, request);

        return converter(repository.save(item));
    }

    @Transactional
    public CategoriaResponse atualizar(Long id, CategoriaRequest request) {
        Categoria item = buscarEntidade(id);
        preencher(item, request);

        return converter(repository.save(item));
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(buscarEntidade(id));
    }

    private void preencher(Categoria item, CategoriaRequest request) {
        item.setNome(request.nome());
    }
}
