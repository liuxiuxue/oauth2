package com.liuxiuxue.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer
public class OauthServerConfig extends AuthorizationServerConfigurerAdapter {

	private static String REALM = "Liuxiuxue's application";

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	RedisConnectionFactory redisConnectionFactory;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.realm(REALM).checkTokenAccess("isAuthenticated()").allowFormAuthenticationForClients();
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("csdn-app-client").secret("csdn-app-secret")
				.authorizedGrantTypes("authorization_code", "refresh_token").scopes("read", "write") // 在授权页面选择赋予第三方app可读可写的范围
				.resourceIds("csdn") // 第三方app所对应client_id可访问的资源服务器
				.redirectUris("http://127.0.0.1:8001/csdn/token");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setAccessTokenValiditySeconds(60 * 60);// access_token 的过期时间
		defaultTokenServices.setTokenStore(tokenStore());
		endpoints.authenticationManager(authenticationManager).tokenServices(defaultTokenServices);
	}

	@Bean
	RedisTokenStore tokenStore() {
		return new RedisTokenStore(redisConnectionFactory);
	}
}
