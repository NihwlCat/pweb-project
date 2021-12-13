package iftm.pedro.aproject.auth;

import iftm.pedro.aproject.entities.User;
import iftm.pedro.aproject.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {

    public static String getAuthenticated(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static boolean isAuthenticated(){
        return !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser");
    }

    public static boolean isAdmin(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(x -> x.getAuthority().equals("ADMIN"));
    }
}
