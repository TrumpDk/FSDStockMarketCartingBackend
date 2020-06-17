package com.ibm.fsd.smc.ms.user.repository;

import com.ibm.fsd.smc.ms.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户管理Repository
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLoginName(String loginName);
}
