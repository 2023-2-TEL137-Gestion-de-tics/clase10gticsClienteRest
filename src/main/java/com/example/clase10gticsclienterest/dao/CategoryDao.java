package com.example.clase10gticsclienterest.dao;

import com.example.clase10gticsclienterest.entity.Category;
import com.example.clase10gticsclienterest.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CategoryDao {

    public List<Category> listar() {

        List<Category> lista = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();

        String endPoint = "http://localhost:8080/category";

        ResponseEntity<Category[]> responseEntity = restTemplate.getForEntity(endPoint, Category[].class);

        if(responseEntity.getStatusCode().is2xxSuccessful()){
            Category[] body = responseEntity.getBody();
            lista = Arrays.asList(body);
        }

        return lista;
    }
}
