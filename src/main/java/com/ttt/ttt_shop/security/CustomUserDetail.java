package com.ttt.ttt_shop.security;

import com.ttt.ttt_shop.model.entity.Authority;
import com.ttt.ttt_shop.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetail implements UserDetails {
    private User user;
    //không cần thiết vì đã có quan hệ n-n giữa bảng users và authorities thông qua bảng trung gian role.
    // Spring Security sẽ tự động lấy các quyền từ cơ sở dữ liệu và chuyển đổi chúng thành GrantedAuthority.
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<Authority> authorities = user.getAuthorities();
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//
//        for (Authority authority : authorities) {
//            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthorityName()));
//            if (authority.getAuthorityName().equals("ROLE_ADMIN")) {
//                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//            } else if (authority.getAuthorityName().equals("ROLE_CUSTOMER")) {
//                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
//            }
//        }
//
//        return grantedAuthorities;
//    }
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            Set<Authority> authorities = user.getAuthorities();
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            for (Authority authority : authorities) {
                grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthorityName()));
            }

            return grantedAuthorities;
        }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
        return true;
    }
}
