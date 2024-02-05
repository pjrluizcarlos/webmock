package br.com.pjrluizcarlos.webmockservice.api.mock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MockResponse {

    private Long id;
    private String uri;
    private String method;
    private Integer status;
    private String response;

}
