package ufc.pds.locagames4all.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ClienteService clienteService;

    @Autowired
    private JogoService jogoService;

    @GetMapping

    public List<Locacao> buscarClientes() {
        return locacaoService.buscarTodasLocacoes();
    }

    @PostMapping
    public Locacao cadastrarLocacao(@RequestBody LocacaoDTO locacaoDTO){
        Cliente cliente = clienteService.buscarClientePorCpf(locacaoDTO.getCpf());
        Jogo jogo = jogoService.buscarJogoPorId(locacaoDTO.getJogoId());
        return  locacaoService.cadastrarLocacao(locacaoDTO.toModel(cliente,jogo));
    }

    @GetMapping("/{id}")
    public LocacaoDTO buscarLocacaoPorId(@PathVariable Long id){
        Locacao locacao = locacaoService.buscarLocacaoPorId(id);
        return  new LocacaoDTO(locacao);
    }
}
