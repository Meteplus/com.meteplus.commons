/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meteplus.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author HuangMing
 */
public class ThreadPool {

 private static ThreadPool instance;
 private final ThreadPoolExecutor threadPoolExecutor;
 static{
     ThreadPool.initInstance();
 }
 public ThreadPool(final int poolSize){
    threadPoolExecutor=new ThreadPoolExecutor(poolSize,poolSize,0,TimeUnit.SECONDS,new LinkedBlockingQueue<>());    
 }
 
 public void assign(Runnable r){
    threadPoolExecutor.execute(r); 
 }

 private static void initInstance(){
    if(instance==null){
        instance=new ThreadPool(2*Runtime.getRuntime().availableProcessors()+1);
    }        
 }
 
 public static ThreadPool getIntance(){
     return instance;
 }

     
}
