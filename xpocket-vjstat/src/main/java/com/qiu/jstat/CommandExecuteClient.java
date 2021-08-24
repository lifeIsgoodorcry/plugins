package com.qiu.jstat;

import com.perfma.xlab.xpocket.spi.XPocketPlugin;
import com.perfma.xlab.xpocket.spi.command.AbstractXPocketCommand;
import com.perfma.xlab.xpocket.spi.command.CommandInfo;
import com.perfma.xlab.xpocket.spi.process.XPocketProcess;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;


@CommandInfo(name = "use", usage = "file ")
public class CommandExecuteClient extends AbstractXPocketCommand {



    private static final String USER_HOME = System.getProperty("user.home");


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

        String[] execCommand=new String[args.length+1];

        /**
         * 初始化的时候进行解压jar包，找到对应的脚本路径。
         * invoke的时候，可以写具体的路径
         */
        //因为只有一个jar包，所以，需要解压下jar包，获取jar的路径，执行文件。
        String path = USER_HOME + File.separator + ".xpocket"
                + File.separator + ".vjmxcli" + File.separator;

      String runShell="sh "+path+"vjmxcli.sh";

      process.output("脚本路径"+runShell); // home/admin/.xpocket/.vjmxcli/vjmxcli.sh
      execCommand[0]=runShell;
      execCommand[1]="- "+args[0];
      for (int i=1;i<args.length;i++) {
           execCommand[i+1]=args[i];
      }
        Process exec = Runtime.getRuntime().exec(execCommand);
        InputStream inputStream = exec.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
        String line=null;
        while (true){
            String readLine = reader.readLine();
            if(readLine==null){
                break;
            }
            line=readLine;
            process.output(readLine);
        }
        if(line==null){
            process.output("param is error");
          }
    }

}
