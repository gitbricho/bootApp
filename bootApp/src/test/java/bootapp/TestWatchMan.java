package bootapp;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * テスト開始時と終了時にログを出力する.
 */
public class TestWatchMan extends TestWatcher {
    private static final Logger log = LoggerFactory.getLogger(TestWatchMan.class);
    private static final String LOG_START_BAR = ":&&&&&&&&&&&&&&& {}";
    private static final String LOG_FAILED_BAR = ":############## {}";
    private static final String LOG_END_BAR = ":%%%%%%%%%%%% {}";
    private Description desc;

    @Override
    protected void starting(Description d) {
        log.info(LOG_START_BAR, d);
    }
    
    @Override
    protected void failed(Throwable e, Description d) {
        log.info(LOG_FAILED_BAR, d, e.getMessage());
        //e.printStackTrace();
    }

    @Override
    protected void finished(Description d) {
        log.info(LOG_END_BAR, d);
    }
}
