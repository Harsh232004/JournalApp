package com.auction.springrestapi.Repo;

/*import com.auction.springrestapi.DTO.userDTO;*/
import com.auction.springrestapi.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
}
