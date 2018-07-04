package core.auth.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Configuration
public class AuthorizationServerTokenServicesImpl implements AuthorizationServerTokenServices {
    @Resource
    DefaultTokenServices defaultTokenServices;


    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        System.out.println("创建token");
        OAuth2AccessToken oAuth2AccessToken =  defaultTokenServices.createAccessToken(authentication);
        return oAuth2AccessToken;
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(String refreshToken, TokenRequest tokenRequest) throws AuthenticationException {
        System.out.println("刷新token");
        return defaultTokenServices.refreshAccessToken(refreshToken,tokenRequest);
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        System.out.println("获取token");
        return defaultTokenServices.getAccessToken(authentication);
    }
}
