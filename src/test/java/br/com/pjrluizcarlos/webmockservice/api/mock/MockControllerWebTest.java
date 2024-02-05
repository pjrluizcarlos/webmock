package br.com.pjrluizcarlos.webmockservice.api.mock;

import br.com.pjrluizcarlos.webmockservice.business.mock.Mock;
import br.com.pjrluizcarlos.webmockservice.business.mock.MockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MockController.class)
class MockControllerWebTest {

    private static final String URI = "/test-1";
    private static final HttpMethod HTTP_METHOD = HttpMethod.PUT;
    private static final HttpStatus HTTP_STATUS = HttpStatus.MULTI_STATUS;
    private static final String RESPONSE = "{\"message\": \"Hello World\"}";

    @Autowired private MockMvc mockMvc;
    @MockBean private MockService service;

    @Test
    void execution() throws Exception {
        Mock model = Mock.builder()
                .uri(URI)
                .method(HTTP_METHOD.name())
                .status(HTTP_STATUS.value())
                .response(RESPONSE)
                .build();

        when(service.findByUriAndMethod(any(), any())).thenReturn(Optional.of(model));

        mockMvc.perform(MockMvcRequestBuilders.request(HTTP_METHOD, MockController.BASE_PATH_TO_MOCK_API + URI))
                .andExpect(status().is(HTTP_STATUS.value()))
                .andExpect(content().string(RESPONSE));

        verify(service).findByUriAndMethod(URI, HTTP_METHOD.name());
    }

}