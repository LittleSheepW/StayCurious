package com.ww.temp;

/**
 * @author zhaochun
 */
public class Student {
    private final String name = "张三";
    private final int entranceAge = 18;
    private String evaluate = "优秀";
    private int scores = 95;
    private Integer level = 5;

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        String tmp = "+";
        this.evaluate = evaluate + tmp;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        final int base = 10;
        System.out.println("base:" + base);
        this.scores = scores + base;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
