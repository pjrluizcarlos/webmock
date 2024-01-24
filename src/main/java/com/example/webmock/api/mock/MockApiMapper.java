package com.example.webmock.api.mock;

import com.example.webmock.business.mock.Mock;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class MockApiMapper {

    public Mock mapToModel(@NonNull MockRequest request) {
        return Mock.builder()
                .uri(request.getUri())
                .method(request.getMethod())
                .response(request.getResponse())
                .status(request.getStatus())
                .build();
    }

    public MockResponse mapToResponse(@NonNull Mock model) {
        return MockResponse.builder()
                .id(model.getId())
                .uri(model.getUri())
                .method(model.getMethod())
                .response(model.getResponse())
                .status(model.getStatus())
                .build();
    }

}
