package bootapp.model;

//MARK: クラス
/**
* 連絡先フォーム・バッキング・ビーン
*/
public class ContactCmd {
    Long id;
    Integer version;
    // 個人情報
    String simeiSei; // 氏名:text
    String simeiMei;
    String yomiSei; // よみ:text
    String yomiMei;
    String seibetu; // 性別:radio
    String seinenGappi; // 誕生日:datepicker
    String memo; // メモ:textarea
    String picture; // 写真:file|DND
    String syumi; // 趣味:checkbox (マルチ選択のテスト用)
    List<String> syumiList; // 趣味:checkbox (マルチ選択のテスト用)
    String syubetu; // 連絡先種別:select

    // 仕事関連
    String kaisya; // 会社:text

    User user;
    String googleid;
    
    // 制御ステータス： 11:追加成功 | 12:更新成功 | 13：削除成功
    // 90:検証エラー | 91:保存失敗 | 93:削除失敗 | 0:初期状態
    int status;
    // ページ全体のメッセージ
    String message;
    // フィールドごとのメッセージ
    Map<String, String> fldErrors;
    
    public ContactCmd() {}

    public ContactCmd(Contact c) {
        init(c);
    }
    
    public ContactCmd(int status, String message) {
        this.status = status;
        this.message = message;
    }
    
    public void init(Contact c) {
        this.id = c.getId();
        this.version = c.getVersion();
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
        this.user = c.getUser();
        this.googleid = c.getGoogleid();
        //InvokerHelper.setProperties(this, c.properties)
    }
}
