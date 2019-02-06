package bootapp.model;

import static bootapp.AppConst.*;

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

import org.hibernate.validator.constraints.URL;

import groovy.transform.EqualsAndHashCode;
import groovy.transform.ToString;

@Entity
@TableGenerator(name = "addressId", table = "ID_GEN", 
  initialValue = 300000, allocationSize = 1000)
@ToString(includeNames=true, includeFields=true)
@EqualsAndHashCode(includes="id")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "addressId")
    Long id = null;
    @Version
    Integer version = 0;

    @Column(length = 7)
    @Size(max = 7, message = SIZE_MSG)
    @Pattern(regexp="^([0-9]{3}[-]?[0-9]{4})?\$", message = "{emsg.zip}")
    String yubin;
    @Column(length = 10)
    @Size(max = 10, message = SIZE_MSG)
    String todofuken;
    @Column(length = 40)
    @Size(max = 40, message = SIZE_MSG)
    String sikuchoson;
    @Column(length = 50)
    @Size(max = 50, message = SIZE_MSG)
    String banchi;
    @Column(length = 50)
    @Size(max = 50, message = SIZE_MSG)
    String banchi2;
    @Column(length = 100)
    @Size(max = 100, message = SIZE_MSG)
    @URL
    String webpage = "";
    @Column(length = 20)
    @NotNull
    @Size(min = 1, message = "{emsg.addressType.hissu}")
    String addressType;
    
    public Address() {
    }

    public Address(Long id, String yubin, String todofuken, String sikuchoson,
            String banchi, String banchi2, String webpage, String addressType) {
        this.id = id;
        this.yubin = yubin;
        this.todofuken = todofuken;
        this.sikuchoson = sikuchoson;
        this.banchi = banchi;
        this.banchi2 = banchi2;
        this.webpage = webpage;
        this.addressType = addressType;
    }

    public Address(Address address) {
        this(null, address.getYubin(), address.getTodofuken(),
                address.getSikuchoson(), address.getBanchi(), address.getBanchi2(),
                address.getWebpage(), address.getAddressType());
    }
}
