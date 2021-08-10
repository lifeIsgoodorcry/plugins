package com.qiu.jstat;

import com.perfma.xlab.xpocket.spi.command.AbstractXPocketCommand;
import com.perfma.xlab.xpocket.spi.command.CommandInfo;
import com.perfma.xlab.xpocket.spi.process.XPocketProcess;


@CommandInfo(name = "analyze", usage = "use \"analyze <GcFileAbsolutePath>\" to analyze gc file ")
public class CommandExecuteClient extends AbstractXPocketCommand {

    @Override
    public void invoke(XPocketProcess process) throws Throwable {

    }
}
