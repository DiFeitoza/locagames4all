package ufc.pds.locagames4all.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufc.pds.locagames4all.dto.LocacaoDTO;
import ufc.pds.locagames4all.model.Cliente;
import ufc.pds.locagames4all.model.Jogo;
import ufc.pds.locagames4all.model.Locacao;
import ufc.pds.locagames4all.service.ClienteService;
import ufc.pds.locagames4all.service.JogoService;
import ufc.pds.locagames4all.service.LocacaoService;

import java.util.List;

@RestController
@RequestMapping("/locacoes")
@Tag(name = "Locacao")
public class LocacaoController {
    @Autowired
    private LocacaoService locacaoService;

    @Autowired
    private JogoService jogoService;

    private final ClienteService clienteService;

    public LocacaoController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Locacao> cadastrarLocacao(@RequestBody LocacaoDTO locacaoDTO){
        Cliente cliente = clienteService.buscarClientePorCpf(locacaoDTO.getCpf());
        Jogo jogo = jogoService.buscarJogoPorId(locacaoDTO.getJogoId());
        locacaoDTO.setDataDaDevolucao(null);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(locacaoService.cadastrarLocacao(locacaoDTO.toModel(cliente, jogo)));
    }

    @GetMapping
    public ResponseEntity<List<Locacao>> buscarLocacoes() {
        return ResponseEntity.ok().body(locacaoService.buscarTodasLocacoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Locacao> buscarLocacaoPorId(@PathVariable Long id){
        return ResponseEntity.ok().body(locacaoService.buscarLocacoesPorId(id));
    }

    @GetMapping("/jogoid/{id}")
    public ResponseEntity<List<Locacao>> buscarHistoricoDeLocacoesPorJogoId(@PathVariable Long id){
        List<Locacao> locacoes = locacaoService.buscarHistoricoDeLocacoesPorJogoId(id);
        return ResponseEntity.ok().body(locacoes);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<Locacao>> buscarHistoricoDeLocacoesPorCPF(
            @PathVariable String cpf,
            @RequestParam(name = "locacaoativa", required = false, defaultValue = "false") boolean locacaoativa
    ){
        if(locacaoativa){
            return ResponseEntity.ok().body(locacaoService.buscarLocacoesAtivasPorCPF(cpf));
        } else {
            return ResponseEntity.ok().body(locacaoService.buscarHistoricoDeLocacoesPorCPF(cpf));
        }
    }
}
