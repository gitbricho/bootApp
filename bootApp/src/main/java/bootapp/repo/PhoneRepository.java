package bootapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import bootapp.model.Phone;


public interface PhoneRepository extends JpaRepository<Phone, Long> {
	
}
