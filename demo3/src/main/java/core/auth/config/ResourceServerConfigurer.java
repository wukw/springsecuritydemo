package core.auth.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableResourceServer
@Configuration
public class ResourceServerConfigurer  extends ResourceServerConfigurerAdapter {


    @Autowired
    protected AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;


    private static final String DEMO_RESOURCE_ID = "order";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/user/loginpage")
                .loginProcessingUrl("/user/login")
                .successHandler(imoocAuthenticationSuccessHandler);



      http
                .requestMatchers().anyRequest()
                .and()
                .anonymous()
                .and()
                .authorizeRequests()
                    .antMatchers("/user/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                .csrf().disable();


    }

}
