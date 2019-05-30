/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meteplus.utils;

import java.util.ArrayList;

/**
 *
 * @author HuangMing
 */
public class QuickSort {

    public void quickSort(ArrayList arrays){
        subQuickSort(arrays, 0, arrays.size() - 1);
    }

    private void subQuickSort(ArrayList arrays, int start, int end){
        if (start >= end){
            return;
        }
        int middleIndex = subQuickSortCore(arrays, start, end);
        subQuickSort(arrays, start, middleIndex - 1);
        subQuickSort(arrays, middleIndex + 1, end);
    }

    private int subQuickSortCore(ArrayList arrays, int start, int end){
        
        int middleValue = (int)arrays.get(start); 
        while (start < end){
            while ((int)arrays.get(end) >= middleValue && start < end){
                end--;
            }
            arrays.set(start, arrays.get(end));
            while ((int)arrays.get(start) <= middleValue && start < end){
                start++;
            }
            arrays.set(end, arrays.get(start));
        }
        arrays.set(start, middleValue);
        return start;
    }

}
