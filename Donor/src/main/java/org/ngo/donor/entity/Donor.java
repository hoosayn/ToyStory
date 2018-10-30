package org.ngo.donor.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="NGO_DONOR")
@Getter
@Setter
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_DONOR_ID")
    private Long userid;

    @NotNull
    @Column(unique = true, name = "USERNAME")
    private String sub;

    @NotNull
    @Column(name = "ROLE")
    private String role;

    @NotNull
    @Column(name = "DONATED_COUNT")
    private Long count;

    @Transient
    private String exp;

    @Transient
    private String iss;

}
