package bootapp.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//MARK: クラス
/**
* application.yml の contact プロパティを取得,
*/
@Component
@ConfigurationProperties(prefix = "contact")
public class ContactProp {
    int rowsOfPage;
    String defaultTheme;
    String defaultSyubetu;
}
