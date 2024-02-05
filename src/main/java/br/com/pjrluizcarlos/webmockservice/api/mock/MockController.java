package br.com.pjrluizcarlos.webmockservice.api.mock;

import br.com.pjrluizcarlos.webmockservice.business.mock.Mock;
import br.com.pjrluizcarlos.webmockservice.business.mock.MockService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.String.format;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(MockController.BASE_PATH_TO_MOCK_API)
public class MockController {

    protected static final String BASE_PATH_TO_MOCK_API = "/mocks";

    private final MockService service;

    @RequestMapping("/**")
    public ResponseEntity<String> execute(HttpServletRequest request) {
        String uri = removeBasePath(request.getRequestURI());
        String method = request.getMethod();
        
        log.info("Mock execution requested. [{}]: [{}].", method, uri);
        
        return service.findByUriAndMethod(uri, method)
                .map(mockFound -> mapSuccess(mockFound, uri, method))
                .orElseGet(() -> buildError(method, uri));
    }

    private ResponseEntity<String> buildError(String method, String uri) {
        String message = format("Mock execution not configured. [%s]: [%s].", method, uri);

        log.info(message);

        return ResponseEntity
                .status(HttpStatus.NOT_IMPLEMENTED)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                .body(message);
    }

    private ResponseEntity<String> mapSuccess(Mock mock, String uri, String method) {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (mock.getResponse() != null) {
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        }

        log.info("Mock execution configured. [{}]: [{}]. Status: [{}], Body: [{}].",
                method, uri, mock.getStatus(), mock.getResponse());

        return ResponseEntity
                .status(mock.getStatus())
                .headers(httpHeaders)
                .body(mock.getResponse());
    }

    private String removeBasePath(String value) {
        return value.replace(BASE_PATH_TO_MOCK_API, "");
    }

}
