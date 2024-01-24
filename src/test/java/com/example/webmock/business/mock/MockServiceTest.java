package com.example.webmock.business.mock;

import com.example.webmock.ValidationAdvice;
import com.example.webmock.data.mock.MockRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MockServiceTest {

    private static final long ID = 1L;
    private static final String URI = "/uri";
    private static final String RESPONSE = "{}";
    private static final String METHOD = HttpMethod.GET.name();
    private static final HttpStatus HTTP_STATUS = HttpStatus.I_AM_A_TEAPOT;

    private MockService service;
    private MockRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(MockRepository.class);

        ProxyFactory factory = new ProxyFactory(new MockService(repository));
        factory.addAdvice(new ValidationAdvice());

        service = (MockService) factory.getProxy();
    }

    @Test
    void givenMock_whenFindAll_shouldReturnListWithMock() {
        List<Mock> mocks = List.of(new Mock());
        when(repository.findAll()).thenReturn(mocks);
        assertEquals(mocks, service.findAll());
        verify(repository).findAll();
    }

    @Test
    void givenMock_whenFindByUriAndMethod_thenReturnMock() {
        Optional<Mock> expected = Optional.of(new Mock());
        when(repository.findByUriAndMethod(any(), any())).thenReturn(expected);
        assertEquals(expected, service.findByUriAndMethod(URI, METHOD));
        verify(repository).findByUriAndMethod(URI, METHOD);
    }

    @Test
    void givenUriIsNull_whenFindByUriAndMethod_thenValidate() {
        assertThrows(IllegalArgumentException.class, () -> service.findByUriAndMethod(null, METHOD));
    }

    @Test
    void givenMethodIsNull_whenFindByUriAndMethod_thenValidate() {
        assertThrows(IllegalArgumentException.class, () -> service.findByUriAndMethod(URI, null));
    }

    @Test
    void givenMock_whenExistsByUriAndMethod_thenReturnTrue() {
        when(repository.existsByUriAndMethod(any(), any())).thenReturn(true);
        assertTrue(service.existsByUriAndMethod(URI, METHOD));
        verify(repository).existsByUriAndMethod(URI, METHOD);
    }

    @Test
    void givenUriIsNull_whenExistsByUriAndMethod_thenValidate() {
        assertThrows(IllegalArgumentException.class, () -> service.existsByUriAndMethod(null, METHOD));
    }

    @Test
    void givenId_whenDeleteById_thenDeleteById() {
        doNothing().when(repository).deleteById(any());
        service.deleteById(ID);
        verify(repository).deleteById(ID);
    }

    @Test
    void givenNullId_whenDeleteById_thenValidate() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteById(null));
    }

    @Test
    void givenCorrectlyFilledMock_whenSave_thenSave() {
        Mock modelToSave = buildMock();

        Mock modelSaved = new Mock();

        when(repository.save(any())).thenReturn(modelSaved);
        assertEquals(modelSaved, service.save(modelToSave));
        verify(repository).save(modelToSave);
    }

    @Test
    void givenNullMock_whenSave_thenValidate() {
        assertThrows(IllegalArgumentException.class, () -> service.save(null));
    }

    @ParameterizedTest
    @MethodSource("nullFieldsMocks")
    void givenMockWithNullFields_whenSave_thenValidate(Mock model) {
        assertThrows(ConstraintViolationException.class, () -> service.save(model));
    }

    @ParameterizedTest
    @MethodSource("blankFieldsMocks")
    void givenMockWithBlankFields_whenSave_thenValidate(Mock model) {
        assertThrows(ConstraintViolationException.class, () -> service.save(model));
    }

    private static Stream<Mock> nullFieldsMocks() {
        return Stream.of(
                buildMock().withStatus(null)
        );
    }

    private static Stream<Mock> blankFieldsMocks() {
        return Stream.of(
                buildMock().withUri(""),
                buildMock().withMethod("")
        );
    }

    private static Mock buildMock() {
        return Mock.builder()
                .uri(URI)
                .method(METHOD)
                .response(RESPONSE)
                .status(HTTP_STATUS.value())
                .build();
    }

}