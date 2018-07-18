package core.auth.config;

import core.auth.service.AuthorizationServerTokenServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableAuthorizationServer
public class AouthServerConfigurer extends AuthorizationServerConfigurerAdapter {

    private static final String DEMO_RESOURCE_ID = "order";
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    AuthorizationServerTokenServicesImpl authorizationServerTokenServices;
    @Autowired
    AuthenticationManager authenticationManager;
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client_1")
                    .authorizedGrantTypes("client_credentials", "refresh_token")
                    .scopes("select")
                    .secret(bCryptPasswordEncoder.encode("123456").toString())
                .and()
                .withClient("client_2")
                    .authorizedGrantTypes("password", "refresh_token","client_credentials")
                    .scopes("select")
                    .secret(bCryptPasswordEncoder.encode("123456").toString());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        //允许表单认证
        oauthServer.allowFormAuthenticationForClients();
    }
    @Bean
    protected UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user_1").password(bCryptPasswordEncoder.encode("123456")).authorities("USER").build());
        manager.createUser(User.withUsername("user_2").password(bCryptPasswordEncoder.encode("123456")).authorities("USER").build());
        return manager;
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenServices(authorizationServerTokenServices).tokenStore(inMemoryTokenStore()).authenticationManager(authenticationManager);
    }

    @Bean
    public TokenStore inMemoryTokenStore(){
        return new InMemoryTokenStore();
    }

    @Bean
    public DefaultTokenServices myTokenStore(){
        DefaultTokenServices tokenStoreService =  new DefaultTokenServices();
        tokenStoreService.setTokenStore(inMemoryTokenStore());
        return tokenStoreService;
    }
}
