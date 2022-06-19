package ufc.pds.locagames4all.facade;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufc.pds.locagames4all.model.Cliente;
import ufc.pds.locagames4all.controllers.ClienteController;
import ufc.pds.locagames4all.repositories.ClienteRepository;

@RestController
public class ClienteFacade {
    ClienteController clienteController;

    @GetMapping("/")
    //http://localhost:8080/?campo=teste
    public String inicio(@RequestParam(value = "campo", defaultValue = "Valor default") String campo) {
        return "Oi, tenho vida. Você me falou '" + campo + "'";
    }

    @PostMapping("/clientes")
    public ResponseEntity<String> cadastrarCliente(@RequestBody Cliente cliente) {
        return new ResponseEntity<>(clienteController.cadastrar(cliente).toString(), HttpStatus.CREATED);
    }

    @GetMapping("/clientes")
    public ResponseEntity<String> buscarClientePorCPF(@RequestParam(value = "cpf") String cpf) {
        Cliente cliente = clienteController.buscarPorCPF(cpf);
        if(cliente == null){
            return new ResponseEntity<>("O CPF informado não pertence a um cliente cadastrado",
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(cliente.toString(), HttpStatus.OK);
        }

    }

    @PutMapping("/clientes")
    public ResponseEntity<String> atualizarCadastroDoCliente(@RequestBody Cliente cliente) {
        Cliente cliente1 = clienteController.atualizar(cliente);
        if(cliente1 == null){
            return new ResponseEntity<>("O CPF informado não pertence a um cliente cadastrado",
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(cliente1.toString(), HttpStatus.OK);
        }
    }

    @DeleteMapping ("/clientes")
    public ResponseEntity<String> excluirCadastroDoCliente(@RequestParam(value = "cpf") String cpf) {
        Cliente cliente = clienteController.excluir(cpf);
        if(cliente == null){
            return new ResponseEntity<>("O CPF informado não pertence a um cliente cadastrado", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cadastro do cliente excluído com sucesso: \n" + cliente, HttpStatus.OK);
        }
    }

    @Bean
    CommandLineRunner inicializar(){
        return args -> {
            System.out.println("inicializacao da fachada de Cliente");

            clienteController = new ClienteController(new ClienteRepository());
        };
    }
}