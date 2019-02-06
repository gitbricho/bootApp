package bootapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@Entity
@TableGenerator(name = "mailId", table = "ID_GEN", 
    initialValue = 300000, allocationSize = 1000)
@ToString(includeNames=true, includeFields=true)
@EqualsAndHashCode(includes="id")
public class Mail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "mailId")
    Long id = null;
    @Version
    Integer version = 0;

    @Column(length = 40)
    @Email(message = "{emsg.mail}")
    String mail;
    @Column(length = 20)
    @NotNull
    @Size(min = 1, message = "{emsg.mailType.hissu}")
    String mailType;
    
    public Mail() {}

    public Mail(Long id, String mail, String mailType) {
        this.id = id;
        this.mail = mail;
        this.mailType = mailType;
    }

    public Mail(Mail mail) {
        this(null, mail.getMail(), mail.getMailType());
    }
}
