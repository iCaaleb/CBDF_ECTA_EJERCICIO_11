package com.upiiz.securitydb.services;

import com.upiiz.securitydb.entities.UserEntity;
import com.upiiz.securitydb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Consultar un usuario por username con todos sus detalles
        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username+" not found"));

        // Authorities = Sus datos, roles y permisos
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();

        // Roles
        userEntity.getRoles()
                .forEach(rol -> {grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + rol.getRoleEnum().name()));});

        userEntity.getRoles().stream()
                .flatMap(role->role.getPermissions().stream())
                .forEach(permission->grantedAuthorities.add(new SimpleGrantedAuthority(permission.getName())));

        // Regresamos el usuario con datos y permisos
        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(),
                grantedAuthorities
        );
    }
}
