package br.com.algaworksauth.algafood.core;

import br.com.algaworksauth.algafood.model.Usuario;
import br.com.algaworksauth.algafood.model.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(s)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado."));

        return new AuthUser(usuario);
    }
}
