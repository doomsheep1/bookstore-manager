package com.bookstore.bookstore_manager.config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/*
 * config setup for combining an oauth2 authorization server with resource server
 */
@Configuration
@EnableWebSecurity
public class OAuth2AuthorizationServerSecurityConfig {

    @Bean 
	@Order(1)
	public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
			throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.csrf(AbstractHttpConfigurer::disable)
            .getConfigurer(OAuth2AuthorizationServerConfigurer.class)
            .registeredClientRepository(registeredClientRepository())
            .authorizationServerSettings(authorizationServerSettings());
            http.oauth2ResourceServer((resourceServer) -> resourceServer
				.jwt(Customizer.withDefaults()));
		return http.build();
	}

    @Bean 
	@Order(2)
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
			throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                                .requestMatchers(HttpMethod.POST, "/find-book/**").hasAuthority("SCOPE_book:read")
                                .requestMatchers(HttpMethod.POST, "/add-book/**").hasAuthority("SCOPE_book:create")
                                .requestMatchers(HttpMethod.PUT,"/update-book/**").hasAuthority("SCOPE_book:update")
                                .requestMatchers(HttpMethod.DELETE,"/delete-book/**").hasAuthority("SCOPE_book:delete")
                                .anyRequest().authenticated()
                ).oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                .csrf(AbstractHttpConfigurer::disable);
		return http.build();
	}

    @Bean 
	public UserDetailsService userDetailsService() {
		UserDetails userDetails = User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(userDetails);
	}

    @Bean 
	public RegisteredClientRepository registeredClientRepository() {
        List<RegisteredClient> registrations = new ArrayList<RegisteredClient>();
        
        RegisteredClient bookstoreNormalClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("normal-client")
                .clientSecret("{noop}secret-client")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("book:create")
                .scope("book:read")
                .scope("book:update")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

            RegisteredClient bookstoreAdminClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("admin-client")
            .clientSecret("{noop}secret-admin")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .scope("book:create")
            .scope("book:read")
            .scope("book:update")
            .scope("book:delete")
            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
            .build();
        
        registrations.add(bookstoreNormalClient);
        registrations.add(bookstoreAdminClient);

		return new InMemoryRegisteredClientRepository(registrations);
	}

	@Bean 
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder()
                                          .issuer("http://localhost:8080")
                                          .build();
	}
}
