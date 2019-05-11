package com.gxh.rabbitmq.testextends;

public class Son extends Father {
    @Override
    public void run() {
        super.run();
      //  System.out.println("子类中强化跑1");
    }

    public void runSon(){
        super.run();
    }

    public static void main(String[] args) {
        Son son = new Son(){
            @Override
            public void run() {
               // super.run();
                System.out.println("子类中强化跑");
            }

            @Override
            public void runSon() {
                System.out.println("zhe ye shi ji cheng");
            }
        };
        son.run();
        son.runSon();
    }
}


