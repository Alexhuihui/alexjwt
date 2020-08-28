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
import top.alexmmd.alexjwt.dao.ApplicationUserDao;
import top.alexmmd.alexjwt.dao.RoleDao;
import top.alexmmd.alexjwt.model.ApplicationUser;
import top.alexmmd.alexjwt.model.RoleDetail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    private final RoleDao roleDao;

    private final ApplicationUserDao applicationUserDao;

    public ApplicationUserDetailsService(RoleDao roleDao, ApplicationUserDao applicationUserDao) {
        this.roleDao = roleDao;
        this.applicationUserDao = applicationUserDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserDao.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        Collection<? extends GrantedAuthority> grantedAuthorityList = getAuthorities(roleDao.queryAllByUserId(applicationUser.getId()));
        return new User(applicationUser.getStaffCode(), applicationUser.getPassword(), grantedAuthorityList);
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Collection<RoleDetail> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    public List<String> getPrivileges(Collection<RoleDetail> roles) {
        return roles.stream().map(RoleDetail::getPrivilegeName).collect(Collectors.toList());
    }

    public List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

}
