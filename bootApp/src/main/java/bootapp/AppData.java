package bootapp;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bootapp.model.User;
import bootapp.repo.UserRepository;

/**
 * データ作成クラス.
 */
@Component
public class AppData {
    @Autowired
    private UserRepository userRepo;

    private AppData() {
    }

    /**
     * ユーザーデータの作成.
     * <p>
     * ログイン・ユーザーを２件登録する.
     * </p>
     */
    public void createUsers() {
    	// ユーザーデータが未登録の場合は登録する.
        if (getUserCount() == 0) {
        	//ADMINロールのユーザー: ユーザー一覧ページにアクセスできる.
            userRepo.save(new User("tosi", "tosi", "ADMIN"));
            //USERロールのユーザー： ユーザー一覧ページにアクセスできない.
            userRepo.save(new User("yumi", "yumi", "USER"));
        }
    }

    /**
     * 登録ユーザーの件数を返す.
     * 
     * @return
     */
    public long getUserCount() {
        return userRepo.count();
    }
}
