package br.com.pjrluizcarlos.webmockservice.data.mock;

import br.com.pjrluizcarlos.webmockservice.business.mock.Mock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MockRepositoryTest {

    private static final long ID = 1L;
    private static final String URI = "uri";
    private static final HttpMethod METHOD = HttpMethod.GET;

    @InjectMocks private MockRepository repository;
    @org.mockito.Mock private MockRepositoryMapper mapper;
    @org.mockito.Mock private MockDAO dao;

    @Test
    void givenMock_whenFindAll_shouldReturnListWithMock() {
        Mock model = new Mock();
        MockEntity entity = new MockEntity();

        when(dao.findAll()).thenReturn(List.of(entity));
        when(mapper.map(any(MockEntity.class))).thenReturn(model);

        List<Mock> actual = repository.findAll();

        assertEquals(List.of(model), actual);
        verify(dao).findAll();
        verify(mapper).map(entity);
    }

    @Test
    void givenNoMock_whenFindAll_shouldReturnEmptyList() {
        when(dao.findAll()).thenReturn(emptyList());

        List<Mock> actual = repository.findAll();

        assertEquals(emptyList(), actual);
        verify(dao).findAll();
        verifyNoInteractions(mapper);
    }

    @Test
    void givenMock_whenFindByUriAndMethod_shouldReturnMock() {
        Mock model = new Mock();
        MockEntity entity = new MockEntity();

        when(dao.findByUriAndMethod(any(), any())).thenReturn(Optional.of(entity));
        when(mapper.map(any(MockEntity.class))).thenReturn(model);

        Optional<Mock> actual = repository.findByUriAndMethod(URI, METHOD.name());

        assertEquals(Optional.of(model), actual);
        verify(dao).findByUriAndMethod(URI, METHOD.name());
        verify(mapper).map(entity);
    }

    @Test
    void givenNoMock_whenFindByUriAndMethod_shouldReturnEmpty() {
        when(dao.findByUriAndMethod(any(), any())).thenReturn(Optional.empty());

        Optional<Mock> actual = repository.findByUriAndMethod(URI, METHOD.name());

        assertEquals(Optional.empty(), actual);
        verify(dao).findByUriAndMethod(URI, METHOD.name());
        verifyNoInteractions(mapper);
    }

    @Test
    void givenMock_whenExistsByUriAndMethod_shouldReturnTrue() {
        when(dao.existsByUriAndMethod(any(), any())).thenReturn(true);

        boolean actual = repository.existsByUriAndMethod(URI, METHOD.name());

        assertTrue(actual);
        verify(dao).existsByUriAndMethod(URI, METHOD.name());
        verifyNoInteractions(mapper);
    }

    @Test
    void givenNoMock_whenExistsByUriAndMethod_shouldReturnFalse() {
        when(dao.existsByUriAndMethod(any(), any())).thenReturn(false);

        boolean actual = repository.existsByUriAndMethod(URI, METHOD.name());

        assertFalse(actual);
        verify(dao).existsByUriAndMethod(URI, METHOD.name());
        verifyNoInteractions(mapper);
    }

    @Test
    void givenMock_whenSave_shouldReturnMock() {
        Mock input = new Mock();
        Mock output = new Mock();
        MockEntity entityToSave = new MockEntity();
        MockEntity entitySaved = new MockEntity();

        when(mapper.map(any(Mock.class))).thenReturn(entityToSave);
        when(dao.save(any())).thenReturn(entitySaved);
        when(mapper.map(any(MockEntity.class))).thenReturn(output);

        Mock actual = repository.save(input);

        assertEquals(output, actual);
        verify(mapper).map(input);
        verify(dao).save(entityToSave);
        verify(mapper).map(entitySaved);
    }

    @Test
    void givenMock_whenDeleteAllByUriAndMethod_shouldCallDAO() {
        doNothing().when(dao).deleteAllByUriAndMethod(any(), any());
        repository.deleteAllByUriAndMethod(URI, METHOD.name());
        verify(dao).deleteAllByUriAndMethod(URI, METHOD.name());
        verifyNoInteractions(mapper);
    }

    @Test
    void givenMock_whenDeleteById_shouldCallDAO() {
        doNothing().when(dao).deleteById(any());
        repository.deleteById(ID);
        verify(dao).deleteById(ID);
        verifyNoInteractions(mapper);
    }

}