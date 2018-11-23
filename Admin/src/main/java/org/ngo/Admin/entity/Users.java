package org.ngo.admin.entity;

import com.sun.javafx.beans.IDProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "NGO_USERS")
@Getter
@Setter
public class Users implements Serializable {

    private static final long serialVersionUID = 7156526077883281623L;

    @Id
    @Column(name = "PK_USERS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "DONOR_ID")
    private long userid;

    @NotNull
    @Column(name = "DONORNAME")
    private String username;

    @NotNull
    @Column(name = "ROLE")
    private String role;

    @NotNull
    @Column(name = "FIRSTNAME")
    private String firstname;

    @NotNull
    @Column(name = "ADDRESS")
    private String address;

    @NotNull
    @Column(name = "DONATED_COUNT")
    private Long count;

    @Transient
    private String sub;

    @Transient
    private String exp;

    @Transient
    private String iss;
}
//{sub=asif2, userid=2, role=DONOR, count=0, username=asif2, firstname=asif, address=street2, exp=1541575261, iss=asak}