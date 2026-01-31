package medium2.repository;

import medium2.model.PhoneRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneRepository extends JpaRepository<PhoneRecord, Long> {
    Optional<PhoneRecord> findByName(String name);
}
