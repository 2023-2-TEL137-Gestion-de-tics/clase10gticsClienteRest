package com.example.clase10gticsclienterest.dao;

import com.example.clase10gticsclienterest.entity.Product;
import com.example.clase10gticsclienterest.entity.ProductDto;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ProductoDao {

    public List<Product> listar() {

        List<Product> lista = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();

        String endPoint = "http://localhost:8080/product";

        ResponseEntity<Product[]> responseEntity = restTemplate.getForEntity(endPoint, Product[].class);

        if(responseEntity.getStatusCode().is2xxSuccessful()){
            Product[] body = responseEntity.getBody();
            lista = Arrays.asList(body);
        }

        return lista;
    }

    public void guardar(Product product){

        RestTemplate restTemplate = new RestTemplate();
        String endPoint = "http://localhost:8080/product";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Product> httpEntity = new HttpEntity<>(product,httpHeaders);

        if(product.getId() ==null){
            restTemplate.postForEntity(endPoint,httpEntity,Product.class);
        }else{
            restTemplate.put(endPoint,httpEntity,Product.class);
        }

    }

    public Product buscarPorId(int id){

        Product product = null;

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/product/" + id;

        ResponseEntity<ProductDto> forEntity = restTemplate.getForEntity(url, ProductDto.class);

        if(forEntity.getStatusCode().is2xxSuccessful()){
            ProductDto productDto = forEntity.getBody();
            product = productDto.getProducto();
        }

        return product;
    }

    public void deleteProductById(int id){

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.delete("http://localhost:8080/product?id="+id);
    }

}
