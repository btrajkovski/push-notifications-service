package com.btrajkovski.push.notifications.config;

import com.btrajkovski.push.notifications.repository.UserRepository;
import com.btrajkovski.push.notifications.service.auth.RepositoryUserDetailsService;
import com.btrajkovski.push.notifications.service.auth.SimpleSocialUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityContext extends WebSecurityConfigurerAdapter {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private UserRepository userRepository;

    @Value("${application.url}")
    private String redirectURL;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .addFilterAfter(csrfTokenResponseHeaderBindingFilter(), CsrfFilter.class)
            //Configures the logout function
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/")
            //Configures url based authorization
            .and()
                .authorizeRequests()
                //Anyone can access the urls
                .antMatchers(
                        "/",
                        "/auth/facebook",
                        "/auth/google",
                        "/auth/twitter",
                        "/auth/logout",
                        "/signup/**",
                        "/signin",
                        "/user/register/**",
                        "/api/devices/**"
                ).permitAll()
                //The rest of the our application is protected.
                .antMatchers("/**").hasRole("USER")
            //Adds the SocialAuthenticationFilter to Spring Security's filter chain.
            .and()
                .csrf().disable()
                .apply(getSpringSocialConfigurer());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SocialUserDetailsService socialUserDetailsService() {
        return new SimpleSocialUserDetailsService(userDetailsService());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new RepositoryUserDetailsService(userRepository);
    }

//    @Bean
//    public CsrfTokenResponseHeaderBindingFilter csrfTokenResponseHeaderBindingFilter() {
//              return new CsrfTokenResponseHeaderBindingFilter();
//    }

    private SpringSocialConfigurer getSpringSocialConfigurer() {
        SpringSocialConfigurer config = new SpringSocialConfigurer();
        config.alwaysUsePostLoginUrl(true);
        config.postLoginUrl(redirectURL);

        return config;
    }
}
