package core.auth.Config;

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
public class JwtConfig {

    @Resource
    RedisConnectionFactory redisConnectionFactory;

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


    //@Bean
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices tokenStoreService =  new DefaultTokenServices();
        tokenStoreService.setTokenStore(jwtTokenStore());
        return tokenStoreService;
    }
}
