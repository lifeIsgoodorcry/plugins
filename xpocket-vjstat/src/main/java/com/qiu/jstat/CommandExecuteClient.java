package com.qiu.jstat;

import com.perfma.xlab.xpocket.spi.XPocketPlugin;
import com.perfma.xlab.xpocket.spi.command.AbstractXPocketCommand;
import com.perfma.xlab.xpocket.spi.command.CommandInfo;
import com.perfma.xlab.xpocket.spi.process.XPocketProcess;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import static com.qiu.jstat.JmxcliPlugin.PATH;


@CommandInfo(name = "use", usage = "file ",index = 2)
public class CommandExecuteClient extends AbstractXPocketCommand {


    private JmxcliPlugin plugin;

    @Override
    public boolean isPiped() {
        return false;
    }

    @Override
    public void init(XPocketPlugin plugin) {
        this.plugin = (JmxcliPlugin) plugin;
    }

    /**
     * 必须attach进去  命令可以修改 成 run 进程号 gcutil 5
     * @param process
     * @throws Throwable
     */
    @Override
    public void invoke(XPocketProcess process) throws Throwable {
        String[] args = process.getArgs();
        if(args.length<0){
            return;
        }
        /**
         * 初始化的时候进行解压jar包，找到对应的脚本路径。
         * invoke的时候，可以写具体的路径
         */
        String[] execArgs = createArgs(args);
      try{
          String result = run(execArgs);
          process.output(result);
      }catch (Exception e){
           process.output(e.toString());
      }
    }


    private String[] createArgs(String[] args) {
        String[] execArgs = new String[args.length + 1];
        execArgs[0] = "sh";
        execArgs[1] = PATH + "vjmxcli";
        execArgs[2] = " - "+ args[0];
        for (int i=1;i<args.length-1;i++) {
            execArgs[i+2]=args[i];
        }
        return execArgs;
    }

    public static String run(String... cmds) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(cmds);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        StringWriter sw = new StringWriter();
        char[] chars = new char[1024];
        try (Reader r = new InputStreamReader(process.getInputStream())) {
            for (int len; (len = r.read(chars)) > 0; ) {
                sw.write(chars, 0, len);
            }
        }
        return sw.toString();
    }


}
