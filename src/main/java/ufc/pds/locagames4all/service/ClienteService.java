package ufc.pds.locagames4all.service;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufc.pds.locagames4all.model.Cliente;
import ufc.pds.locagames4all.repositories.ClienteRepositoryJPA;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepositoryJPA clienteRepository;

    private static final String MSG_ENTITY_NOT_FOUND = "Cliente não encontrado.";

    public List<Cliente> buscarTodosCLientes(){
        return  clienteRepository.findAll();
    }

    public Cliente buscarClientePorId(Long id){
        return clienteRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(MSG_ENTITY_NOT_FOUND));
    }

    public Cliente buscarClientePorCpf(String cpf){
        return clienteRepository.findByCpf(cpf).orElseThrow(()-> new EntityNotFoundException(MSG_ENTITY_NOT_FOUND));
    }

    public Cliente atualizaCliente(String cpf, Cliente clienteAtualizado){
        Cliente clientePersistido = clienteRepository.findByCpf(cpf).orElseThrow(()-> new EntityNotFoundException(MSG_ENTITY_NOT_FOUND));
        if(Boolean.TRUE.equals(validaAtualizacaoCliente(clientePersistido,clienteAtualizado))){
            return clienteRepository.save(clienteAtualizado);
        }else{
            throw new UnsupportedOperationException("Não foi possível concluir operação.");
        }
    }

    public Cliente desativaCliente(String cpf){
        Cliente clientePersistido = clienteRepository.findByCpf(cpf).orElseThrow(()-> new EntityNotFoundException(MSG_ENTITY_NOT_FOUND));
        if(BooleanUtils.isFalse(clientePersistido.getExcluido())){
            clientePersistido.setExcluido(true);
            clientePersistido.setNome(null);
            clientePersistido.setEmail(null);
            clientePersistido.setTelefone(null);
            clientePersistido.setEndereco(null);
            return clienteRepository.save(clientePersistido);
        }else{
            throw new UnsupportedOperationException("Não foi possível concluir operação.");
        }
    }

    public Cliente cadastrarCliente(Cliente cliente) {
        Optional<Cliente> clienteJaCadastrado = clienteRepository.findByCpf(cliente.getCpf());
        if (clienteJaCadastrado.isPresent()) {
            if(BooleanUtils.isTrue(clienteJaCadastrado.get().getExcluido())){
                cliente.setId(clienteJaCadastrado.get().getId());
                return clienteRepository.save(cliente);
            }else{
                throw new UnsupportedOperationException("Cliente Já cadastrado.");
            }
        }else{
            return clienteRepository.save(cliente);
        }
    }
    private Boolean validaAtualizacaoCliente(Cliente persistido, Cliente atualizado){
        return BooleanUtils.isTrue(persistido.getId().equals(atualizado.getId()));
    }
}
