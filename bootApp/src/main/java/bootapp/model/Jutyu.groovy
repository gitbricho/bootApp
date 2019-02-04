package bootapp.model

import javax.persistence.CascadeType;
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany
import javax.persistence.TableGenerator
import javax.persistence.Version

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * 受注エンティティ (Order).
 * <p>
 * 受注：明細 = 1:N (双方向)
 * <p>
 * <ul>
 * <li>受注追加）明細０件でも可
 * <li>受注削除）明細も削除
 * <li>受注更新）受注項目の更新
 * <li>受注更新）明細追加、明細削除、明細項目の更新
 * </ul>
 */
@Entity
// ID 生成の設定
@TableGenerator(name = "jutyuId", table = "ID_GEN",
initialValue = 10000, allocationSize = 10000)
// 名前付きクエリーの例：指定した納品先を持つ受注データを検索。
@NamedQuery(name = "Jutyu.findByNohinSaki",
query = "select j from Jutyu j where j.nohinSaki = ?1")
@ToString(includeNames=true, includeFields=true, excludes="meisaiList")
@EqualsAndHashCode(includes="id")
public class Jutyu implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "jutyuId")
    Long id = null;
    @Version
    Integer version = 0;

    /** 受注日 */
    String jutyuBi;
    /** 納品先 */
    String nohinSaki;
    /** 受注額 */
    int jutyuGaku;

    /** 受注明細 (1:N) */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Meisai> meisaiList = new ArrayList<Meisai>();

    /**
     * コンストラクタ.
     */
    public Jutyu() {}

    /**
     * コンストラクタ.
     * @param id
     * @param jutyuBi
     * @param nohinSaki
     */
    public Jutyu(Long id, String jutyuBi, String nohinSaki) {
        this.id = id;
        this.jutyuBi = jutyuBi;
        this.nohinSaki = nohinSaki;
    }

    /**
     * 受注額(明細の金額の合計)を求める.
     * @return 受注額.
     */
    public int getJutyuGaku() {
        jutyuGaku = 0
        for(Meisai m: meisaiList) {
            jutyuGaku += m.getTanka() * m.getSuryo()
        }
        return jutyuGaku
    }
}
