package com.qiu.sar;

import com.perfma.xlab.xpocket.spi.command.AbstractXPocketCommand;
import com.perfma.xlab.xpocket.spi.command.CommandInfo;
import com.perfma.xlab.xpocket.spi.process.XPocketProcess;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 怀疑CPU存在瓶颈，可用 sar -u 和 sar -q 等来查看
 *
 * 怀疑内存存在瓶颈，可用 sar -B、sar -r 和 sar -W 等来查看
 *
 * 怀疑I/O存在瓶颈，可用 sar -b、sar -u 和 sar -d 等来查看
 */
@CommandInfo(name = "sar", usage = "all primary sar commmand can be used",index = 1)
@CommandInfo(name = "sar -h", usage = "you can use \"sar -h\" to see the detailed usage of the command ",index = 2)
@CommandInfo(name = "sar -u",usage = "CPU problem，use \"sar -u\" 、 \"sar -q\" to see the detailed " )
@CommandInfo(name = "sar -B",usage = "MEMORY problem，use \"sar -B\" 、 \"sar -r \"、 \"sar -W \" to see the detailed " )
@CommandInfo(name = "sar -b",usage = "I/O problem，use \"sar -b\" 、 \"sar -u \"、 \"sar -d \" to see the detailed " )
public class CommandExecuteClient extends AbstractXPocketCommand {

    /**
     * usage-tips=plugins only used on linux, if you want to user sar, please download sar package
     * @param process
     * @throws Throwable
     */
    @Override
    public void invoke(XPocketProcess process) throws Throwable {
        String cmd = process.getCmd();
        String[] args = process.getArgs();
        String[] targetArgs=new String[args.length+1];
        targetArgs[0]=cmd;
        if(args!=null && args.length>=1){
            System.arraycopy(args, 0, targetArgs, 1, args.length);
        }
        Process exec = Runtime.getRuntime().exec(targetArgs);
        InputStream inputStream = exec.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
        String result=null;
        while (true){
            String line = reader.readLine();
            if(line==null){
                break;
            }
            result=line;
            process.output(line);
        }
        if(result==null){
            process.output("Command not supported...");
        }
        process.end();
    }
}
