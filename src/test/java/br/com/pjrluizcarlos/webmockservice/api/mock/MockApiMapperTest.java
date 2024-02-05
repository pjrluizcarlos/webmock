package br.com.pjrluizcarlos.webmockservice.api.mock;

import br.com.pjrluizcarlos.webmockservice.business.mock.Mock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MockApiMapperTest {

    private static final long ID = 1L;
    private static final String URI = "uri";
    private static final String METHOD = "method";
    private static final String RESPONSE = "response";
    private static final HttpStatus STATUS = HttpStatus.OK;
    private static final HttpMethod PATCH = HttpMethod.PATCH;

    @InjectMocks private MockApiMapper mapper;

    @Test
    void givenNullRequest_whenCallToMap_shouldValidate() {
        assertThrows(IllegalArgumentException.class, () -> mapper.mapToModel(null));
    }

    @Test
    void givenEmptyRequest_whenCallToMap_shouldMapToEmptyModel() {
        assertEquals(new Mock(), mapper.mapToModel(new MockRequest()));
    }

    @Test
    void givenFilledRequest_whenCallToMap_shouldMapToFilledModel() {
        assertEquals(buildMock(null), mapper.mapToModel(buildMocKRequest()));
    }

    @Test
    void givenNullModel_whenCallToMap_shouldValidate() {
        assertThrows(IllegalArgumentException.class, () -> mapper.mapToResponse(null));
    }

    @Test
    void givenEmptyModel_whenCallToMap_shouldMapToEmptyResponse() {
        assertEquals(new MockResponse(), mapper.mapToResponse(new Mock()));
    }

    @Test
    void givenFilledModel_whenCallToMap_shouldMapToFilledResponse() {
        assertEquals(buildMockResponse(), mapper.mapToResponse(buildMock()));
    }

    private MockResponse buildMockResponse() {
        return MockResponse.builder()
                .id(ID)
                .uri(URI)
                .method(PATCH.name())
                .response(RESPONSE)
                .status(STATUS.value())
                .build();
    }

    private static MockRequest buildMocKRequest() {
        return MockRequest.builder()
                .uri(URI)
                .method(METHOD)
                .response(RESPONSE)
                .status(STATUS.value())
                .method(PATCH.name())
                .build();
    }

    private static Mock buildMock() {
        return buildMock(ID);
    }

    private static Mock buildMock(Long id) {
        return Mock.builder()
                .id(id)
                .uri(URI)
                .method(PATCH.name())
                .response(RESPONSE)
                .status(STATUS.value())
                .build();
    }

}