package br.com.pjrluizcarlos.webmockservice.business.mock;

import br.com.pjrluizcarlos.webmockservice.data.mock.MockRepository;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
