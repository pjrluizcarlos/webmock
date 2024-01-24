package com.example.webmock.api.mock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MockRequest {

    private String response; // TODO: @NullOrNotBlank

    @NotBlank private String uri;
    @NotBlank private String method;
    @NotNull private Integer status;

}
