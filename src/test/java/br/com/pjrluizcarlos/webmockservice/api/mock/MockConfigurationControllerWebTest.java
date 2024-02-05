package br.com.pjrluizcarlos.webmockservice.api.mock;

import br.com.pjrluizcarlos.webmockservice.business.mock.Mock;
import br.com.pjrluizcarlos.webmockservice.business.mock.MockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MockConfigurationController.class)
class MockConfigurationControllerWebTest {

    private static final Long ID = 1L;
    private static final String URI = "/uri";
    private static final String BODY = "{\"test\":1}";
    private static final HttpMethod METHOD = HttpMethod.GET;
    private static final String BASE_PATH = "/mocks-configuration";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final HttpStatus HTTP_STATUS = HttpStatus.I_AM_A_TEAPOT;

    @Autowired private MockMvc mockMvc;
    @MockBean private MockService service;
    @MockBean private MockApiMapper mapper;

    @Test
    void findAll() throws Exception {
        Mock model = Mock.builder().build();
        MockResponse response = MockResponse.builder().build();

        when(service.findAll()).thenReturn(List.of(model));
        when(mapper.mapToResponse(any())).thenReturn(response);

        mockMvc.perform(get(BASE_PATH))
                .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(List.of(response))))
                .andExpect(status().isOk());

        verify(service).findAll();
        verify(mapper).mapToResponse(model);
    }

    @Test
    void save() throws Exception {
        MockRequest request = buildMockRequest();
        Mock modelToSave = Mock.builder().build();
        Mock modelSaved = Mock.builder().build();
        MockResponse response = MockResponse.builder().build();

        when(mapper.mapToModel(any())).thenReturn(modelToSave);
        when(service.save(any())).thenReturn(modelSaved);
        when(mapper.mapToResponse(any())).thenReturn(response);

        MockHttpServletRequestBuilder requestBuilder = post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(request));

        mockMvc.perform(requestBuilder)
                .andExpect(content().json(OBJECT_MAPPER.writeValueAsString(response)))
                .andExpect(status().isOk());

        verify(mapper).mapToModel(request);
        verify(service).save(modelToSave);
        verify(mapper).mapToResponse(modelSaved);
    }

    @Test
    void deleteById() throws Exception {
        doNothing().when(service).deleteById(any());

        mockMvc.perform(delete(BASE_PATH + "/" + ID))
                .andExpect(status().isNoContent());

        verify(service).deleteById(ID);
    }

    private MockRequest buildMockRequest() {
        return MockRequest.builder()
                .uri(URI)
                .method(METHOD.name())
                .response(BODY)
                .status(HTTP_STATUS.value())
                .build();
    }

}