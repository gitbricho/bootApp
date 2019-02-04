package bootapp.model

import javax.persistence.Entity;
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.TableGenerator
import javax.persistence.Version

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * 顧客エンティティ (Customer).
 */
@Entity
// ID 生成の設定
@TableGenerator(name = "kokyakuId", table = "ID_GEN",
initialValue = 10000, allocationSize = 10000)
@ToString(includeNames=true, includeFields=true)
@EqualsAndHashCode(includes="id")
public class Kokyaku implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "kokyakuId")
    Long id = null;
    @Version
    Integer version = 0;

    /** 顧客名 */
    String name = null;
    /** 電子メール */
    String email = null;
    /** 受注回数 */
    Integer jutyuKaisu = 0;

    /**
     * コンストラクタ.
     */
    public Kokyaku() {}

    /**
     * コンストラクタ.
     * @param id
     * @param name
     * @param email
     * @param jutyuKaisu
     */
    public Kokyaku(Long id, String name, String email, Integer jutyuKaisu) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.jutyuKaisu = jutyuKaisu;
    }
}
