package ufc.pds.locagames4all.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufc.pds.locagames4all.dto.LocacaoDTO;
import ufc.pds.locagames4all.model.Cliente;
import ufc.pds.locagames4all.model.Jogo;
import ufc.pds.locagames4all.model.Locacao;
import ufc.pds.locagames4all.repositories.LocacaoRepositoryJPA;

import javax.persistence.EntityNotFoundException;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class LocacaoService {

    private static final String MSG_ENTITY_NOT_FOUND = "Locação não encontrada.";
    private static final String MSG_JOGOS_NAO_ENCONTRADOS = "jogos não encontrados";

    @Autowired
    private LocacaoRepositoryJPA locacaoRepositoryJPA;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private JogoService jogoService;

    public Locacao cadastrarLocacao(LocacaoDTO locacaoDTO) {
        Cliente cliente = clienteService.buscarClientePorCpf(locacaoDTO.getCpf());
        Jogo jogo = jogoService.buscarJogoPorId(locacaoDTO.getJogoId());
        locacaoDTO.setDataDaDevolucao(null);
        return locacaoRepositoryJPA.save(locacaoDTO.toModel(cliente, jogo));
    }

    public List<Locacao> buscarTodasLocacoes() {
        return locacaoRepositoryJPA.findAll();
    }

    public Locacao buscarLocacoesPorId(Long id) {
        return locacaoRepositoryJPA.findById(id).orElseThrow(() -> new EntityNotFoundException(MSG_ENTITY_NOT_FOUND));
    }

    public List<Locacao> buscarHistoricoDeLocacoesPorCPF(String cpf) {
        Cliente cliente = clienteService.buscarClientePorCpf(cpf);
        List<Locacao> locacoes = locacaoRepositoryJPA.findByClienteId(cliente.getId());
        if (locacoes.isEmpty()) {
            throw new EntityNotFoundException(MSG_JOGOS_NAO_ENCONTRADOS);
        } else {
            return locacoes;
        }
    }

    public List<Locacao> buscarLocacoesAtivasPorCPF(String cpf) {
        Cliente cliente = clienteService.buscarClientePorCpf(cpf);
        List<Locacao> locacoes = locacaoRepositoryJPA.findByClienteIdAndDataDaDevolucaoIsNull(cliente.getId());
        if (locacoes.isEmpty()) {
            throw new EntityNotFoundException(MSG_JOGOS_NAO_ENCONTRADOS);
        } else {
            return locacoes;
        }
    }

    public List<Locacao> buscarHistoricoDeLocacoesPorJogoId(Long jogoId) {
        List<Locacao> locacoes = locacaoRepositoryJPA.findByJogoId(jogoId);
        if (locacoes.isEmpty()) {
            throw new EntityNotFoundException(MSG_JOGOS_NAO_ENCONTRADOS);
        } else {
            return locacoes;
        }
    }

    public List<Locacao> buscarLocacoesPorCPFeJogoId(String cpf, Long jogoId) {
        Cliente cliente = clienteService.buscarClientePorCpf(cpf);
        List<Locacao> locacoes = locacaoRepositoryJPA.findByClienteIdAndJogoId(cliente.getId(), jogoId);
        if (locacoes.isEmpty()) {
            throw new EntityNotFoundException(MSG_JOGOS_NAO_ENCONTRADOS);
        } else {
            return locacoes;
        }
    }

    public Locacao consultarLocacaoParaDevolucao(Long id) {
        Locacao locacao = buscarLocacoesPorId(id);
        Long diferencaEmDias = ChronoUnit.DAYS.between(locacao.getDataDaDevolucao(), locacao.getDataPrevistaDevolucao());
        locacao.setSaldo(diferencaEmDias * locacao.getValorDaDiariaNaLocacao());
        return locacao;
    }
}