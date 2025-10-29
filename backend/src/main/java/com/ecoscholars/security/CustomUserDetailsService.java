package com.ecoscholars.security;

import com.ecoscholars.entity.User;
import com.ecoscholars.entity.UserRole;
import com.ecoscholars.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Custom UserDetailsService implementation
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmailAndIsActiveTrue(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        return UserPrincipal.create(user);
    }

    public static class UserPrincipal implements UserDetails {
        private Long id;
        private String username;
        private String email;
        private String password;
        private Collection<? extends GrantedAuthority> authorities;
        private Boolean isActive;
        private Boolean isEmailVerified;
        private Long studentId;
        private Long schoolAdminId;
        private Long systemAdminId;

        public UserPrincipal(Long id, String username, String email, String password,
                            Collection<? extends GrantedAuthority> authorities, Boolean isActive,
                            Boolean isEmailVerified, Long studentId, Long schoolAdminId, Long systemAdminId) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.password = password;
            this.authorities = authorities;
            this.isActive = isActive;
            this.isEmailVerified = isEmailVerified;
            this.studentId = studentId;
            this.schoolAdminId = schoolAdminId;
            this.systemAdminId = systemAdminId;
        }

        public static UserPrincipal create(User user) {
            Set<GrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                    .collect(Collectors.toSet());

            return new UserPrincipal(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    authorities,
                    user.getIsActive(),
                    user.getIsEmailVerified(),
                    user.getStudentId(),
                    user.getSchoolAdminId(),
                    user.getSystemAdminId()
            );
        }

        public Long getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public Long getStudentId() {
            return studentId;
        }

        public Long getSchoolAdminId() {
            return schoolAdminId;
        }

        public Long getSystemAdminId() {
            return systemAdminId;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return isActive;
        }
    }
}
