package com.gus.forum_hub_api.infra.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {
    public static String getEmailUsuarioLogado(){
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) return userDetails.getUsername();
        return principal.toString();
    }
}
