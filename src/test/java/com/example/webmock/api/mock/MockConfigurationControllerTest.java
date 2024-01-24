package com.example.webmock.api.mock;

import com.example.webmock.ValidationAdvice;
import com.example.webmock.business.mock.Mock;
import com.example.webmock.business.mock.MockService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class MockConfigurationControllerTest {

    private static final long ID = 1L;
    private static final String URI = "uri";
    private static final String RESPONSE = "response";
    private static final HttpMethod METHOD = HttpMethod.PATCH;
    private static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;

    private MockConfigurationController controller;
    private MockService service;
    private MockApiMapper mapper;

    @BeforeEach
    void setUp() {
        service = mock(MockService.class);
        mapper = mock(MockApiMapper.class);

        ProxyFactory factory = new ProxyFactory(new MockConfigurationController(service, mapper));
        factory.addAdvice(new ValidationAdvice());

        controller = (MockConfigurationController) factory.getProxy();
    }

    @Test
    void givenThereIsMock_whenCallToFindAll_shouldReturnAListWithMock() {
        MockResponse expected = new MockResponse();
        Mock model = new Mock();

        when(service.findAll()).thenReturn(List.of(model));
        when(mapper.mapToResponse(any())).thenReturn(expected);

        List<MockResponse> actual = controller.findAll();

        assertEquals(List.of(expected), actual);
        verify(service).findAll();
        verify(mapper).mapToResponse(model);
    }

    @Test
    void givenThereIsNoMock_whenCallToFindAll_shouldReturnAnEmptyList() {
        when(service.findAll()).thenReturn(emptyList());

        List<MockResponse> actual = controller.findAll();

        assertEquals(emptyList(), actual);
        verify(service).findAll();
        verifyNoInteractions(mapper);
    }

    @Test
    void givenAMockToSave_whenCallToSave_shouldSave() {
        MockRequest request = build();
        Mock modelToSave = new Mock();
        Mock modelSaved = new Mock();
        MockResponse expected = new MockResponse();

        when(mapper.mapToModel(any())).thenReturn(modelToSave);
        when(service.save(any())).thenReturn(modelSaved);
        when(mapper.mapToResponse(any())).thenReturn(expected);

        MockResponse actual = controller.save(request);

        assertEquals(expected, actual);
        verify(mapper).mapToModel(request);
        verify(service).save(modelToSave);
        verify(mapper).mapToResponse(modelSaved);
    }

    @ParameterizedTest
    @MethodSource("notBlankFieldsMocks")
    void givenAMockWithBlankField_whenCallToSave_shouldValidate(MockRequest request) {
        assertThrows(ConstraintViolationException.class, () -> controller.save(request));
        verifyNoInteractions(service, mapper);
    }

    @ParameterizedTest
    @MethodSource("notNullFieldsMocks")
    void givenAMockWithNullField_whenCallToSave_shouldValidate(MockRequest request) {
        assertThrows(Exception.class, () -> controller.save(request));
        verifyNoInteractions(service, mapper);
    }

    @Test
    void givenAMockId_whenCallToDeleteById_shouldDeleteMockById() {
        doNothing().when(service).deleteById(any());
        controller.deleteById(ID);
        verify(service).deleteById(ID);
    }

    private static Stream<MockRequest> notBlankFieldsMocks() {
        return Stream.of(
                build().withMethod(""),
                build().withUri("")
        );
    }

    private static Stream<MockRequest> notNullFieldsMocks() {
        return Stream.of(
                build().withMethod(null),
                build().withUri(null),
                build().withStatus(null)
        );
    }

    private static MockRequest build() {
        return MockRequest.builder()
                .uri(URI)
                .method(METHOD.name())
                .status(HTTP_STATUS.value())
                .response(RESPONSE)
                .build();
    }

}