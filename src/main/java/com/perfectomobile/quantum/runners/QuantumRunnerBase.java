package com.perfectomobile.quantum.runners;

import static com.perfectomobile.quantum.utils.ConfigurationUtils.getBaseBundle;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;
import com.perfecto.reportium.WebDriverProvider;
import com.perfectomobile.quantum.listeners.QuantumReportiumListener;
import com.qmetry.qaf.automation.core.AutomationError;
import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.ui.WebDriverTestCase;

@Listeners(QuantumReportiumListener.class)
public abstract class QuantumRunnerBase extends WebDriverTestCase implements WebDriverProvider {

	@Override
    public WebDriver getWebDriver() {
        final String repeat = "driver.retry.times";
        final String wait = "driver.retry.wait.sec";

        Callable<WebDriver> callable = () -> getDriver();

        Retryer<WebDriver> retryer = RetryerBuilder.<WebDriver>newBuilder()
                .retryIfResult(Predicates.isNull())
                .retryIfExceptionOfType(AutomationError.class)
                .retryIfRuntimeException()
                .withStopStrategy(StopStrategies.stopAfterAttempt(getBaseBundle().getInt(repeat, ConfigurationManager.getBundle().getInt(repeat, 3))))
                .withWaitStrategy(WaitStrategies.fixedWait(getBaseBundle().getLong(wait, ConfigurationManager.getBundle().getLong(wait, 15)), TimeUnit.SECONDS))
                .build();
        try {
            return retryer.call(callable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
