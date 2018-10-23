package org.ngo.registration.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "NGO_ROLE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    public Role(Long id, String rolecode, String rolename){
        this.id = id;
        this.rolecode = rolecode;
        this.rolename = rolename;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ROLE_ID")
    private Long id;

    @Column(name = "ROLECODE")
    @Size(min = 3, max = 20)
    private String rolecode;

    @Column(name = "ROLENAME")
    @Size(min = 3, max = 20)
    private String rolename;

    @ManyToMany(fetch = FetchType.LAZY,
                cascade = CascadeType.ALL
          )
    private Set<User> users = new HashSet<User>();

}
