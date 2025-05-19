package com.example.assignment1_api.response.keycloak;
import com.example.assignment1_api.dto.identity.ClientTokenExchangeParam;
import com.example.assignment1_api.dto.identity.TokenExchangeParam;
import com.example.assignment1_api.dto.identity.TokenExchangeResponse;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "identity-client", url = "${idp.url}")
public interface IdentityClient {
    @PostMapping(
            value = "/realms/Assignment/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenExchangeResponse exchangeToken(@QueryMap TokenExchangeParam param);

    @PostMapping(
            value = "/realms/Assignment/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenExchangeResponse exchangeTokenClient(@QueryMap ClientTokenExchangeParam param);
}
