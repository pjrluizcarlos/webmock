package com.example.webmock.data.mock;

import com.example.webmock.business.mock.Mock;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MockRepository {

    private final MockDAO dao;
    private final MockRepositoryMapper mapper;

    public List<Mock> findAll() {
        return dao.findAll().stream().map(mapper::map).toList();
    }

    public Optional<Mock> findByUriAndMethod(@NonNull String uri, @NonNull String method) {
        return dao.findByUriAndMethod(uri, method).map(mapper::map);
    }

    public boolean existsByUriAndMethod(@NonNull String uri, @NonNull String method) {
        return dao.existsByUriAndMethod(uri, method);
    }

    @Transactional
    public Mock save(@NonNull Mock model) {
        dao.deleteAllByUriAndMethod(model.getUri(), model.getMethod());

        MockEntity entityToSave = mapper.map(model);
        MockEntity entitySaved = dao.save(entityToSave);

        return mapper.map(entitySaved);
    }

    public void deleteAllByUriAndMethod(@NonNull String uri, @NonNull String method) {
        dao.deleteAllByUriAndMethod(uri, method);
    }

    public void deleteById(@NonNull Long id) {
        dao.deleteById(id);
    }

}
