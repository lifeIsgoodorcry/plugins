package com.qiu.sar;

import com.perfma.xlab.xpocket.spi.command.AbstractXPocketCommand;
import com.perfma.xlab.xpocket.spi.command.CommandInfo;
import com.perfma.xlab.xpocket.spi.command.XPocketProcessTemplate;
import com.perfma.xlab.xpocket.spi.process.XPocketProcess;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;


@CommandInfo(name = "sar", usage = "all primary sar commmand can be used")
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
        while (true){
            String line = reader.readLine();
            if(line==null){
                return;
            }
            process.output(line);
        }
    }
}
