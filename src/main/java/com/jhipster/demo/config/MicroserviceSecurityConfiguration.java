package com.jhipster.demo.config;

import com.jhipster.demo.config.oauth2.OAuth2JwtAccessTokenConverter;
import com.jhipster.demo.config.oauth2.OAuth2Properties;
import com.jhipster.demo.security.oauth2.OAuth2SignatureVerifierClient;
import com.jhipster.demo.security.AuthoritiesConstants;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.client.RestTemplate;

// @Secured 支持单一角色或者多个角色之间的任何一个角色,不支持spring EL表达式
// @PreAuthorize 注解适合进入方法前的权限验证， @PreAuthorize可以将登录用户的roles/permissions参数传到方法中。
// 例子:
// @PreAuthorize("hasRole('ADMIN')")   拥有ADMIN角色权限才能访问
// @PreAuthorize("hasRole('ADMIN') AND hasRole('DBA')")  拥有ADMIN角色和DBA角色权限才能访问
// @PreAuthorize("hasAnyRole('ADMIN','DBA')") 拥有ADMIN或者DBA角色均可访问
// @PostAuthorize   注解使用并不多，在方法执行后再进行权限验证。
// @PreAuthorize / @PostAuthorize 注解更适合方法级的安全,也支持Spring 表达式语言，提供了基于表达式的访问控制。
@Configuration
@EnableResourceServer                                                     // prePostEnabled 开启可用注解 PreAuthorize 和  PostAuthorize
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // Spring Security 开启全局方法安全
public class MicroserviceSecurityConfiguration extends ResourceServerConfigurerAdapter {
    private final OAuth2Properties oAuth2Properties;

    public MicroserviceSecurityConfiguration(OAuth2Properties oAuth2Properties) {
        this.oAuth2Properties = oAuth2Properties;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
            .headers()
            .frameOptions()
            .disable()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .antMatchers("/api/profile-info").permitAll()
            .antMatchers("/api/demo/uaa/account").permitAll()
            .antMatchers("/api/demo/uaa/login").permitAll()
            .antMatchers("/api/demo/login").permitAll()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/swagger-resources/configuration/ui").permitAll();
    }

    @Bean
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(OAuth2SignatureVerifierClient signatureVerifierClient) {
        return new OAuth2JwtAccessTokenConverter(oAuth2Properties, signatureVerifierClient);
    }

    @Bean
	@Qualifier("loadBalancedRestTemplate")
    public RestTemplate loadBalancedRestTemplate(RestTemplateCustomizer customizer) {
        RestTemplate restTemplate = new RestTemplate();
        customizer.customize(restTemplate);
        return restTemplate;
    }

    @Bean
    @Qualifier("vanillaRestTemplate")
    public RestTemplate vanillaRestTemplate() {
        return new RestTemplate();
    }
}
