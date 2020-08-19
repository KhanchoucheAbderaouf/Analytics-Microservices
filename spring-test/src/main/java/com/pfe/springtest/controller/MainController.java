package com.pfe.springtest.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import com.pfe.springtest.model.AuthenticationRequest;
import com.pfe.springtest.model.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/requests")
public class MainController {


    @Autowired
    private RestTemplate restTemplate;

/*
    @Autowired
    WebClient.Builder webClientBuilder;
*/


    @Autowired
    private JdbcTemplate jdbcTemplate;


    /*@HystrixCommand(fallbackMethod = "getFallbackRequest",
            commandProperties = {
                        @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "5000"),
                        @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "5"),
                        @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "50"),
                        @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "5000"),
            }
            bulkhead pattern

            fallbackmethod .., threadPoolKey = "geg",threadPoolProperties={
                        @HystrixProperty(name="coreSize",value = "20"),
                        @HystrixProperty(name="maxQueueSize",value = "10"),

        )*/
    @GetMapping("/{req}")
    public List<Map<String, Object>> Requests(@PathVariable("req") String req) {
        System.out.println(req);
        List<Map<String, Object>> a = jdbcTemplate.queryForList(req);
        return a;

    }

    @GetMapping(value = "/authenticate/{username}/{password}", consumes = "application/json", produces = "application/json")
    public void test(@PathVariable("username") String username, @PathVariable("password") String password) {
        System.out.println(username + " " + password);
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(username, password);
        // AuthenticationRequest authenticationRequest = new AuthenticationRequest("zinou","zinou");
        String jwt =
                restTemplate.postForObject("http://users-manager-jwt/authenticate", authenticationRequest, String.class);
        System.out.println(jwt);
        System.out.println();
    }
}
        /*HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+);
    }

    }




/* EXEMPLE OF WEB CLIENT
Alternative WebClient way
Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/"+ rating.getMovieId())
.retrieve().bodyToMono(Movie.class).block();
*/

/*ObjectMapper objectMapper =  new ObjectMapper();
            String json = objectMapper.writeValueAsString(a);
            System.out.println(json);*/