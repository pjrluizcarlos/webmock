package com.example.webmock.business.mock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mock {

    private Long id;

    @NotBlank private String uri;
    @NotBlank private String method;
    @NotBlank private String response;
    @NotNull private Integer status;

}
