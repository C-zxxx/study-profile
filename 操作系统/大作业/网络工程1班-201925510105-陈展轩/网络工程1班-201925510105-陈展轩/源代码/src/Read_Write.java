
import java.util.Scanner;


public class Read_Write { //主程序
    public static void main(String[] args) {
        int i,n,j;
        String type = ""; //读者或写者的标志
        int time_last; //读写持续时间
        int timebase = 3; //申请读写时间
        Scanner input = new Scanner(System.in);
        System.out.println("请输入读者与写者总数：");
        j = input.nextInt();
        Status.initialize(j);//初始化线程状态
        ClockThread clk = new ClockThread(0); //初始化当前时间为0
        clk.start();//开始时钟线程
        for (i = 1 ; i <= j ; i ++){
            n = (int)(Math.random()*2);//获得随机数0或1
            if (n == 0){
                type = "R";
            }else if (n == 1){
                type = "W";
            }
            time_last = 1 + (int)(Math.random()*5);//获取1-5之间的随机整数
            (new MultiThread(i,type,timebase,time_last)).start();//开始运行读者/写者程序
            Status.ini_type(type); // 初始化线程的类型（读者或写者）
            //输出该线程的信息
            System.out.println("线程" + i + "已创建，角色为" + type + "，申请读写时间为" + timebase + "，读写持续时间为" + time_last);
            timebase += 1 + (int)(Math.random()*3);//线程的申请时间增加量为一个随机数，随机数为1-3之间的整数
        }

    }
}
