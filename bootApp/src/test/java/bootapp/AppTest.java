package bootapp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import javax.persistence.EntityManagerFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.junit4.SpringRunner;

import bootapp.controller.ContactController;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AppTest {
    
    private static final Logger log = LoggerFactory.getLogger(AppTest.class);
    
    @Autowired
    ApplicationContext ctx;
    @Autowired
    Environment env;

    //=====(TEST-METHOD)=====
    // ### 全てはログの出力で確認できるがテストで確認する
    @Test
    public void applicationContextIsNotNull() throws Exception {
        log.info("server.port="+env.getRequiredProperty("server.port"));
        assertThat(this.ctx).isNotNull();
    }
    @Test
    public void ctxContainsApp() throws Exception {
        log.info("## アプリは起動してる？");
        assertThat(this.ctx.containsBean("app")).isTrue();
        assertThat(this.ctx.containsBean("dispatcherServlet")).isTrue();
    }
    @Test
    public void ctxContainsAppConfig() throws Exception {
        assertThat(this.ctx.containsBean("appConfig")).isTrue();
    }
    @Test
    public void ctxContainsSecConfig() throws Exception {
        assertThat(this.ctx.containsBean("secConfig")).isTrue();
    }
    @Test
    public void ctxContainsDbConfig() throws Exception {
        assertThat(this.ctx.containsBean("dbConfig")).isTrue();
    }
    @Test
    public void ctxContainsEntityManagerFactory() throws Exception {
        assertThat(this.ctx.containsBean("entityManagerFactory")).isTrue();
    }
    @Test
    public void checkAppProperties1() throws Exception {
        log.info("## プロパティの値1: application.yml");
        assertThat(env.getProperty("server.contextPath")).isEqualTo("/bootApp");
        assertThat(env.getProperty("spring.thymeleaf.cache")).isEqualTo("false");
        assertThat(env.getProperty("logging.file")).isEqualTo("bootApp");
    }
    @Test
    public void checkDbSetting() throws Exception {
        log.info("## プロパティの値2: application.yml");
        assertThat(env.getProperty("spring.profiles.active")).isEqualTo("");
        assertThat(env.getProperty("model.scan.package")).isEqualTo("bootapp.model");
        assertThat(env.getProperty("message.source.basename")).isEqualTo("classpath:messages/messages");
        assertThat(env.getProperty("eclipselink.ddl-generation")).isEqualTo("drop-and-create-tables");
        assertThat(env.getProperty("eclipselink.target-database"))
            .isEqualTo("org.eclipse.persistence.platform.database.H2Platform");
        assertThat(env.getProperty("eclipselink.weaving")).isEqualTo("false");
        assertThat(env.getProperty("eclipselink.logging.level")).isEqualTo("config");
    }
    @Test
    public void checkActiveProfile() throws Exception {
       log.info("## アクティブ・プロファイル?");
        Arrays.asList(env.getActiveProfiles()).forEach( ap -> {
            log.info(">>> activeProfile=" + ap);
        });
    }
    @Test
    public void checkH2DbSetting() throws Exception {
        assertThat(env.getProperty("spring.datasource.driverClassName")).isEqualTo("org.h2.Driver");
        assertThat(env.getProperty("spring.datasource.url")).isEqualTo("jdbc:h2:~/_data/h2/bootContact;MODE=MySQL");
        assertThat(env.getProperty("spring.datasource.username")).isEqualTo("demo");
        assertThat(env.getProperty("spring.datasource.password")).isEqualTo("pass");
        
        EntityManagerFactory emf = (EntityManagerFactory)this.ctx.getBean("entityManagerFactory");
        assertThat(emf.getProperties().get("eclipselink.ddl-generation")).isEqualTo("drop-and-create-tables");
        assertThat(this.ctx.containsBean("dataSource")).isTrue();
        DriverManagerDataSource dsc = (DriverManagerDataSource)this.ctx.getBean("dataSource");
        assertThat(dsc.getUrl()).isEqualTo("jdbc:h2:~/_data/h2/bootContact;MODE=MySQL");
    }
    @Test
    public void checkBeanTransactionManager() throws Exception {
        assertThat(this.ctx.containsBean("transactionManager")).isTrue();
    }
    @Test
    public void checkBeanRestTemplate() throws Exception {
        assertThat(this.ctx.containsBean("restTemplate"));
    }
    @Test
    public void checkContactController() throws Exception {
        assertThat(this.ctx.containsBean("contactController")).isTrue();
        assertThat(this.ctx.getType("contactController")).isEqualTo(ContactController.class);
    }
    @Test
    public void checkRepository() throws Exception {
        assertThat(this.ctx.containsBean("userRepository")).isTrue();
        assertThat(this.ctx.containsBean("contactRepository")).isTrue();
    }
    @Test
    public void checkService() throws Exception {
        assertThat(this.ctx.containsBean("userServiceImpl")).isTrue();        
        assertThat(this.ctx.containsBean("contactService")).isFalse();
    }
    public void checkRequestMappingHandler() throws Exception {
        assertThat(this.ctx.containsBean("requestMappingHandlerAdapter")).isTrue();
    }
}
