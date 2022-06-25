package ufc.pds.locagames4all.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LOCACAO")
@Data
public class Locacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "Jogo_id")
    private Jogo jogo;

    private LocalDate dataDaLocacao;

    private LocalDate dataPrevistaDevolucao;

    private LocalDate dataDaDevolucao;

    public Locacao(Cliente cliente, Jogo jogo, LocalDate dataDaLocacao, LocalDate dataPrevistaDevolucao, LocalDate dataDaDevolucao) {
        this.cliente= cliente;
        this.jogo= jogo;
        this.dataDaLocacao= dataDaLocacao;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
        this.dataDaDevolucao = dataDaDevolucao;
    }

}
