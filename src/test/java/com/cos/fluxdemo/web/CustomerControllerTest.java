package com.cos.fluxdemo.web;

import com.cos.fluxdemo.domain.Customer;
import com.cos.fluxdemo.domain.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;

@WebFluxTest
//@SpringBootTest
public class CustomerControllerTest {

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private WebTestClient webClient;

    @Test
    public void 한건찾기_테스트(){

        // given
        Mono<Customer> givenData = Mono.just(new Customer("Jack","Bauer"));

        // stub -> 행동 지시
        when(customerRepository.findById(1L)).thenReturn(givenData);
        webClient.get().uri("/customer/{id}",1L)
                .exchange()
                .expectBody()
                .jsonPath("$.firstName").isEqualTo("Jack")
                .jsonPath("$.lastName").isEqualTo("Bauer");
    }

}
