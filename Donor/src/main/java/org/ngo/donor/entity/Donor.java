package org.ngo.donor.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="NGO_DONOR")
@Getter
@Setter
public class Donor implements Serializable {

    private static final long serialVersionUID = 7156526077883281623L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_DONOR_ID")
    private Long userid;

    @NotNull
    @Column(unique = true, name = "USERNAME")
    private String username;

    @NotNull
    @Column(name = "ROLE")
    private String role;

    @NotNull
    @Column(name = "DONATED_COUNT")
    private Long count;

    @NotNull
    @Column(name = "FIRSTNAME")
    private String firstname;

    @NotNull
    @Column(name = "ADDRESS")
    private String address;

    @Transient
    private String exp;

    @Transient
    private String iss;

    @Transient
    private String sub;
}
