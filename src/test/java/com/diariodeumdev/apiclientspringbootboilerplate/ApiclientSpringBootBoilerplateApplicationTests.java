package com.diariodeumdev.apiclientspringbootboilerplate;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiClientSpringBootBoilerplateApplicationTests {

    @Mock
    private SpringApplicationBuilder springApplicationBuilder;

    @Test
    void contextLoads() {
    }

    @Test
    public void main() {
        ApiClientSpringBootBoilerplateApplication.main(new String[]{});
    }

}
