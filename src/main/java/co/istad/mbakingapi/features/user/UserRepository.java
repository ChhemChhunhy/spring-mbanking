package co.istad.mbakingapi.features.user;

import co.istad.mbakingapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByNationalCardId (String  nationalCardId);
    boolean existsByStudentIdCard(String studentIdCard);

}
