package com.Shuan.spring_boot_study.respository;
import com.Shuan.spring_boot_study.model.User;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

   private final List<User> users = List.of(
           new User(1l,"Alice"),
           new User(2L ,"Bob"),
           new User(3L, "Charlie")
   );

    public List<User> findAll() {
        return users;
    }
    public Optional<User> findById(long id) {
        return  users.stream().filter(user -> user.id().equals(id)).findFirst();
    }
}
