package br.com.symon.rentapi.service;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import br.com.symon.rentapi.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.symon.rentapi.repository.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Log4j2
@AllArgsConstructor
@Service
public class UserService  implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerNewUser(@Valid User user, String password) {
        var encodedPass = passwordEncoder.encode(password);
        user.setPasswordHash(encodedPass);
        userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(user -> new UserDetails() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return user.getRoles().stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());
                    }

                    @Override
                    public String getPassword() {
                        return user.getPasswordHash();
                    }

                    @Override
                    public String getUsername() {
                        return user.getEmail();
                    }
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}