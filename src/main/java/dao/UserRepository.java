package dao;

import Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component("userRepository")
public interface UserRepository extends JpaRepository<User,Long> {
    User findOneByUsername(String username);
}
