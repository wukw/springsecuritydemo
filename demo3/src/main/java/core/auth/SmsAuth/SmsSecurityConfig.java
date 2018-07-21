package core.auth.SmsAuth;

import core.auth.Handler.FailHandler;
import core.auth.Handler.SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SmsSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private SuccessHandler successHandler;

    @Autowired
    private FailHandler failHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        SmsFilter smsFilter =new SmsFilter();
        smsFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsFilter.setAuthenticationSuccessHandler(successHandler);
        smsFilter.setAuthenticationFailureHandler(failHandler);

        String key = UUID.randomUUID().toString();
        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);
        http.authenticationProvider(smsCodeAuthenticationProvider).addFilterAfter(smsFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
