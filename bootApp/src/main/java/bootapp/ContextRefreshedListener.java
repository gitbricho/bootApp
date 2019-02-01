package bootapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * アプリケーション・リスナーの実装例を示す。
 * アプリの起動前にテストデータを作成する。
 * 
 * @author bri_tcho
 */

@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private AppData data;
	
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.err.println("アプリ実行前にログインに必要なユーザーを登録する.");
        data.createUsers();
    }
	
}
