package uz.uzum.financeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.uzum.financeapp.model.UserInfo;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByUsername(String username);

}