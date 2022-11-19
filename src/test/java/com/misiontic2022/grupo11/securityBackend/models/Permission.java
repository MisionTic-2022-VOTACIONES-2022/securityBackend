package com.misiontic2022.grupo11.securityBackend.models;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "permission")
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPermission;
    @Column(name = "url", nullable = false, unique = true)
    private String url;
    @Column(name = "method", nullable = false)
    private String method;

    @ManyToMany(mappedBy = "permissions")
    private Set<Rol> roles;


    public Integer getId() {
        return getIdPermission();
    }

    public void setId(Integer id) {
        this.setIdPermission(id);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }
}
