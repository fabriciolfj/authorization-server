package br.com.algaworksauth.algafood.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Grupo {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @ManyToMany
    @JoinTable(name = "grupo_permissao",joinColumns = @JoinColumn(name = "grupo_id"), inverseJoinColumns = @JoinColumn(name = "permissao_id"))
    private List<Permissao> permissoes = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "grupo_id"))
    private Set<Grupo> grupos = new HashSet<>();

    public void adicionarPermissao(Permissao permissao){
        this.permissoes.add(permissao);
    }

    public void removerPermissao(Permissao permissao){
        this.permissoes.remove(permissao);
    }
}
