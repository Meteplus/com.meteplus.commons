/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meteplus.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 
 * @author HuangMing
 */
public class UrlBuilder{
       
       private StringBuilder url;
       private StringBuilder query; 
       
       /**
        * 默认构造https
        */
       public UrlBuilder(){
           https();
       }
       
       public final UrlBuilder https(){
           url=new StringBuilder();
           url.append("https:/");
           return this;
       }
       
       public final UrlBuilder http(){
           url=new StringBuilder();
           url.append("http:/");
           return this;
       }
       
       public final UrlBuilder ws(){
           if(url==null){
               url=new StringBuilder();
           }
           url.append("ws:/");
           return this;
       }
       
//       public UrlBuilder host(String host){
//           appendSeg(host);
//           return this;
//       }
//       
       public UrlBuilder path(String seg){
           
           if(seg==null){
               return this;
           }
           
           int startPos=0;
           int lastPos=seg.length()-1;
           for(;startPos<seg.length();startPos++){
               if(seg.charAt(startPos)!='/'&&seg.charAt(startPos)!='\\'&&seg.charAt(startPos)!=' '){
                   break;
               }
           }
           for(;lastPos>=0;lastPos--){
               if(seg.charAt(startPos)!='/'&&seg.charAt(startPos)=='\\'&&seg.charAt(startPos)!=' '){
                   break;
               }
           }
           
           if(startPos<=lastPos){
               url.append("/");
               while(startPos<=lastPos){
                   url.append(seg.charAt(startPos));
               }               
           }
           
           return this;
           
       }
       
       public UrlBuilder query(String name,int value){
           if(name!=null){
                if(query==null){
                    query=new StringBuilder("?");
                }else{
                    query.append("&");
                }
                try{
                     query.append(URLEncoder.encode(name,"UTF-8")).append("=").append(value);
                }catch(UnsupportedEncodingException e){
                     e.printStackTrace();
                }
           }
           return this;
       }       
       
       public UrlBuilder query(String name,long value){
           if(name!=null){
                if(query==null){
                    query=new StringBuilder("?");
                }else{
                    query.append("&");
                }
                try{
                     query.append(URLEncoder.encode(name,"UTF-8")).append("=").append(value);
                }catch(UnsupportedEncodingException e){
                     e.printStackTrace();
                }
           }
           return this;
       }          
       
       public UrlBuilder query(String name,float value){
           if(name!=null){
                if(query==null){
                    query=new StringBuilder("?");
                }else{
                    query.append("&");
                }
                try{
                     query.append(URLEncoder.encode(name,"UTF-8")).append("=").append(value);
                }catch(UnsupportedEncodingException e){
                     e.printStackTrace();
                }
           }
           return this;
       }          
       
       public UrlBuilder query(String name,double value){
           if(name!=null){
                if(query==null){
                    query=new StringBuilder("?");
                }else{
                    query.append("&");
                }
                try{
                     query.append(URLEncoder.encode(name,"UTF-8")).append("=").append(value);
                }catch(UnsupportedEncodingException e){
                     e.printStackTrace();
                }
           }
           return this;
       }          
       
       public UrlBuilder query(String name,boolean value){
           if(name!=null){
                if(query==null){
                    query=new StringBuilder("?");
                }else{
                    query.append("&");
                }
                try{
                     query.append(URLEncoder.encode(name,"UTF-8")).append("=").append(value);
                }catch(UnsupportedEncodingException e){
                     e.printStackTrace();
                }
           }
           return this;
       }          
       
       public UrlBuilder query(String name,String value){
           if(name!=null&&value!=null){
                if(query==null){
                    query=new StringBuilder("?");
                }else{
                    query.append("&");
                }
                try{
                     query.append(URLEncoder.encode(name,"UTF-8")).append("=").append(URLEncoder.encode(value,"UTF-8"));
                }catch(UnsupportedEncodingException e){
                     e.printStackTrace();
                }
           }
           return this;
       }       
       
       private StringBuilder build(){
           if(query!=null){
               url.append(query);
           }
           return url;
       }
       
       @Override
       public String toString(){
           if(url!=null){
               return build().toString();
           }
           return null;
       }
       
   }