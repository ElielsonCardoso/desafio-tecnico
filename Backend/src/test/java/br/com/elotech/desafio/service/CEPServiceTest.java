package br.com.elotech.desafio.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import br.com.elotech.desafio.service.response.CEPResponse;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CEPServiceTest {

    @Test
    public void testGetEnderecoByCEP(){
        String url = "https://viacep.com.br/ws/12345678/json";

        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.getForObject(url, CEPResponse.class)).thenReturn(null);

        CEPService cepService = new CEPService(restTemplate);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cepService.getEnderecoByCEP("12345678");
        });
    }
}
