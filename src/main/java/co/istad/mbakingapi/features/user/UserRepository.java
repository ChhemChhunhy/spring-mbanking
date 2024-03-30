package co.istad.mbakingapi.features.user;

import co.istad.mbakingapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByNationalCardId (String  nationalCardId);
    boolean existsByStudentIdCard(String studentIdCard);

    boolean existsByName(String name);

    Optional<User> findByPassword(String password);
    Optional<User> findByUuid(String uuid);


}
