
package br.com.sistemas.chamados.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sistemas.chamados.entity.*;
import br.com.sistemas.chamados.dto.*;
import br.com.sistemas.chamados.repository.*;
import br.com.sistemas.chamados.exception.*;

@Service
public class ComentarioService {

    private final ComentarioRepository repository;
    private final ChamadoRepository chamadoRepository;

    public ComentarioService(
        ComentarioRepository repository,
        ChamadoRepository chamadoRepository
    ) {
        this.repository = repository;
        this.chamadoRepository = chamadoRepository;
    }

    private Comentario buscarEntidade(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new RecursoNaoEncontradoException(
                    "Comentário não encontrado: " + id
                ));
    }

    private ComentarioResponse converter(Comentario item) {
        return new ComentarioResponse(
            item.getId(),
            item.getTexto(),
            item.getCriadoEm(),
            item.getChamado().getId()
        );
    }

    @Transactional(readOnly = true)
    public List<ComentarioResponse> listar() {
        return repository.findAll()
            .stream()
            .map(this::converter)
            .toList();
    }

    @Transactional(readOnly = true)
    public ComentarioResponse buscar(Long id) {
        return converter(buscarEntidade(id));
    }

    @Transactional
    public ComentarioResponse criar(ComentarioRequest request) {
        Comentario item = new Comentario();
        preencher(item, request);

        return converter(repository.save(item));
    }

    @Transactional
    public ComentarioResponse atualizar(Long id, ComentarioRequest request) {
        Comentario item = buscarEntidade(id);
        preencher(item, request);

        return converter(repository.save(item));
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(buscarEntidade(id));
    }

    private void preencher(Comentario item, ComentarioRequest request) {

        item.setTexto(request.texto());

        item.setChamado(
            request.chamadoId() == null ? null :
            chamadoRepository.findById(request.chamadoId())
                .orElseThrow(() ->
                    new RecursoNaoEncontradoException(
                        "Chamado não encontrado"
                    ))
        );
    }
}
