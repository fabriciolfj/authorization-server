package br.com.algaworksauth.algafood;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@Component
@ConfigurationProperties(value = "algafood.jwt.keystore")
public class JwtKeyStoreProperties {

    @NotBlank
    private String path;

    @NotBlank
    private String password;

    @NotBlank
    private String keypairAlias;
}
