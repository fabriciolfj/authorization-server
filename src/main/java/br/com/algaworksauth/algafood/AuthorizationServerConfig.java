package br.com.algaworksauth.algafood;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager; // apenas para o password flow

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                    .withClient("algafood-web")
                    .secret(passwordEncoder.encode("123"))
                    .authorizedGrantTypes("password", "refresh_token")
                    .scopes("write", "read")
                    .accessTokenValiditySeconds(60)
                    .refreshTokenValiditySeconds(60 * 6)
                .and()
                    .withClient("foodanalytics")
                    .secret(passwordEncoder.encode("123"))
                    .authorizedGrantTypes("authorization_code", "refresh_token")
                    .scopes("write", "read")
                    .redirectUris("http://localhost:8082")
                    .accessTokenValiditySeconds(60)
                    .refreshTokenValiditySeconds(60 * 6)
                .and()
                    .withClient("mobile")
                    .authorizedGrantTypes("implicit")
                    .scopes("write", "read")
                    .redirectUris("http://aplicacao-cliente:8082")
                    .accessTokenValiditySeconds(60 * 60 *60 *60)
                .and()
                    .withClient("checktoken")
                    .secret(passwordEncoder.encode("123"))
                    .authorizedGrantTypes("password", "refresh_token")
                    .scopes("write", "read")
                    .accessTokenValiditySeconds(60)
                    .refreshTokenValiditySeconds(60 * 6)
                .and()
                    .withClient("faturamento")
                    .secret(passwordEncoder.encode("123"))
                    .authorizedGrantTypes("client_credentials")
                    .scopes("write", "read");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(redisTokenStore())
                .userDetailsService(userDetailsService)
                .tokenGranter(tokenGranter(endpoints));
                //.reuseRefreshTokens(false);
    }

    private TokenStore redisTokenStore() {
        return new RedisTokenStore(connectionFactory);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()");  //precisa estar autenticado para acessar o check token, permitAll() para náo precisar de autenticação, isAuthenticated() precisa estar autenticado
       // .allowFormAuthenticationForClients() para autenticar no form-urlencoded no post colocando o client_id e client_secret
    }

    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
                endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory());

        var granters = Arrays.asList(
                pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());

        return new CompositeTokenGranter(granters);
    }
}
