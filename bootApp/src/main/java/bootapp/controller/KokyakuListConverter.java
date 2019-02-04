package bootapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import bootapp.model.Kokyaku;
import bootapp.repo.KokyakuRepo;

/**
 * 文字列(id | email など) から List<Kokyaku> を取得するコンバーター.
 */
@Component
public class KokyakuListConverter implements Converter<String, List<Kokyaku>> {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private KokyakuRepo repo;

  /**
   * コンストラクタ.
   * 
   * @param repo
   *          顧客リポジトリ.
   */
  @Autowired
  public KokyakuListConverter(KokyakuRepo repo) {
    this.repo = repo;
  }

  /**
   * 指定された検索文字列で、該当する顧客一覧を取得.
   * 
   * @param searchStr
   *          検索文字列.
   */
  @Override
  public List<Kokyaku> convert(String searchStr) {
    log.info("searchStr=" + searchStr);
    List<Kokyaku> list = null;
    if (searchStr.startsWith("nameLike:")) {
      // 名前曖昧検索
      String str = searchStr.substring("nameLike:".length());
      log.info("### str={}", str);
      list = repo.findByNameLikeOrderByNameDesc("%" + str + "%");
    } else if (searchStr.startsWith("name:")) {
      // 名前検索
      String str = searchStr.substring("name:".length());
      log.info("### str={}", str);
      list = repo.findByName(str);
    } else if (searchStr.startsWith("emailLike:")) {
      // メールアドレス曖昧検索
      String str = searchStr.substring("emailLike:".length());
      log.info("### str={}", str);
      list = repo.findByEmailLike("%" + str + "%");
    } else if (searchStr.startsWith("email:")) {
      // メールアドレス検索
      String str = searchStr.substring("email:".length());
      log.info("### str={}", str);
      list = repo.findByEmail(str);
    }
    return list;
  }

}
