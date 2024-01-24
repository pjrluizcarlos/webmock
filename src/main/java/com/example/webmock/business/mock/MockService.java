package com.example.webmock.business.mock;

import com.example.webmock.data.mock.MockRepository;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MockService {

    private final MockRepository repository;

    public List<Mock> findAll() {
        return repository.findAll();
    }

    public Mock save(@NonNull @Valid Mock model) {
        return repository.save(model);
    }

    public Optional<Mock> findByUriAndMethod(@NonNull String uri, @NonNull String method) {
        return repository.findByUriAndMethod(uri, method);
    }

    public boolean existsByUriAndMethod(@NonNull String uri, @NonNull String method) {
        return repository.existsByUriAndMethod(uri, method);
    }

    public void deleteById(@NonNull Long id) {
        repository.deleteById(id);
    }

}
