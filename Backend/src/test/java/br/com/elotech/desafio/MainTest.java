package br.com.elotech.desafio;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

public class MainTest {
    @Test
    void main() {
        Mockito.mockStatic(SpringApplication.class);

        Mockito.when(SpringApplication.run(Main.class, "TESTE", "TESTE"))
                .thenReturn(null);

        Main.main(new String[]{"TESTE", "TESTE"});
        SpringApplication.run(Main.class, "TESTE", "TESTE");
    }
}
