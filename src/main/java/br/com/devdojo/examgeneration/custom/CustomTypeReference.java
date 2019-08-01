package br.com.devdojo.examgeneration.custom;

import org.springframework.core.ParameterizedTypeReference;

public class CustomTypeReference<T> extends ParameterizedTypeReference<T> {
    public ParameterizedTypeReference<T> typeReference(){
        return new ParameterizedTypeReference<T>(){};
    }
}
