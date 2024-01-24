package com.example.webmock.api.mock;

import com.example.webmock.business.mock.Mock;
import com.example.webmock.business.mock.MockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mocks-configuration")
public class MockConfigurationController {

    private final MockService service;
    private final MockApiMapper mapper;

    @GetMapping
    public List<MockResponse> findAll() {
        return service.findAll().stream().map(mapper::mapToResponse).toList();
    }

    @PostMapping
    public MockResponse save(@RequestBody @Valid MockRequest requestBody) {
        Mock mockToSave = mapper.mapToModel(requestBody);
        Mock mockSaved = service.save(mockToSave);

        return mapper.mapToResponse(mockSaved);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

}
