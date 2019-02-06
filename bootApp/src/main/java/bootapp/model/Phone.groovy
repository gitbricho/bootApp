package bootapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@Entity
@TableGenerator(name = "phoneId", table = "ID_GEN", 
    initialValue = 300000, allocationSize = 1000)
@ToString(includeNames=true, includeFields=true)
@EqualsAndHashCode(includes="id")
public class Phone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "phoneId")
    Long id = null;
    @Version
    Integer version = 0;
    
    @Column(length = 13)
    @Size(max = 13)
    // メッセージを明示しない場合：JSR303のデフォルトメッセージが表示される.
    @Pattern(regexp = "^(\\d{2,4}\\d{2,4}\\d{4})?\$", message = "{emsg.telno}")
    String telno;
    // Size は EL式で messages.properties の cstm.size.msg を表示
    @Column(length = 20)
    @NotNull
    @Size(min = 1, message = "{emsg.phoneType.hissu}")
    String phoneType;
    
    public Phone() {}

    public Phone(Long id, String telno, String phoneType) {
        this.id = id;
        this.telno = telno;
        this.phoneType = phoneType;
    }

    public Phone(Phone phone) {
        this(null, phone.getTelno(), phone.getPhoneType());
    }
}
