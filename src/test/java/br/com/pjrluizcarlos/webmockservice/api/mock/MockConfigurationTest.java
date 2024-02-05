package br.com.pjrluizcarlos.webmockservice.api.mock;

import br.com.pjrluizcarlos.webmockservice.data.mock.MockDAO;
import br.com.pjrluizcarlos.webmockservice.data.mock.MockEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MockConfigurationTest {

    private static final long ID = 1L;
    private static final int STATUS = 200;
    private static final String METHOD = "GET";
    private static final String URI = "/test-1";
    private static final String RESPONSE = "{\"message\": \"Hello World!\"}";

    @Autowired private MockDAO dao;
    @Autowired private MockConfigurationController controller;

    @Test
    void givenMock_whenFindAll_thenReturnMocks() {
        dao.save(buildEntity());
        assertEquals(List.of(buildResponse()), controller.findAll());
    }

    @Test
    void givenNoMock_whenFindAll_thenReturnEmptyList() {
        assertEquals(List.of(), controller.findAll());
    }

    @Test
    void givenMock_whenSave_thenSaveMock() {
        assertEquals(buildResponse(), controller.save(buildRequest()));

        Optional<MockEntity> entity = dao.findById(ID);

        assertTrue(entity.isPresent());
        assertEquals(buildEntity(), entity.get());
    }

    @Test
    void givenAMockWithExistentUriAndMethod_whenSave_thenUpdateMock() {
        int status = 308;
        String response = "{\"test\":1}";

        MockRequest request = MockRequest.builder()
                .uri(URI)
                .method(METHOD)
                .status(status)
                .response(response)
                .build();

        MockResponse expectedResponse = MockResponse.builder()
                .id(ID)
                .uri(URI)
                .method(METHOD)
                .status(status)
                .response(response)
                .build();

        MockEntity expectedEntity = MockEntity.builder()
                .id(ID)
                .uri(URI)
                .method(METHOD)
                .status(status)
                .response(response)
                .build();

        dao.save(buildEntity());

        assertEquals(expectedResponse, controller.save(request));

        Optional<MockEntity> entity = dao.findById(ID);

        assertTrue(entity.isPresent());
        assertEquals(expectedEntity, entity.get());
    }

    @Test
    void givenExistsMockWithId_whenCallToDeleteById_shouldDeleteMock() {
        dao.save(buildEntity());
        controller.deleteById(ID);
        assertTrue(dao.findById(ID).isEmpty());
    }

    private MockRequest buildRequest() {
        return MockRequest.builder()
                .method(METHOD)
                .uri(URI)
                .status(STATUS)
                .response(RESPONSE)
                .build();
    }

    private MockEntity buildEntity() {
        return MockEntity.builder()
                .id(ID)
                .uri(URI)
                .method(METHOD)
                .response(RESPONSE)
                .status(STATUS)
                .build();
    }

    private MockResponse buildResponse() {
        return MockResponse.builder()
                .id(ID)
                .uri(URI)
                .method(METHOD)
                .response(RESPONSE)
                .status(STATUS)
                .build();
    }

}
