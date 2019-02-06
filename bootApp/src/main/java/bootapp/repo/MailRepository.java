package bootapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import bootapp.model.Mail;

public interface MailRepository extends JpaRepository<Mail, Long> {
	
}
