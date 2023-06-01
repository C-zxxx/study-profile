import java.util.ArrayList;

public class Status { //用于记录线程状态的数组
    private static ArrayList<Integer> stat = new ArrayList<>();//记录线程状态的数组
    private static ArrayList<String> thr = new ArrayList<>();//记录线程类型的数组（读者或写者）

    public static void initialize(int n){ //根据线程总数初始化stat数组每个元素为0
        for (int i = 1; i <= n ; i++){
            stat.add(0);
        }
    }

    public static void ini_type(String s){ //初始化线程类型（读者或写者）
        thr.add(s);
    }

    public static boolean check_reader(int n){ //检查从第一个创建的线程到当前线程前的读者线程之中，有没有正在读的线程
        int flag = 0;
        for (int i = 0;i <= (n-2) ; i++){
            if (thr.get(i) == "R"){
                if (stat.get(i) == 2){
                    flag = 1;
                    break;
                }
            }
        }
        if (flag == 1){
            return true; //可以开始读
        }else {
            return false;//不可以开始读
        }
    }

    public static void modify_apply(int n){ //修改状态为‘已申请读写’
        stat.set(n-1,1);
    }

    public static void modify_start(int n){ //修改状态为‘已开始读写’
        stat.set(n-1,2);
    }

    public static void modify_finish(int n){ //修改状态为‘已完成’
        stat.set(n-1,3);
    }

    public static boolean check(int n){//检查从第一个创建的线程到当前线程前一个创建的的线程之中，有没有已申请读或者写。但还没有开始读或写的线程
        int flag = 0;
        for (int i = 0; i <= (n-2); i++){
            if (stat.get(i) == 1){ //已申请读或写，但还没有开始读或写
                flag = 1;
                break;
            }
        }
        if (flag == 1){
            return false;//不能执行wsem--
        }else {
            return true;//可以执行wsem--
        }
    }

    public static boolean all_finished(){  //判断所有线程是否已完成
        int flag = 0;
        for (int i = 0 ; i <= (stat.size() - 1); i ++){
            if (stat.get(i) != 3){ //存在未完成的线程
                flag = 1;
                break;
            }
        }
        if (flag == 0){
            return true;  //所有线程已完成
        }else {
            return false; //有线程还没完成
        }
    }



}





















