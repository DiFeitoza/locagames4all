package ufc.pds.locagames4all.dto;

import lombok.Data;
import ufc.pds.locagames4all.model.Cliente;

@Data
public class ClienteDTO {
    private String nome;
    private String cpf;
    private String endereco;
    private String telefone;
    private String email;

    public ClienteDTO(String nome, String cpf, String endereco, String telefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }

    public ClienteDTO(Cliente cliente) {
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.endereco = cliente.getEndereco();
        this.telefone = cliente.getTelefone();
        this.email = cliente.getEmail();
    }

    public Cliente toModel() {
        Cliente cliente = new Cliente();
        cliente.setNome(this.getNome());
        cliente.setCpf(this.getCpf());
        cliente.setEndereco(this.getEndereco());
        cliente.setTelefone(this.getTelefone());
        cliente.setEmail(this.getEmail());
        return cliente;
    }
}
