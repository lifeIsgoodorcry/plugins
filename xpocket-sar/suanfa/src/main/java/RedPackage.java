import java.util.ArrayList;
import java.util.List;

import static javax.management.Query.TIMES;

public class RedPackage {


    int MINMONEY = 1;

    int MAXMONEY = 20;


    public static void main(String[] args) {
        RedPackage redPackage = new RedPackage();

        List<Integer> list = redPackage.splitRedPackets(100, 10);
        list.stream().forEach(s-> System.out.println(s));
    }

    /**
     * 校验下次产生红包的时候，是否满足分配的条件
     *
     * @param money 剩余钱数
     * @param count 剩余红包数
     * @return
     */
    private boolean isRight(int money, int count) {
        double avg = money / count;
        //小于最小金额
        if (avg < MINMONEY) { //说明剩下的红包数分配的金额 会不合法
            return false;
            //大于最大金额
        } else if (avg > MAXMONEY) {
            return false;
        }
        return true;
    }


    private int randomRedPacket(int money, int minS, int maxS, int count) {
        //若是只有一个，直接返回红包
        if (count == 1) {
            return money;
        }
        //若是最小金额红包 == 最大金额红包， 直接返回最小金额红包
        if (minS == maxS) {
            return minS;
        }
        //校验 最大值 max 要是比money 金额高的话？ 去 money 金额
        int max = maxS > money ? money : maxS;

        //随机一个红包 = 随机一个数* (金额-最小)+最小
        int one = ((int) Math.rint(Math.random() * (max - minS) + minS));
        //剩下的金额
        int moneyOther = money - one;
        //校验这种随机方案是否可行，不合法的话，就要重新分配方案
        if (isRight(moneyOther, count - 1)) {
            return one;
        } else {
            //重新分配
            double avg = moneyOther / (count - 1); //根据剩下红包的金额平均值，判断本次发红包金额过大导致的，还是过少导致的
            //本次红包过大，导致下次的红包过小；如果红包过大，下次就随机一个小值到本次红包金额的一个红包
            if (avg < MINMONEY) {
                //递归调用，修改红包最大金额，说明本次的红包金额大导致后面红包不足，重新分配的时候，把分的金额作为下次分配的上限。
                return randomRedPacket(money, minS, one, count);
            } else if (avg > MAXMONEY) {
                //递归调用，修改红包最小金额
                return randomRedPacket(money, one, maxS, count);
            }
        }
        return one;
    }


    /**
     * 二倍均值解决了暴力分配的问题，但缺少了大红包的惊喜.大家的红包数基本都差不多
     * @param money
     * @param count
     * @return
     */
    public List<Integer> splitRedPackets(int money, int count) {
        //红包 合法性校验
        if (!isRight(money, count)) {
            return null;
        }
        //红包列表
        List<Integer> list = new ArrayList<Integer>();
        //每个红包最大的金额为平均金额的2倍  就是平均值的2倍之内为最大值
        int max = (int) (money * TIMES / count);

        max = max > MAXMONEY ? MAXMONEY : max;

        //分配红包
        for (int i = 0; i < count; i++) {
            int one = randomRedPacket(money, MINMONEY, max, count - i);
            list.add(one);
            money -= one;
        }
        return list;
    }

}
