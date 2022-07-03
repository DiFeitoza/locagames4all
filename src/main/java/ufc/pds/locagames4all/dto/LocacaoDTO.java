package ufc.pds.locagames4all.dto;

import lombok.Data;
import ufc.pds.locagames4all.model.Cliente;
import ufc.pds.locagames4all.model.Jogo;
import ufc.pds.locagames4all.model.Locacao;

import java.time.LocalDate;

@Data
public class LocacaoDTO {
    private String cpf;
    private Long jogoId;
    private LocalDate dataDaLocacao;
    private LocalDate dataPrevistaDevolucao;

    public LocacaoDTO(String cpf, Long jogoId, LocalDate dataDaLocacao, LocalDate dataPrevistaDevolucao) {
        this.cpf = cpf;
        this.jogoId = jogoId;
        this.dataDaLocacao = dataDaLocacao;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }

    public LocacaoDTO(Locacao locacao) {
        this.cpf = locacao.getCliente().getCpf();
        this.jogoId = locacao.getJogo().getId();
        this.dataDaLocacao = locacao.getDataDaLocacao();
        this.dataPrevistaDevolucao = locacao.getDataPrevistaDevolucao();
    }

    public Locacao toModel(Cliente cliente, Jogo jogo) {
        Locacao model = new Locacao();
        model.setCliente(cliente);
        model.setJogo(jogo);
        model.setValorDaDiariaNaLocacao(jogo.getValorDiaria());
        model.setDataDaLocacao(this.dataDaLocacao);
        model.setDataPrevistaDevolucao(this.dataPrevistaDevolucao);
        return model;
    }
}
