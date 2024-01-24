package br.com.elotech.desafio.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.elotech.desafio.entity.Endereco;
import br.com.elotech.desafio.service.response.CEPResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
public class CEPService {

    RestTemplate restTemplate;

    public CEPService(){
        restTemplate = new RestTemplate();
    }

    public Endereco getEnderecoByCEP(String cep){
        if (cep.length() != 8){
            throw new IllegalArgumentException("O CEP deve possuir 8 números!");
        }
        String url = "https://viacep.com.br/ws/"+cep+"/json";

        CEPResponse cepResponse = restTemplate.getForObject(url, CEPResponse.class);
        if ((cepResponse != null)) {
            if (cepResponse.getErro() != null && cepResponse.getErro()) {
                throw new IllegalArgumentException("CEP inválido!");
            }
            Endereco endereco = new Endereco();
            endereco.setCep(cep);
            endereco.setLogradouro(cepResponse.getLogradouro());
            endereco.setNumero(cepResponse.getNumero());
            endereco.setCidade(cepResponse.getLocalidade());
            endereco.setUf(cepResponse.getUf());
            return endereco;
        } else {
            throw new IllegalArgumentException("Não foi possível obter informações do CEP informado.");
        }
    }
}
