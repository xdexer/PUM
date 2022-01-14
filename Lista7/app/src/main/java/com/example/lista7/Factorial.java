package com.example.lista7;

import java.math.BigInteger;

public class Factorial extends Thread{
    int start, end;
    BigInteger res;

    Factorial(int start, int end){
        this.start = start;
        this.end = end;
    }
    public void run(){
        try{
            res = new BigInteger(String.valueOf(1));
            for(int i = start; i <= end; i++){
                res = res.multiply(BigInteger.valueOf(i));
            }
        }catch (Exception e){
            System.out.println("Exception caught: " + e.toString());
        }
    }

    public BigInteger getRes(){
        return res;
    }
}
