package org.ngo.admin.entity;

import lombok.Data;

import javax.persistence.Transient;


@Data
public class Admin {

    private long userid;

    private String sub;

    private String role;

    private String exp;


}

