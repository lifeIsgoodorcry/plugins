package com;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;

public class test {
    public static void main(String[] args) throws InterruptedException {
        String s = monitorMemoryPool();
        System.out.println(s);
        Thread.sleep(12324235);
    }




    public static String monitorMemoryPool() {
        StringBuilder sb = new StringBuilder("MemoryPool:");

        List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();

        for (MemoryPoolMXBean p : pools) {
            sb.append("[" + p.getName() + ":");
            MemoryUsage u = p.getUsage();
            sb.append(" Used=" + (u.getUsed()));
            sb.append(" Committed=" + (u.getCommitted()));
            sb.append("]");
        }
        return sb.toString();

    }

}
