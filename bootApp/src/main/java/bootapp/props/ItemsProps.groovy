package bootapp.props;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//MARK: クラス
/**
* application.yml の items プロパティを取得,
*/
@Component
@ConfigurationProperties(prefix="items")
public class ItemsProps {
    List<String> seibetu;
    List<String> syumi;
    List<String> contactSyubetu;
    List<String> mailSyubetu;
    List<String> phoneSyubetu;
    List<String> jusyoSyubetu;
    List<String> theme;
}
