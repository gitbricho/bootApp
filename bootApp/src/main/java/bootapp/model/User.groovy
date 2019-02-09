package bootapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table;
import javax.persistence.TableGenerator
import javax.persistence.Transient;
import javax.persistence.Version

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString;

@Entity
@TableGenerator(name = "userId", table = "ID_GEN", 
  initialValue = 100, allocationSize = 100)
@ToString(includeNames=true, includeFields=true)
@EqualsAndHashCode(includes="id")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "userId")
	Long id = null;
	@Version
	Integer version = 0;

    private static final BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();

    // ログイン情報
    @Column(unique = true, length = 12)
    @Length(min = 4, max = 12, message = "ユーザー名は{min}から{max}文字の範囲で入力してください。")
   String name;

    /** 暗号化されたパスワード */
    @Column(length = 128)
   String encodePass;
    /** ロール一覧 */
    @Transient
    private List<String> roleList;
    /** ロールカンマ区切り文字列 */
    @Column(length = 128)
    private String roles;
    /** ユーザーのデフォルト種別 */
    @Column(length = 20)
   String syubetu;
    /** ユーザーのデフォルトテーマ */
    @Column(length = 20)
   String theme = "";

    // 個人情報
    @Column(length = 30)
   String simei = "";
    @Column(length = 30)
   String yomi = "";
    @Email
   String email = "";

    public User() {}

    public User(String name) {
        this(name, name, "User", "家族知人", "", "", "", "");
    }

    public User(String name, String rawPass, String roles) {
        this(name, rawPass, roles, "家族知人", "", "", "", "");
    }

    public User(String name, String rawPass, String roles, String syubetu,
            String theme, String simei, String yomi, String email) {
        super();
        this.name = name;
        this.encodePass = bpe.encode(rawPass);
        setRoles(roles);
        this.syubetu = syubetu;
        this.theme = theme;
        this.simei = simei;
        this.yomi = yomi;
        this.email = email;
    }

    // %%%%% Role %%%%%
    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
        if (roles != null && roles.length() > 0) {
            this.roleList = Arrays.asList(roles.split(",", 0));
        }
    }

    public List<String> getRoleList() {
        if (this.roleList == null) {
            this.roleList = new ArrayList<String>();
        }
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
        if (roleList != null) {
            this.roles = String.join(",", roleList);
        }
    }
}