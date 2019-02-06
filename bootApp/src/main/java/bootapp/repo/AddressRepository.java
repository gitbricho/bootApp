package bootapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import bootapp.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
	
}
