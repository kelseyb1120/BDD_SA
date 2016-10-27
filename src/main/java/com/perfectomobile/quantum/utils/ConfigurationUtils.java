package com.perfectomobile.quantum.utils;

import com.perfectomobile.intellij.connector.ConnectorConfiguration;
import com.perfectomobile.intellij.connector.impl.client.ClientSideLocalFileSystemConnector;
import com.perfectomobile.intellij.connector.impl.client.ProcessOutputLogAdapter;
import com.perfectomobile.selenium.util.EclipseConnector;
import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.core.TestBaseProvider;
import com.qmetry.qaf.automation.util.PropertyUtil;
import org.json.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

/**
 * Created by mitchellw on 9/27/2016.
 */
public class ConfigurationUtils {

    public static PropertyUtil getBaseBundle() {
        return TestBaseProvider.instance().get().getContext();
    }

    public static void setExecutionIdCapability() {
        try {
            PropertyUtil properties = ConfigurationManager.getBundle();
            String executionId = null;
            if ("intellij".equalsIgnoreCase(properties.getPropertyValue("driver.pluginType"))) {
                ClientSideLocalFileSystemConnector intellijConnector = new ClientSideLocalFileSystemConnector(new ProcessOutputLogAdapter(System.err, System.out, System.out, System.out));
                ConnectorConfiguration connectorConfiguration = intellijConnector.getConnectorConfiguration();
                if (connectorConfiguration != null && connectorConfiguration.getHost() != null) {
                    System.out.println(connectorConfiguration.toString());
                    executionId = connectorConfiguration.getExecutionId();
                }
            } else if ("eclipse".equalsIgnoreCase(properties.getPropertyValue("driver.pluginType"))) {
                EclipseConnector connector;
                try {
                    connector = new EclipseConnector();
                    if (connector.getHost() != null) {
                        executionId = connector.getExecutionId();
                    }
                } catch (Exception e) {
                    System.err.println("Eclipse Connector Plugin socket not found");
                }
            }

            if (executionId != null) {
                DesiredCapabilities dcaps = new DesiredCapabilities();
                dcaps.setCapability(EclipseConnector.ECLIPSE_EXECUTION_ID, executionId);
                addAdditionalCapabilities(dcaps);
            }
        } catch (Exception e) {
            System.err.println("Could not connect to local device");
        }
    }

    public static void setMavenCapabilities() {
        PropertyUtil properties = ConfigurationManager.getBundle();
        String caps = properties.getProperty("driver.mavenCapabilities") + "";
        if (caps == null || caps.indexOf("=") < 0)
            return;
        DesiredCapabilities dcaps = new DesiredCapabilities();
        for (String capKeyValue : caps.split(","))
            if (capKeyValue != null && capKeyValue.length() > 3 && capKeyValue.indexOf("=") > 0)
                dcaps.setCapability(capKeyValue.split("=")[0], capKeyValue.split("=")[1]);
        addAdditionalCapabilities(dcaps);
    }

    public static void addAdditionalCapabilities(DesiredCapabilities caps) {
        JSONObject addCapsJson = new JSONObject();
        PropertyUtil properties = ConfigurationManager.getBundle();
        if (properties.getProperty("driver.additional.capabilities") != null)
            addCapsJson = new JSONObject(properties.getProperty("driver.additional.capabilities").toString());

        for (Map.Entry<String, ?> cap : caps.asMap().entrySet())
            addCapsJson.put(cap.getKey(), cap.getValue());
        properties.setProperty("driver.additional.capabilities", addCapsJson.toString());
    }

}
