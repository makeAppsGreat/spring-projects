package kr.makeappsgreat.demospringsecurityform.config;

import kr.makeappsgreat.demospringsecurityform.account.AccountService;
import kr.makeappsgreat.demospringsecurityform.common.LoggingFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Autowired
    AccountService accountService;

    // 1. Using AccessDecisionManager -> ~.accessDecisionManager( ... )
    /* public AccessDecisionManager accessDecisionManager() {
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(expressionHandler());

        List<AccessDecisionVoter<? extends Object>> voters = Arrays.asList(webExpressionVoter);
        return new AffirmativeBased(voters);
    } */

    // 2. Using SecurityExpressionHandler -> ~.expressionHandler( ... )
    public SecurityExpressionHandler expressionHandler() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);

        return handler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

        http.addFilterBefore(new LoggingFilter(), WebAsyncManagerIntegrationFilter.class);

        http.authorizeRequests()
                .mvcMatchers("/", "/info", "/account/**", "/signup", "/favicon.ico").permitAll()
                .mvcMatchers("/user").hasRole("USER")
                .mvcMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
                // .accessDecisionManager(accessDecisionManager())
                .expressionHandler(expressionHandler());

        http.formLogin()
                .loginPage("/login")
                .permitAll();

        http.rememberMe()
                .userDetailsService(accountService)
                .key("remember-me-sample");

        http.httpBasic();

        http.exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    String username = principal.getUsername();

                    log.warn("{} is denied to access \"{}\".", username, request.getRequestURI());
                    response.sendRedirect("/access-denied");
                });


        return http.build();
    }

    /**
     * 1. Using pattern
     *
     * You are asking Spring Security to ignore Mvc [pattern='/favicon.ico'].
     * This is not recommended -- please use permitAll via HttpSecurity#authorizeHttpRequests instead.
     *
     * Will not secure Mvc [pattern='/favicon.ico']
     *
     *
     * 2. Using StaticResourceRequest
     *
     * You are asking Spring Security to ignore
     * org.springframework.boot.autoconfigure.security.servlet.StaticResourceRequest$StaticResourceRequestMatcher@7352418c.
     * This is not recommended -- please use permitAll via HttpSecurity#authorizeHttpRequests instead.
     *
     * Will not secure org.springframework.boot.autoconfigure.security.servlet.StaticResourceRequest$StaticResourceRequestMatcher@7352418c
     */
    /* @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // return (web) -> web.ignoring().mvcMatchers("/favicon.ico");
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    } */


    /* @Bean
    public UserDetailsService users() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("youn")
                .password("simple")
                .roles("USER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("simple")
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    } */
}