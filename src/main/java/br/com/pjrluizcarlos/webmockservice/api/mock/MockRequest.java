package br.com.pjrluizcarlos.webmockservice.api.mock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MockRequest {

    private String response;

    @NotBlank private String uri;
    @NotBlank private String method;
    @NotNull private Integer status;

}
