package ufc.pds.locagames4all.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;
import ufc.pds.locagames4all.enums.StatusJogo;
import ufc.pds.locagames4all.enums.TipoJogo;
import ufc.pds.locagames4all.model.Jogo;
import ufc.pds.locagames4all.repositories.JogoRepositoryJPA;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;

@Service
public class JogoService {

    @Autowired
    private JogoRepositoryJPA JogoRepository;

    private static final String MSG_ENTITY_NOT_FOUND = "Jogo não encontrado.";
    private static final String MSG_JOGOS_NÃO_ENCONTRADOS = "jogos não encontrados";
    public List<Jogo> buscarTodosJogos(){
        return  JogoRepository.findAll();
    }

    public List<Jogo> buscarJogosPorTipo(TipoJogo tipo){
        List<Jogo> jogos =  JogoRepository.findByTipo(tipo);
        if(jogos.isEmpty()){
            throw new EntityNotFoundException(MSG_JOGOS_NÃO_ENCONTRADOS);
        }else
            return jogos;
    }

    public List<Jogo> buscarJogosPorStatus(StatusJogo status){
        List<Jogo> jogos =  JogoRepository.findByStatus(status);
        if(jogos.isEmpty()){
            throw new EntityNotFoundException(MSG_JOGOS_NÃO_ENCONTRADOS);
        }else
            return jogos;
    }

    public Jogo excluirJogo(Long id){
        Jogo jogo = JogoRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(MSG_ENTITY_NOT_FOUND));
        jogo.setExcluido(true);
        jogo.setStatus(StatusJogo.INDISPONIVEL);
        return jogo;
    }
}
