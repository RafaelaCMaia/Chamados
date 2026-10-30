
package br.com.sistemas.chamados.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sistemas.chamados.entity.*;
import br.com.sistemas.chamados.dto.*;
import br.com.sistemas.chamados.repository.*;
import br.com.sistemas.chamados.exception.*;

@Service
public class ChamadoService {

    private final ChamadoRepository repository;
    private final ClienteRepository clienteRepository;
    private final CategoriaRepository categoriaRepository;
    private final TecnicoRepository tecnicoRepository;

    public ChamadoService(
        ChamadoRepository repository,
        ClienteRepository clienteRepository,
        CategoriaRepository categoriaRepository,
        TecnicoRepository tecnicoRepository
    ) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.categoriaRepository = categoriaRepository;
        this.tecnicoRepository = tecnicoRepository;
    }

    private Chamado buscarEntidade(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new RecursoNaoEncontradoException(
                    "Chamado não encontrado: " + id
                ));
    }

    private ChamadoResponse converter(Chamado item) {
        return new ChamadoResponse(
            item.getId(),
            item.getTitulo(),
            item.getDescricao(),
            item.getStatus(),
            item.getCriadoEm(),
            item.getCliente().getId(),
            item.getCategoria().getId(),
            item.getTecnico().getId()
        );
    }

    @Transactional(readOnly = true)
    public List<ChamadoResponse> listar() {
        return repository.findAll()
            .stream()
            .map(this::converter)
            .toList();
    }

    @Transactional(readOnly = true)
    public ChamadoResponse buscar(Long id) {
        return converter(buscarEntidade(id));
    }

    @Transactional
    public ChamadoResponse criar(ChamadoRequest request) {
        Chamado item = new Chamado();
        preencher(item, request);

        return converter(repository.save(item));
    }

    @Transactional
    public ChamadoResponse atualizar(Long id, ChamadoRequest request) {
        Chamado item = buscarEntidade(id);
        preencher(item, request);

        return converter(repository.save(item));
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(buscarEntidade(id));
    }

    private void preencher(Chamado item, ChamadoRequest request) {

        item.setTitulo(request.titulo());
        item.setDescricao(request.descricao());

        item.setCliente(
            request.clienteId() == null ? null :
            clienteRepository.findById(request.clienteId())
                .orElseThrow(() ->
                    new RecursoNaoEncontradoException(
                        "Cliente não encontrado"
                    ))
        );

        item.setCategoria(
            request.categoriaId() == null ? null :
            categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() ->
                    new RecursoNaoEncontradoException(
                        "Categoria não encontrada"
                    ))
        );

        item.setTecnico(
            request.tecnicoId() == null ? null :
            tecnicoRepository.findById(request.tecnicoId())
                .orElseThrow(() ->
                    new RecursoNaoEncontradoException(
                        "Técnico não encontrado"
                    ))
        );
    }

    @Transactional
    public ChamadoResponse alterarStatus(Long id, StatusChamado status) {
        Chamado item = buscarEntidade(id);

        if (item.getStatus() == StatusChamado.CONCLUIDO
                && status != StatusChamado.CONCLUIDO) {
            throw new RegraNegocioException(
                "Chamado concluído não pode ser reaberto"
            );
        }

        item.setStatus(status);

        return converter(repository.save(item));
    }
}
