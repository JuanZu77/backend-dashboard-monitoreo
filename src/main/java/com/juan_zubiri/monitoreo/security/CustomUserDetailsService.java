package com.juan_zubiri.monitoreo.security;  

import com.juan_zubiri.monitoreo.dao.UserRepository;
import com.juan_zubiri.monitoreo.model.User; // Asegúrate de tener esta clase de entidad

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Verificar si hay usuarios en la base de datos
        if (userRepository.count() == 0) {
            throw new EmptyDatabaseException("No hay usuarios en la base de datos.");
        }

        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    user.get().getEmail(),
                    user.get().getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
    }
}
