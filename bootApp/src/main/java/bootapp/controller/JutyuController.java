package bootapp.controller;

import static bootapp.AppConst.ATTR_KEKKA_LIST;
import static bootapp.AppConst.VIEW_KEKKA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bootapp.model.Jutyu;
import bootapp.model.Kokyaku;
import bootapp.model.Meisai;
import bootapp.repo.JutyuRepo;
import bootapp.repo.KokyakuRepo;
import bootapp.repo.MeisaiRepo;

@Controller
public class JutyuController {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private JutyuRepo jutyuRepo;
  
  @Autowired
  private MeisaiRepo meisaiRepo;

  @Autowired
  private KokyakuRepo kokyakuRepo;

  @ModelAttribute("syohinMeiValues")
  public List<String> loadSyohinValues() {
    return Arrays.asList(new String[] { "商品001", "商品002", "商品003", "商品004", "商品005" });
  }

  @ModelAttribute("kokyakuList")
  public List<Kokyaku> loadKokyakuList() {
    return kokyakuRepo.findAll();
  }

  @ModelAttribute("jutyuList")
  public List<Jutyu> loadJutyuList() {
    return jutyuRepo.findAll();
  }

  // =====(MAP-METHOD)=====

  @GetMapping("/jutyu_list")
  public String showJutyuList() {
    return "views/jutyu_list";
  }

  // %%%%% パスパラメータの取得 %%%%%
  // 例1）/jutyu/meisai/301
  @GetMapping("/jutyu/meisai/{meisaiId}")
  public ModelAndView getJutyuMeisai(@PathVariable("meisaiId") Long id) {
    List<String> kekkaList = new ArrayList<>();
    kekkaList.add("リクエスト: /jutyu/meisai/{meisaiId}");
    kekkaList.add("meisaiId = " + id);
    Meisai meisai = meisaiRepo.findOne(id);
    kekkaList.add("取得した明細：" + meisai.toString());
    return new ModelAndView(VIEW_KEKKA, ATTR_KEKKA_LIST, kekkaList);
  }

  // 例2）/jutyu/syohin/100/15
  @GetMapping("/jutyu/syohin/{tanka}/{kosu}")
  public ModelAndView getSyohin(@PathVariable("tanka") int tanka, @PathVariable("kosu") int kosu) {
    List<String> kekkaList = new ArrayList<>();
    kekkaList.add("リクエスト: /jutyu/syohin/{tanka}/{kosu}");
    kekkaList.add("tanka = " + tanka);
    kekkaList.add("kosu = " + kosu);
    // TODO: 渡された単価、個数を使った処理を行う
    return new ModelAndView(VIEW_KEKKA, ATTR_KEKKA_LIST, kekkaList);
  }

  // %%%%% クエリパラメータの取得 %%%%%
  // 例1）/kokyaku?id=10&name=納品先01"
  @GetMapping("/kokyaku")
  public ModelAndView getKokyaku(@RequestParam String id, @RequestParam String name) {
    List<String> kekkaList = new ArrayList<>();
    kekkaList.add("リクエスト: /kokyaku?id=XXX&name=XXX");
    kekkaList.add("@RequestParam id = " + id + " , name = " + name);
    Kokyaku kokyaku = kokyakuRepo.findOne(Long.valueOf(id));
    kekkaList.add("取得した顧客: " + kokyaku.toString());
    return new ModelAndView(VIEW_KEKKA, ATTR_KEKKA_LIST, kekkaList);
  }

  /**
   * コンバーターを使って文字列から Kokyaku へ変換.
   * 
   * @param kokyaku
   * @return
   */
  @GetMapping("/kokyaku/{id}")
  public ModelAndView getKokyaku(@PathVariable("id") Kokyaku kokyaku) {
    log.debug("kokyaku=" + kokyaku);
    List<String> kekkaList = new ArrayList<>();
    kekkaList.add("リクエスト: /kokyaku/{id}");
    if (kokyaku != null) {
      kekkaList.add("取得した顧客: " + kokyaku.toString());
    } else {
      kekkaList.add("not found");
    }
    return new ModelAndView(VIEW_KEKKA, ATTR_KEKKA_LIST, kekkaList);
  }

  /**
   * コンバーターを使って文字列から List<Kokyaku>へ変換.
   * <p>{str:.+} の説明:<br/>
   * "."などの特別な文字を正しく渡すため。 <br/>
   * "email:k03@xxx.com" が渡された場合、{str}だけだと
   * "email:k03@xxx となる</p>
   * @param kokyakuList
   * @return
   */
  @GetMapping("/kokyaku/search/{str:.+}")
  public ModelAndView searchKokyaku(@PathVariable("str") List<Kokyaku> klist) {
    return getKekkaMav(klist);
  }

  private ModelAndView getKekkaMav(List<Kokyaku> kokyakuList) {
    List<String> kekkaList = new ArrayList<>();
    kekkaList.add("リクエスト: /kokyaku/search/{str:.+}");
    kekkaList.add("str には検索文字列が渡される：");
    if (kokyakuList != null) {
      kekkaList.add("取得件数: " + kokyakuList.size() + "件");
      kokyakuList.forEach((k) -> kekkaList
          .add(k.toString()));
    } else {
      kekkaList.add("not found");
    }
    return new ModelAndView(VIEW_KEKKA, ATTR_KEKKA_LIST, kekkaList);
  }
}
