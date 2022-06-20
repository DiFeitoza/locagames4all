package ufc.pds.locagames4all.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ufc.pds.locagames4all.model.Cliente;
import ufc.pds.locagames4all.repositories.ClienteRepositoryJPA;

import java.util.Arrays;

@Configuration
@Profile("test")
public class DataBaseTest implements CommandLineRunner {

    @Autowired
    private ClienteRepositoryJPA clienteRepository;

    @Override
    public void run(String... args) throws Exception {
        Cliente c1 = new Cliente("Ana", "11122233344", "rua JK, 72","8899911223344","ana@bol.com");
        Cliente c2 = new Cliente("Borges", "51418818305", "rua JK, 81","8599922334455","borges@bol.com");
        Cliente c3 = new Cliente("Carlos", "35262405312", "avenida barao do rio branco","998855664422","carlinhos@bol.com");
        Cliente c4 = new Cliente("Duda",   "74412219356", "rua do cedro.","85996543211","duda@bol.com");


        clienteRepository.saveAll(Arrays.asList(c1,c2,c3,c4));
    }
}
