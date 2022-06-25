package ufc.pds.locagames4all.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufc.pds.locagames4all.model.Cliente;
import ufc.pds.locagames4all.model.Locacao;
import ufc.pds.locagames4all.repositories.LocacaoRepositoryJPA;

import javax.persistence.EntityNotFoundException;
import java.util.List;



@Service
public class LocacaoService {

    private static final String MSG_ENTITY_NOT_FOUND = "Locação não encontrada.";
    @Autowired
    private LocacaoRepositoryJPA locacaoRepositoryJPA;

    private ClienteService clienteService;

    public List<Locacao> buscarTodasLocacoes(){
       return  locacaoRepositoryJPA.findAll();
    }

    public List<Locacao> buscarTodasLocacoesPorUsuario(String cpf){
        Cliente cliente = clienteService.buscarClientePorCpf(cpf);
        return locacaoRepositoryJPA.findByClienteId(cliente.getId());
    }
    public Locacao cadastrarLocacao(Locacao locacao){
        return locacaoRepositoryJPA.save(locacao);
    }

    public Locacao buscarLocacaoPorId(Long id){
        return  locacaoRepositoryJPA.findById(id).orElseThrow(()-> new EntityNotFoundException(MSG_ENTITY_NOT_FOUND));
    }
}
