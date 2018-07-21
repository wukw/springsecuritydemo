package com.resourceserver.Token;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;

@Configuration
public class TokenConfig {

    @Resource
    RedisConnectionFactory redisConnectionFactory;

    //@Autowired
    //ClientDetailsService clientDetailsService;

    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("wukw");
        return converter;
    }

    //@Bean
    public TokenStore inRedisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }


    @Bean
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices tokenStoreService =  new DefaultTokenServices();
        tokenStoreService.setAccessTokenValiditySeconds(1000*60*60*30);
        tokenStoreService.setRefreshTokenValiditySeconds(1000*60*60*30);
        tokenStoreService.setSupportRefreshToken(true);
        tokenStoreService.setReuseRefreshToken(true);
        //tokenStoreService.setClientDetailsService(clientDetailsService);
        tokenStoreService.setTokenEnhancer(jwtAccessTokenConverter());
        tokenStoreService.setTokenStore(jwtTokenStore());
        return tokenStoreService;
    }
}
