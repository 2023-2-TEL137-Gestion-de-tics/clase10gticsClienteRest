package com.example.clase10gticsclienterest.dao;

import com.example.clase10gticsclienterest.entity.Category;
import com.example.clase10gticsclienterest.entity.Supplier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SupplierDao {

    public List<Supplier> listar() {

        List<Supplier> lista = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();

        String endPoint = "http://localhost:8080/supplier";

        ResponseEntity<Supplier[]> responseEntity = restTemplate.getForEntity(endPoint, Supplier[].class);

        if(responseEntity.getStatusCode().is2xxSuccessful()){
            Supplier[] body = responseEntity.getBody();
            lista = Arrays.asList(body);
        }

        return lista;
    }
}
