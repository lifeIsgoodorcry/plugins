package com.qiu.jstat;

import com.perfma.xlab.xpocket.spi.XPocketPlugin;
import com.perfma.xlab.xpocket.spi.command.AbstractXPocketCommand;
import com.perfma.xlab.xpocket.spi.command.CommandInfo;
import com.perfma.xlab.xpocket.spi.process.XPocketProcess;

import java.io.*;

import static com.qiu.jstat.JmxcliPlugin.PATH;


@CommandInfo(name = "gcutil",usage ="use:gcutil <pid> [<interval> [<count>]],eg:gcutil 1234 2",index = 2)
@CommandInfo(name = "help",  usage ="use:gcutil <pid> [<interval> [<count>]]. <interval>:how long once,if interval is null,default 1,<count>:how many times,if count is null,default 4"
        ,index = 1)
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
     *
     * @param process
     * @throws Throwable
     */
    @Override
    public void invoke(XPocketProcess process) throws Throwable {
        String cmd = process.getCmd();
        if("help".equals(cmd)){
            process.output("you can use gcutil <pid> <n>, <n> : interval if n is null ,default 1");
            process.end();
            return;
        }
        String[] args = process.getArgs();
        if(args.length==0){
            process.output("pid is empty,please check args");
            process.end();
            return;
        }
        /**
         * 初始化的时候进行解压jar包，找到对应的脚本路径。
         * invoke的时候，可以写具体的路径
         */
        String[] execArgs = createArgs(cmd,args);
      try{
           run(process,false, execArgs);
      }catch (Exception e){
           process.output(e.toString());
      }
      //杀死shell的进程,杀不死
        killShell(process);
      process.end();
    }

    private void killShell(XPocketProcess process) {
        try{
            String[] execArgs=new String[2];
            execArgs[0] = "sh";
            execArgs[1] = PATH + "killvjmxcli";
            run(process,true,execArgs);
        }catch (Exception e){
             process.output(e.toString());
        }
    }


    /**
     * Options — 选项，我们一般使用 -gcutil 查看gc情况
     *
     * vmid    — VM的进程号，即当前运行的java进程号
     *
     * interval– 间隔时间，单位为秒或者毫秒
     *
     * count   — 打印次数，如果缺省则打印无数次
     *
     * @param cmd
     * @param args
     * @return
     */
    private String[] createArgs(String cmd,String[] args) {
        // gcutil 123214 4  或者，数组长度固定6，补充默认值
        // sh aa 12324 gcutil 4 4  +4：sh+path+默认1,+cmd
         String[] execArgs=new String[6];
         execArgs[0] = "sh";
         execArgs[1] = PATH + "vjmxcli";
         execArgs[2] = " - "+ args[0];
         execArgs[3]=cmd;
         execArgs[4]= "1"; //默认间隔1s
         execArgs[5]="4"; //默认打印4次
        if(args.length>1){
            for (int i=1;i<args.length;i++) {
                execArgs[i+3]=args[i];
            }
        }
        return execArgs;
    }

    private  void run(XPocketProcess xprocess,boolean isKill,String... cmds) throws IOException {
        Process process = null;
        BufferedReader bufferedReader=null;
        int totalLine= 10;
        if(!isKill){
            totalLine=Integer.parseInt(cmds[cmds.length-1])+2;
        }
        try{
            ProcessBuilder pb = new ProcessBuilder(cmds);
            pb.redirectErrorStream(true);
            process= pb.start();
            InputStream inputStream = process.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int lineCount=0;
            while (true){
                String line = bufferedReader.readLine();
                if(line==null){
                    break;
                }
                if(line!=null){
                    if(lineCount>=totalLine){
                        break;
                    }
                    lineCount++;
                    if(lineCount>=2){
                        xprocess.output(line);
                    }
                }
            }
        }finally {
            if(bufferedReader!=null){
                bufferedReader.close();
            }
            if(process!=null && process.isAlive()){
                process.destroyForcibly();
            }
        }
    }

}
