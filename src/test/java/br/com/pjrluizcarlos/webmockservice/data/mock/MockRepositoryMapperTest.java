package br.com.pjrluizcarlos.webmockservice.data.mock;

import br.com.pjrluizcarlos.webmockservice.business.mock.Mock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class MockRepositoryMapperTest {

    private static final long ID = 1L;
    private static final String URI = "uri";
    private static final String RESPONSE = "response";
    private static final HttpStatus STATUS = HttpStatus.OK;
    private static final HttpMethod METHOD = HttpMethod.PATCH;

    @InjectMocks
    private MockRepositoryMapper mapper;

    @Test
    void givenNullModel_whenCallToMap_shouldValidate() {
        assertThrows(IllegalArgumentException.class, () -> mapper.map((Mock) null));
    }

    @Test
    void givenEmptyModel_whenCallToMap_shouldMapToEmptyEntity() {
        assertEquals(new MockEntity(), mapper.map(new Mock()));
    }

    @Test
    void givenFilledModel_whenCallToMap_shouldMapToFilledEntity() {
        assertEquals(buildMockEntity(), mapper.map(buildMock()));
    }

    @Test
    void givenNullEntity_whenCallToMap_shouldValidate() {
        assertThrows(IllegalArgumentException.class, () -> mapper.map((MockEntity) null));
    }

    @Test
    void givenEmptyEntity_whenCallToMap_shouldMapToEmptyModel() {
        assertEquals(new Mock(), mapper.map(new MockEntity()));
    }

    @Test
    void givenFilledEntity_whenCallToMap_shouldMapToFilledModel() {
        assertEquals(buildMock(), mapper.map(buildMockEntity()));
    }

    private Mock buildMock() {
        return Mock.builder()
                .id(ID)
                .uri(URI)
                .method(METHOD.name())
                .response(RESPONSE)
                .status(STATUS.value())
                .build();
    }

    private MockEntity buildMockEntity() {
        return MockEntity.builder()
                .id(ID)
                .uri(URI)
                .method(METHOD.name())
                .response(RESPONSE)
                .status(STATUS.value())
                .build();
    }

}