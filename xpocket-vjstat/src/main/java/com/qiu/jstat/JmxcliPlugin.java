package com.qiu.jstat;

import com.perfma.xlab.xpocket.spi.AbstractXPocketPlugin;
import com.perfma.xlab.xpocket.spi.process.XPocketProcess;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class JmxcliPlugin extends AbstractXPocketPlugin {


    private static final String USER_HOME = System.getProperty("user.home");

    public static final String PATH = USER_HOME + File.separator + ".xpocket"
            + File.separator + ".vjmxcli" + File.separator;

    private static final String[] files = {"vjmxcli-1.0.8.jar","vjmxcli","killvjmxcli"};

    @Override
    public void init(XPocketProcess process) {
        try {
            File file = new File(PATH);
            if(file.exists()){
                return;
            }
            file.mkdirs();
            for(String f : files) {
                InputStream is = JmxcliPlugin.class.getClassLoader().getResourceAsStream("lib/" + f);
                Path targetFile = new File(PATH + f).toPath();
                Files.copy(is, targetFile);
                is.close();
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

}
