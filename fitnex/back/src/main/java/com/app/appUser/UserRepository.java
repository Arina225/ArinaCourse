package com.app.appUser;

import com.app.enums.AgeCategory;
import com.app.enums.Gender;
import com.app.enums.Role;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

    List<AppUser> findAllByRole(Role role, Sort sort);

    List<AppUser> findAllByRoleAndProfile_AgeCategory(Role role, AgeCategory ageCategory);

    List<AppUser> findAllByRoleAndProfile_Gender(Role role, Gender gender);
}
