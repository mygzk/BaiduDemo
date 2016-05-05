package com.example.demo;

/**
 * Created by guozhk on 16-5-4.
 */
public class TestClass {
    public static void main(String[] args){
        String s="104.856211;104.856395;104.856467;104.856593;104.856669;104.856768;104.856813;104.856836;104.856809;104.856795;104.856791;104.856764;104.856755;104.856741;104.856732;104.856692;104.856692;104.856647;104.856616;104.856584";
        String[] res = s.split("\\;");
        System.out.println(res);
    }
}
