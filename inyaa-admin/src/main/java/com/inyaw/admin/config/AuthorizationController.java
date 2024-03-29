/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.inyaw.admin.config;

import com.inyaw.admin.base.BaseResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;
import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

/**
 * @author Joe Grandja
 * @since 0.0.1
 */
@RestController
@RequiredArgsConstructor
public class AuthorizationController {

    private final WebClient webClient;
    private final RedisService redisService;
    @Value("${messages.base-uri}")
    private String messagesBaseUri;

    @GetMapping(value = "/authorize", params = "grant_type=authorization_code")
    public BaseResult<String[]> authorizationCodeGrant(@RegisteredOAuth2AuthorizedClient("messaging-client-authorization-code") OAuth2AuthorizedClient authorizedClient) {
        String[] messages = webClient
                .get()
                .uri(messagesBaseUri)
                .attributes(oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .bodyToMono(String[].class)
                .block();
        return BaseResult.success(messages);
    }

    // '/authorized' is the registered 'redirect_uri' for authorization_code
    @GetMapping(value = "/authorized", params = OAuth2ParameterNames.ERROR)
    public BaseResult<OAuth2Error> authorizationFailed(HttpServletRequest request) {
        String errorCode = request.getParameter(OAuth2ParameterNames.ERROR);
        if (StringUtils.hasText(errorCode)) {
            return BaseResult.success(new OAuth2Error(
                    errorCode,
                    request.getParameter(OAuth2ParameterNames.ERROR_DESCRIPTION),
                    request.getParameter(OAuth2ParameterNames.ERROR_URI)));
        }
        return BaseResult.success();
    }

    @GetMapping(value = "/authorize", params = "grant_type=client_credentials")
    public BaseResult<String[]> clientCredentialsGrant() throws IOException {

        String[] messages = webClient
                .get()
                .uri(messagesBaseUri)
                .attributes(clientRegistrationId("messaging-client-client-credentials"))
                .retrieve()
                .bodyToMono(String[].class)
                .block();
        return BaseResult.success(messages);
    }
}
