package org.dadeco.cu996.api.repository;

import org.dadeco.cu996.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByNtAccount(String ntAccount);

	User findByEmail(String email);

    List<User> findUsersByEmailOrNtAccount(String email, String ntAccount);

    @Modifying
    @Transactional
    void deleteByNtAccount(String ntAccount);

}
