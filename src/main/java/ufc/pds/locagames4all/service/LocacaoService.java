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
    private static final String MSG_JOGOS_NAO_ENCONTRADOS = "jogos não encontrados";

    @Autowired
    private LocacaoRepositoryJPA locacaoRepositoryJPA;

    @Autowired
    private ClienteService clienteService;

    public Locacao cadastrarLocacao(Locacao locacao){
        return locacaoRepositoryJPA.save(locacao);
    }

    public List<Locacao> buscarTodasLocacoes(){
       return  locacaoRepositoryJPA.findAll();
    }

    public Locacao buscarLocacoesPorId(Long id){
        return  locacaoRepositoryJPA.findById(id).orElseThrow(()-> new EntityNotFoundException(MSG_ENTITY_NOT_FOUND));
    }

    public List<Locacao> buscarHistoricoDeLocacoesPorCPF(String cpf){
        Cliente cliente = clienteService.buscarClientePorCpf(cpf);
        List<Locacao> locacoes = locacaoRepositoryJPA.findByClienteId(cliente.getId());
        if(locacoes.isEmpty()){
            throw new EntityNotFoundException(MSG_JOGOS_NAO_ENCONTRADOS);
        } else {
            return locacoes;
        }
    }

    public List<Locacao> buscarHistoricoDeLocacoesPorJogoId(Long jogoId) {
        List<Locacao> locacoes = locacaoRepositoryJPA.findByJogoId(jogoId);
        if(locacoes.isEmpty()){
            throw new EntityNotFoundException(MSG_JOGOS_NAO_ENCONTRADOS);
        } else {
            return locacoes;
        }
    }
}
