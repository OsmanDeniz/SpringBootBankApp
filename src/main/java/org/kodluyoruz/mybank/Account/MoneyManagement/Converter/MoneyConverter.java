package org.kodluyoruz.mybank.Account.MoneyManagement.Converter;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class MoneyConverter implements CommandLineRunner {

    private RestTemplate restTemplate;

    public MoneyConverter(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.rootUri("https://api.exchangeratesapi.io").build();
    }

    public void run(String... args) throws Exception {
        //readCurrencyXtoY("EUR", "TRY");
    }

    private double readCurrencyXtoY(String baseParameter, String getCurrency) throws JsonProcessingException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("base", baseParameter);
        RatesEntity rates = restTemplate.getForObject("/latest?base={base}", RatesEntity.class, parameters);

        Map<String, Object> map = new ObjectMapper().convertValue(rates.getRates(), Map.class);
        return Double.valueOf(String.valueOf(map.get(getCurrency)));

//        System.out.println("+++++++++readTry++++++++++++");
//        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(rates));
    }

    public double convertXtoY(Double amount, String sourceCurrency, String targetCurrency) throws JsonProcessingException {
        Double foreignCurrency = readCurrencyXtoY(sourceCurrency, targetCurrency);
        if (foreignCurrency.equals(0)) throw new ArithmeticException("Muhtemel sifira bolme hatasi");
        return amount / foreignCurrency;
    }

}
