package com.misiontic2022.grupo11.securityBackend.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rol")
public class Rol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRol;
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;
    private String description;

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "rol")
    @JsonIgnoreProperties("rol")
    private list<User> users;

    @ManyToMany
    @JoinTable(
            name = "permissions_rol",
            joinColumns = @JoinColumn(name = "idRol"),
            inverseJoinColumns = @JoinColumn(name = "idPermission")
    )
    private Set<Permission> permissions;


    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer id) {
        this.idRol = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Permission> getPermissions() { return permissions; }

    public void setPermissions(Set<Permission> permissions) { this.permissions = permissions; }


    public list<User> getUsers() {
        return users;
    }

    public void setUsers(list<User> users) {
        this.users = users;
    }
}
