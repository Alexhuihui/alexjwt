package top.alexmmd.alexjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.alexmmd.alexjwt.model.ApplicationUser;

/**
 * @author 汪永晖
 */
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
}
