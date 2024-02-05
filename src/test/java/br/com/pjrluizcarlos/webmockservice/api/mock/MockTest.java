package br.com.pjrluizcarlos.webmockservice.api.mock;

import br.com.pjrluizcarlos.webmockservice.data.mock.MockDAO;
import br.com.pjrluizcarlos.webmockservice.data.mock.MockEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MockTest {

    private static final long ID = 1L;
    private static final int STATUS = 200;
    private static final String METHOD = "GET";
    private static final String URI = "/test-1";
    private static final String RESPONSE = "{\"message\": \"Hello World!\"}";

    @Autowired private MockController controller;
    @Autowired private MockDAO dao;
    @MockBean private HttpServletRequest request;

    @Test
    void givenExistsMockWithMethodAndUri_whenCallMockExecution_thenExecuteMock() {
        dao.save(buildEntity());

        when(request.getRequestURI()).thenReturn(URI);
        when(request.getMethod()).thenReturn(METHOD);

        ResponseEntity<String> actual = controller.execute(request);

        assertEquals(STATUS, actual.getStatusCode().value());
        assertEquals(RESPONSE, actual.getBody());

        List<String> contentTypes = actual.getHeaders().get(HttpHeaders.CONTENT_TYPE);

        assertNotNull(contentTypes);
        assertEquals(1, contentTypes.size());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, contentTypes.get(0));
    }

    @Test
    void givenNotExistsMockWithMethodAndUri_whenCallMockExecution_thenNotExecuteMock() {
        when(request.getRequestURI()).thenReturn(URI);
        when(request.getMethod()).thenReturn(METHOD);

        ResponseEntity<String> actual = controller.execute(request);

        assertEquals(HttpStatus.NOT_IMPLEMENTED, actual.getStatusCode());
        assertEquals(format("Mock execution not configured. [%s]: [%s].", METHOD, URI), actual.getBody());

        List<String> contentTypes = actual.getHeaders().get(HttpHeaders.CONTENT_TYPE);

        assertNotNull(contentTypes);
        assertEquals(1, contentTypes.size());
        assertEquals(MediaType.TEXT_PLAIN_VALUE, contentTypes.get(0));
    }

    private static MockEntity buildEntity() {
        return MockEntity.builder()
                .id(ID)
                .uri(URI)
                .method(METHOD)
                .response(RESPONSE)
                .status(STATUS)
                .build();
    }

}
