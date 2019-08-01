package br.com.devdojo.examgeneration.persistence.dao;

import br.com.devdojo.examgeneration.annotation.ExceptionHandler;
import br.com.devdojo.examgeneration.custom.CustomObjectMapper;
import br.com.devdojo.examgeneration.custom.CustomRestTemplate;
import br.com.devdojo.examgeneration.persistence.model.Token;
import br.com.devdojo.examgeneration.persistence.model.support.ErrorDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;

import static br.com.devdojo.examgeneration.util.ApiUtil.LOGIN_URL;
import static org.springframework.http.HttpMethod.POST;

public class LoginDao implements Serializable {

    private final CustomRestTemplate restTemplate;

    @Inject
    public LoginDao(CustomRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @ExceptionHandler
    public Token loginReturnToken(String username, String password) {
        String loginJson = "{\"username\":" + addQuotes(username) + ",\"password\":" + addQuotes(password) + "}";
        ResponseEntity<Token> tokenExchange = restTemplate
                .exchange(LOGIN_URL, POST, new HttpEntity<>(loginJson, creatJsonHeader()), Token.class);
        return tokenExchange.getBody();

    }

    private String addQuotes(String value) {
        return new StringBuilder(300).append("\"").append(value).append("\"").toString();
    }

    //Criar o header do JSON
    private HttpHeaders creatJsonHeader() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return header;
    }
}
