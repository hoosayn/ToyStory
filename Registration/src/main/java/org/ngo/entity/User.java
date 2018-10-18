package org.ngo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="NGO_USER")
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_USER_ID")
    private Long id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "USERNAME")
    private String username;


    @Size(min = 1, max = 99)
    private String password;

    @Column
    private String title;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="NGO_USER_ROLE",
            joinColumns = {@JoinColumn(name="FK_USER_ID", referencedColumnName = "PK_USER_ID")},
            inverseJoinColumns = @JoinColumn(name="FK_ROLE_ID", referencedColumnName = "PK_ROLE_ID")
    )
    private Collection<Role> roles = new HashSet<>();

    @Transient
    private List<GrantedAuthority> authorities = AuthorityUtils.NO_AUTHORITIES;



    @Transient
    private boolean isAccountNonExpired = true;

    @Transient
    private boolean isAccountNonLocked = true;

    @Transient
    private boolean isCredentialsNonExpired = true;

    @Transient
    private boolean isEnabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}

