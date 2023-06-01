public class ClockThread  extends Thread { // 时钟线程，用于记录时间

    private static int curtime; //当前时间

    public ClockThread(int curtime){
        this.curtime = curtime;
    }

    @Override
    public void run(){
        while (true){
            try{
                Thread.sleep(1000);//暂停执行1s
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            curtime++;//时间增加1
            System.out.println("CLK" + curtime);
            if (Status.all_finished() == true){ //判断所有线程是否已完成
                System.out.println("所有线程已完成！");
                break;
            }

        }
    }

    public static int getcurtime(){ //获取curtime的值
        return curtime;
    }


}
























