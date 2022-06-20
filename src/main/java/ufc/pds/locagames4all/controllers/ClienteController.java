//package ufc.pds.locagames4all.controllers;
//
//import ufc.pds.locagames4all.model.Cliente;
//import ufc.pds.locagames4all.repositories.ClienteRepository;
//
//public class ClienteController {
//    ClienteRepository clienteRepository;
//
//    public ClienteController(ClienteRepository clienteRepository) {
//        this.clienteRepository = clienteRepository;
//    }
//
//    public Cliente cadastrar(Cliente cliente){
//        return clienteRepository.salvar(cliente);
//    }
//
//    public Cliente buscarPorCPF(String cpf){
//        return clienteRepository.buscarPorCPF(cpf);
//    }
//
//    public Cliente atualizar(Cliente newCliente){
//        Cliente oldCliente = buscarPorCPF(newCliente.getCpf());
//        if(oldCliente != null){
//            oldCliente.setNome(newCliente.getNome());
//            oldCliente.setCpf(newCliente.getCpf());
//            oldCliente.setEndereco(newCliente.getEndereco());
//            oldCliente.setTelefone(newCliente.getTelefone());
//            oldCliente.setEmail(newCliente.getEmail());
//            return clienteRepository.buscarPorCPF(newCliente.getCpf());
//        }
//        return null;
//    }
//
//    public Cliente excluir(String cpf){
//        return clienteRepository.excluir(cpf);
//    }
//}