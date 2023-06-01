import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiThread extends Thread {  //读写线程
    static int readcount = 0; //表示正在进行读操作的线程数量
    static int x = 1, wsem = 1; //x为修改readcount的互斥信号量  wsem为读写互斥信号量
    private int No;//线程编号
    private String type;// 线程类型 表示读者或写者
    private int time_apply; // 申请读写时间
    private int time_last; //读写持续时间
    static Lock lock = new ReentrantLock(); //定义一个互斥锁，用于实现原子操作

    public MultiThread(int No,String type,int time_apply,int time_last){  //定义线程
        this.No = No;
        this.type = type;
        this.time_apply = time_apply;
        this.time_last = time_last;
    }

    @Override
    public void run(){
        if (type.equals("R")){//读者
            while (true){
                try {
                    sleep(00);//暂停0.5s
                }catch (InterruptedException ex){
                    Logger.getLogger(MultiThread.class.getName()).log(Level.SEVERE,null,ex);//日志处理
                }
                if (time_apply == ClockThread.getcurtime()){ //当前时间为申请读写时间
                    Status.modify_apply(No);//修改状态为“已申请读写”
                    System.out.println("线程" + No + ":" + type + "申请读写！" + "当前时间" + ClockThread.getcurtime());
                    break;
                }
            }
            semWaitx();//readcount的互斥信号量的wait操作
            while (true){
                if (getx() >= 0){ //x的值大于0
                    break;
                }
            }
            readcount ++;  //正在进行读操作的线程数量加1
            semSignalx(); //readcount的互斥信号量的sigbal操作
            if (readcount == 1){
                semWaitwsem();//wsem的wait操作
            }else {
                while (true){
                    if (Status.check_reader(No) == true ){  //检查从第一个创建的线程到当前线程的前一个创建的线程有没有正在读的线程
                        break;
                    }
                    try {
                        sleep(500);//暂停0.5s
                    }catch (InterruptedException ex){
                        Logger.getLogger(MultiThread.class.getName()).log(Level.SEVERE,null,ex);//日志处理
                    }
                }
            }
            Status.modify_start(No);//修改状态为“已开始读写”
            System.out.println("线程" + No + ":" + type + "开始读写！" + "当前时间" + ClockThread.getcurtime());
            while (true){
                try {
                    sleep(1000);//暂停1s
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                time_last -- ; //读写持续时间减1
                if (time_last == 0){
                    System.out.println("线程" + No + ":" + type + "已完成！" + "当前时间" + ClockThread.getcurtime());
                    Status.modify_finish(No);//修改状态为“已完成”
                    break;
                }
            }
            semWaitx();//readcount的互斥信号量的wait操作
            while (true){
                if (getx() >= 0){ //x的值大于0
                    break;
                }
            }
            readcount --;  //正在进行读操作的线程数量减1
            if (readcount == 0){
                semSignalwsem();//wsem的signal操作
            }
            semSignalx(); //readcount的互斥信号量的signal操作
        }else if (type.equals("W")){ // 写者
            while (true){
                try {
                    sleep(00);//暂停0.5s
                }catch (InterruptedException ex){
                    Logger.getLogger(MultiThread.class.getName()).log(Level.SEVERE,null,ex);//日志处理
                }
                if (time_apply == ClockThread.getcurtime()){ //当前时间为申请读写时间
                    Status.modify_apply(No);//修改状态为“已申请读写”
                    System.out.println("线程" + No + ":" + type + "申请读写！" + "当前时间" + ClockThread.getcurtime());
                    break;
                }
            }
            semWaitwsem();//wsem的wait操作
            Status.modify_start(No);//修改状态为“已开始读写”
            System.out.println("线程" + No + ":" + type + "开始读写！" + "当前时间" + ClockThread.getcurtime());
            while (true){
                try {
                    Thread.sleep(1000);//暂停1s
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                time_last -- ; //读写持续时间减1
                if (time_last == 0){
                    System.out.println("线程" + No + ":" + type + "已完成！" + "当前时间" + ClockThread.getcurtime());
                    Status.modify_finish(No);//修改状态为“已完成”
                    semSignalwsem();//wsem的signal操作
                    break;
                }
            }

        }
    }

    public int getx(){
        return x;
    }

    public int getwsem(){
        return wsem;
    }

    public void semWaitx(){  //readcount的互斥信号量的wait操作
        x--;
    }

    public void semWaitwsem(){  //wsem的wait操作
        lock.lock();//获得锁
        while (true){
            try {
                sleep(100);//暂停0.1s
            }catch (InterruptedException ex){
                Logger.getLogger(MultiThread.class.getName()).log(Level.SEVERE,null,ex);//日志处理
            }
            if (getwsem() == 1){ // wsem为1
                if (Status.check(No) == false){ //检查从第一个创建的线程到当前线程的前一个创建的线程中，有没有已经申请读写，但还没有开始读写的进程
                    continue;
                }
                break;
            }
        }
        wsem--;
        lock.unlock();//释放锁
    }

    public void semSignalx(){  //readcount的互斥信号量的signal操作
        x++;
    }

    public void semSignalwsem(){  //wsem的互斥信号量的signal操作
        wsem++;
    }









}
















































