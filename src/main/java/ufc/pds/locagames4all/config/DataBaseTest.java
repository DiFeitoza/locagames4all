package ufc.pds.locagames4all.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import ufc.pds.locagames4all.enums.TipoJogo;
import ufc.pds.locagames4all.model.Cliente;
import ufc.pds.locagames4all.model.Jogo;
import ufc.pds.locagames4all.model.Locacao;
import ufc.pds.locagames4all.repositories.ClienteRepositoryJPA;
import ufc.pds.locagames4all.repositories.JogoRepositoryJPA;
import ufc.pds.locagames4all.repositories.LocacaoRepositoryJPA;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
@Profile("test")
public class DataBaseTest implements CommandLineRunner {

    @Autowired
    private ClienteRepositoryJPA clienteRepository;

    @Autowired
    private JogoRepositoryJPA jogoRepositoryJPA;

    @Autowired
    private LocacaoRepositoryJPA locacaoRepositoryJPA;
    @Override
    public void run(String... args)  {
        Cliente c1 = new Cliente("Ana", "11122233344", "rua JK, 72","8899911223344","ana@bol.com");
        Cliente c2 = new Cliente("Borges", "51418818305", "rua JK, 81","8599922334455","borges@bol.com");
        Cliente c3 = new Cliente("Carlos", "35262405312", "avenida barao do rio branco","998855664422","carlinhos@bol.com");
        Cliente c4 = new Cliente("Duda",   "74412219356", "rua do cedro.","85996543211","duda@bol.com");


        clienteRepository.saveAll(Arrays.asList(c1,c2,c3,c4));

        Jogo j1 = new Jogo("LoTR", TipoJogo.RPG,"Senhor dos aneis",1,8,15.0);
        Jogo j2 = new Jogo("Xadrez", TipoJogo.TABULEIRO,"Xadrez",2,2,10.0);
        Jogo j3 = new Jogo("Dama", TipoJogo.TABULEIRO,"Dama",2,2,10.0);
        Jogo j4 = new Jogo("Poker", TipoJogo.CARTAS,"Baralho para Poker",2,9,12.0);

        jogoRepositoryJPA.saveAll(Arrays.asList(j1,j2,j3,j4));

        Locacao l1 = new Locacao(c1,j1, LocalDate.parse("2022-06-01"), LocalDate.parse("2021-06-06"),LocalDate.parse("2021-06-06"));

        locacaoRepositoryJPA.save(l1);
    }
}
