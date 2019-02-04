package bootapp;

import static bootapp.AppConst.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import bootapp.service.UserService;

/**
 * セキュリティ設定.
 */
@Configuration
public class SecConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	// 誰でもアクセス可能な URL の設定
            .antMatchers(URL_ROOT, URL_HOME).permitAll()
            .antMatchers("/css/**", "js/**", "/os/**").permitAll()
            // ADMIN ロールのみがアクセスできる URL
            .antMatchers(URL_ADMIN + "/**").hasRole("ADMIN")
            .antMatchers(URL_USER_LIST + "/**").hasRole("ADMIN")
            // bootJutyu では全ページアクセスできるように以下をコメント文とする.
            //.anyRequest().authenticated() 
            .and().formLogin()
            .loginPage(URL_LOGIN).defaultSuccessUrl(URL_HOME).permitAll()
            .and().logout().permitAll();
        //http.csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	// ログインユーザーの取得に使用するサービスを設定.
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}