
package br.com.sistemas.chamados.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.sistemas.chamados.repository.ClienteRepository;
import br.com.sistemas.chamados.entity.Cliente;
import br.com.sistemas.chamados.dto.*;
import br.com.sistemas.chamados.exception.*;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    private Cliente encontrar(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new RecursoNaoEncontradoException("Cliente não encontrado"));
    }

    private ClienteResponse converter(Cliente c) {
        return new ClienteResponse(
            c.getId(),
            c.getNome(),
            c.getEmail(),
            c.getTelefone()
        );
    }

    public List<ClienteResponse> listar() {
        return repository.findAll()
            .stream()
            .map(this::converter)
            .toList();
    }

    public ClienteResponse buscar(Long id) {
        return converter(encontrar(id));
    }

    public ClienteResponse criar(ClienteRequest r) {
        return converter(repository.save(
            new Cliente(r.nome(), r.email(), r.telefone())
        ));
    }

    public ClienteResponse atualizar(Long id, ClienteRequest r) {
        Cliente c = encontrar(id);

        c.setNome(r.nome());
        c.setEmail(r.email());
        c.setTelefone(r.telefone());

        return converter(repository.save(c));
    }

    public void excluir(Long id) {
        repository.delete(encontrar(id));
    }
}
