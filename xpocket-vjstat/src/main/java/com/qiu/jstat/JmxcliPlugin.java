package com.qiu.jstat;

import com.perfma.xlab.xpocket.spi.AbstractXPocketPlugin;
import com.perfma.xlab.xpocket.spi.process.XPocketProcess;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class JmxcliPlugin extends AbstractXPocketPlugin {


    private static final String USER_HOME = System.getProperty("user.home");

    private static final String path = USER_HOME + File.separator + ".xpocket"
            + File.separator + ".vjmxcli" + File.separator;

    private static final String[] files = {"vjmxcli.jar","vjmxcli.sh"};

    @Override
    public void init(XPocketProcess process) {
        try {
            File file = new File(path+".vjmxcli");
            if(file.exists()) {
                return;
            } //说明已经拷贝过了
            process.output("输出文件目录:"+file.getPath());
            file.mkdirs();
            for(String f : files) {
                InputStream is = JmxcliPlugin.class.getClassLoader().getResourceAsStream(".vjmxcli/lib/" + f);
                Path targetFile = new File(path + f).toPath();
                Files.copy(is, targetFile);
                is.close();
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
}
