package ufc.pds.locagames4all.repositories;

import org.springframework.stereotype.Component;
import ufc.pds.locagames4all.model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {
    List<Cliente> clientes = new ArrayList<>();

    public Cliente salvar(Cliente cliente) {
        clientes.add(cliente);
        System.out.println(clientes.get(0).toString());
        return buscarPorCPF(cliente.getCpf());
    }

    public Cliente buscarPorCPF(String cpf) {
        for(Cliente cliente : this.clientes) {
            if(cliente.getCpf().equals(cpf))
                return cliente;
        }
        return null;
    }

    public Cliente excluir(String cpf) {
        for(Cliente cliente : this.clientes) {
            if(cliente.getCpf().equals(cpf) && !cliente.getExcluido()) {
                cliente.setExcluido(true);
                return cliente;
            }
        }
        return null;
    }
}
