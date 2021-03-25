package top.alexmmd.alexjwt.service;

/**
 * @author 汪永晖
 */
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.alexmmd.alexjwt.model.ApplicationUser;
import top.alexmmd.alexjwt.model.RoleDetail;
import top.alexmmd.alexjwt.repository.ApplicationUserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    private ApplicationUserRepository applicationUserRepository;

    public ApplicationUserDetailsService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        if (applicationUser == null) {
//            throw new UsernameNotFoundException(username);
            // 如果该用户没有注册，那么就在此处注册
            applicationUserRepository.save(ApplicationUser.builder().username(username).id(2).password("222").build());
            applicationUser = applicationUserRepository.findByUsername(username);
        }
        Collection<? extends GrantedAuthority> grantedAuthorityList = getAuthorities(Arrays.asList(new RoleDetail(1, "ROLE_ADMIN"), new RoleDetail(2, "ROLE_USER")));

        return new User(applicationUser.getUsername(), applicationUser.getPassword(), grantedAuthorityList);
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Collection<RoleDetail> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    public List<String> getPrivileges(Collection<RoleDetail> roles) {
        return roles.stream().map(RoleDetail::getRoleName).collect(Collectors.toList());
    }

    public List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
