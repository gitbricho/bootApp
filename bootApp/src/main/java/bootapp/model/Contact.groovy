package bootapp.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@Entity
@TableGenerator(name = "contactId", table = "ID_GEN", 
 initialValue = 300000, allocationSize = 1000)
@ToString(includeNames=true, includeFields=true)
@EqualsAndHashCode(includes="id")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "contactId")
    Long id = null;
    @Version
    Integer version = 0;

    /** ユーザー */
    @ManyToOne
    User user;

    String googleid = "";

    // === 個人情報 ====
    /** 氏名：姓 */
    @Column(length = 20)
    @Size(min = 1, max = 20, message = "{emsg.sei.hissu}")
    String simeiSei;
    /** 氏名：名 */
    @Column(length = 20)
    @Size(min = 1, max = 20, message = "{emsg.mei.hissu}")
    String simeiMei;
    /** よみ：姓 */
    @Column(length = 20)
    String yomiSei;
    /** よみ：名 */
    @Column(length = 20)
    String yomiMei; // よみ(姓):text
    /** 性別 */
    String seibetu;
    /** 生年月日 */
    @Column(length = 10) // yyyy/MM/dd
    String seinenGappi; // 誕生日:datepicker
    // @Temporal(TemporalType.DATE)
    // @Convert(converter = LocalDateConverter.class)
    // LocalDate seinenGappi;
    /** メモ */
    String memo = "";
    /** 写真 */
    String picture = "";
    /** 趣味 */
    String syumi = "";
    @Transient
    List<String> syumiList; // 趣味:checkbox (マルチ選択のテスト用)
    /** 連絡先種別 */
    @NotNull(message = "連絡先種別は必ず設定してください")
    String syubetu; // 連絡先種別:select
    /** 会社 */
    @Column(length = 40)
    String kaisya = "";

    /** 電話番号 */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<Phone> phones = new ArrayList<Phone>();
    /** メールアドレス */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<Mail> mails = new ArrayList<Mail>();
    /** 住所 */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<Address> addresses = new ArrayList<Address>();
    
    public Contact() {}

    public Contact(Long id, User user, String simeiSei, String simeiMei,
            String yomiSei, String yomiMei, String seibetu, String seinenGappi,
            String syubetu) {
        this.id = id;
        this.user = user;
        this.simeiSei = simeiSei;
        this.simeiMei = simeiMei;
        this.yomiSei = yomiSei;
        this.yomiMei = yomiMei;
        this.seibetu = seibetu;
        this.seinenGappi = seinenGappi;
        this.syubetu = syubetu;
    }

    public Contact(Contact c) {
        this.user = c.getUser();
        this.simeiSei = c.getSimeiSei();
        this.simeiMei = c.getSimeiMei();
        this.yomiSei = c.getYomiSei();
        this.yomiMei = c.getYomiMei();
        this.seibetu = c.getSeibetu();
        this.seinenGappi = c.getSeinenGappi();
        this.memo = c.getMemo();
        this.picture = c.getPicture();
        this.syumi = c.getSyumi();
        this.syubetu = c.getSyubetu();
        this.kaisya = c.getKaisya();
        this.googleid = c.getGoogleid();
        this.addresses.addAll(c.getAddresses());
        this.mails.addAll(c.getMails());
        this.phones.addAll(c.getPhones());
    }

    public String getSyumi() {
        return syumi;
    }

    /**
     * 趣味を設定する.
     * <p>
     * 同時に、趣味（カンマ区切り文字列）を趣味リストに変換する
     * </p>
     * 
     * @param syumi
     *            趣味.
     */
    public void setSyumi(String syumi) {
        this.syumi = syumi;
        if (syumi != null && syumi.length() > 0) {
            this.syumiList = Arrays.asList(syumi.split(",", 0));
        }
    }

    /**
     * 趣味リストを取得する.
     * <p>
     * 先に、setSyumi() を呼んで、現在の趣味（カンマ区切り文字列）と 趣味リストの同期させておく.
     * </p>
     * 
     * @return 趣味リスト.
     */
    public List<String> getSyumiList() {
        setSyumi(this.syumi);
        if (this.syumiList == null) {
            this.syumiList = new ArrayList<String>();
        }
        return syumiList;
    }

    /**
     * 趣味のリストを設定する.
     * <p>
     * 入力画面から得られた趣味リストをカンマ区切り文字列に変換して syumi フィールドにセットする.
     * </p>
     *
     * @param syumiList
     *            趣味のリスト.
     */
    public void setSyumiList(List<String> syumiList) {
        this.syumiList = syumiList;
        if (syumiList != null) {
            this.syumi = String.join(",", syumiList);
        }
    }

}
