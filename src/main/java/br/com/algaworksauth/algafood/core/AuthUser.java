package br.com.algaworksauth.algafood.core;

import br.com.algaworksauth.algafood.model.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;

@Getter
public class AuthUser extends User {

    private static final long serialVersionUID = -4043784805167384690L;

    private Long userId;
    private String fullName;

    public AuthUser(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getEmail(), usuario.getSenha(), authorities);

        this.fullName = usuario.getNome();
        this.userId = usuario.getId();
    }
}
