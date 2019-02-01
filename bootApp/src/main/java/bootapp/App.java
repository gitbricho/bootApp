package bootapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

/**
 * アプリケーション起動クラス.
 * <p>
 * Spring boot 雛形アプリケーションの起動クラス.</p>
 */
@SpringBootApplication
// @Configuration, @EnableAutoConfiguration, @ComponentScan を一緒に使用するのと同等。

@ComponentScan("bootapp")
// 指定したパッケージ以下（サブパッケージも含めて）をスキャンするように指示。
// コンポーネントが見つかれば、インスタンスを生成して、Spring DI コンテナに登録。
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
}