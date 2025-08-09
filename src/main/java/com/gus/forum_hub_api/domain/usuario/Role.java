package com.gus.forum_hub_api.domain.usuario;

public enum Role {
    ADMIN("Admin"),
    USER("User");

    private String role;

    Role(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
