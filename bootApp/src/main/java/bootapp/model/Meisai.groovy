package bootapp.model

import javax.persistence.Entity;
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator
import javax.persistence.Version

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * 明細エンティティ.
 */
@Entity
// ID 生成の設定
@TableGenerator(name = "meisaiId", table = "ID_GEN",
initialValue = 10000, allocationSize = 10000)
@ToString(includeNames=true, includeFields=true)
@EqualsAndHashCode(includes="id")
public class Meisai implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "meisaiId")
    Long id = null;
    @Version
    Integer version = 0;

    /** 商品 */
    String syohin ="";
    /** 単価 */
    int tanka = 0;
    /** 数量 */
    int suryo = 0;

    /** 受注 */
    @ManyToOne
    Jutyu jutyu;

    public Meisai() {}

    public Meisai(Long id, String syohin, int tanka, int suryo, Jutyu jutyu) {
        this.id = id;
        this.syohin = syohin;
        this.tanka = tanka;
        this.suryo = suryo;
        this.jutyu = jutyu;
    }
}
