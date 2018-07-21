package core.auth.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsModel implements UserDetails {

    User user;

    public UserDetailsModel( User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthority();
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
       return  user.getIsAccountNonExpired() == 1 ? true:false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return  user.getIsAccountNonLocked() == 1 ? true:false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getIsCredentialsNonExpired() == 1 ?true:false;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled == 1 ?true:false;
    }
}
