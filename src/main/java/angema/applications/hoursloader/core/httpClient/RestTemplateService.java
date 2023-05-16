package angema.applications.hoursloader.core.httpClient;

import angema.applications.hoursloader.core.globalResponse.GlobalResponse;
import angema.applications.hoursloader.core.utils.ObjectMapperUtil;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Service
public class RestTemplateService {

    @Value("${configs.auth.timezone}")
    private String URL_BASE;
    private RestTemplate restTemplate;

    @Autowired
    public RestTemplateService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(5000))
                .setReadTimeout(Duration.ofMillis(5000))
                .build();
    }

    public <T> T post(String path, Object body, Class<T> clazz) {
        return (T) getExchange(path, HttpMethod.POST, body, clazz);
    }

    public <T> Object postFromList(String path, Object body, TypeReference<T> listTypeReference) {
        return (T) getExchangeFromList(path, HttpMethod.POST, body, listTypeReference);
    }

    private <T> Object getExchange(String path, HttpMethod method, Object body, Class<T> clazz) {
        String url = getPathUrl(path);
        HttpEntity entity = getHeaders(body);

        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url, method, entity, new ParameterizedTypeReference<String>() {
            });
        } catch (RestClientException e) {
            throw new RestTemplateException(new GlobalResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
        return ObjectMapperUtil.stringToObject(response.getBody(), clazz);
    }

    private <T> Object getExchangeFromList(String path, HttpMethod method, Object body, TypeReference<T> listTypeReference) {
        String url = getPathUrl(path);
        HttpEntity entity = getHeaders(body);

        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url, method, entity, new ParameterizedTypeReference<String>() {
            });
        } catch (RestClientException e) {
            throw new RestTemplateException(new GlobalResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
        return ObjectMapperUtil.stringToObject(response.getBody(), listTypeReference);
    }

    private HttpEntity getHeaders(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(body, headers);
        return entity;
    }

    private String getPathUrl(String path) {
        if (path.startsWith("http")) {
            return path;
        }
        return URL_BASE + path;
    }

}
