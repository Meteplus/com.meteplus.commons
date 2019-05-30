/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meteplus.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author HuangMing
 */
public class Utils {
    
    public static int ONLY_NUMERS=1;
    public static int ONLY_CHARS=2;
    public static int CHARS_AND_NUMBERS=3;
/** 
     * 手机号码:  
     * 13[0-9], 14[5,7], 15[0, 1, 2, 3, 5, 6, 7, 8, 9], 17[6, 7, 8], 18[0-9], 170[0-9] 
     * 移动号段: 134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,187,188,147,178,1705 
     * 联通号段: 130,131,132,155,156,185,186,145,176,1709 
     * 电信号段: 133,153,180,181,189,177,1700 
     */  //(\\+86|)?(1\\d{10})
    private static String  MOBILE = "(?<!\\d)1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}(?!\\d)";  
    /** 
     * 中国移动：China Mobile 
     * 134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,187,188,147,178,1705 
     */  
    private static String  CM = "((?<!\\d)1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}(?!\\d))|((?<!\\d)1705\\d{7}(?!\\d))";  
    /** 
     * 中国联通：China Unicom 
     * 130,131,132,155,156,185,186,145,176,1709 
     */  
    private static String CU = "((?<!\\d)1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}(?!\\d))|((?<!\\d)1709\\d{7}(?!\\d))";  
    /** 
     * 中国电信：China Telecom 
     * 133,153,180,181,189,177,1700 
     */  
    private static String  CT = "((?<!\\d)1(33|53|77|8[019])\\d{8}(?!\\d))|((?<!\\d)1700\\d{7}(?!\\d))";  
    
    //System.out.println(getMobile(note,"("+MOBILE+")"+"|("+CM+")"+"|("+CU+")"+"|("+CT+")"));    
    public static String utc2Local(String utcTime, String utcTimePatten,String localTimePatten) {
        SimpleDateFormat  utcFormater = new SimpleDateFormat(utcTimePatten);
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUTCDate.getTime());
        return localTime;
    }

    
    public static String getRandomString(int length,int strMode) {   
        
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        if(strMode==ONLY_CHARS){
            base="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }else if(strMode==ONLY_NUMERS){
            base="0123456789";
        }
        
        Random random = new Random();   
        StringBuilder sb = new StringBuilder();   
        for (int i = 0; i < length; i++) {   
            int number = random.nextInt(base.length());   
            sb.append(base.charAt(number));   
        }   
        return sb.toString();   
        
    }      
   
    
    public static byte[] toByteArrayFromFile(String imageFile) throws Exception
    {
            InputStream is = null;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] bytes=null;
            try
            {
                    is = new FileInputStream(imageFile);
                    byte[] b = new byte[1024];
                    int n;
                    while ((n = is.read(b)) != -1)
                    {
                            out.write(b, 0, n);
                    }// end while
                    bytes=out.toByteArray();

            } catch (Exception e)
            {
                    throw new Exception("System error,SendTimingMms.getBytesFromFile", e);
            } finally
            {

                    if (is != null)
                    {
                            try
                            {
                                    is.close();
                            } catch (Exception e)
                            {}// end try
                    }// end if
                    if(out!=null){
                        try
                        {
                                out.close();
                        } catch (Exception e)
                        {}// end try
                    }
            }// end try
            return bytes;
    }    
    
    

    
/**
     * MD5加密
     * @param str 内容       
     * @param charset 编码方式
	 * @throws Exception 
     */
    @SuppressWarnings("unused")
    public static  String MD5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuilder sb = new StringBuilder(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

	/**
     * base64编码
     * @param str 内容       
     * @param charset 编码方式
	 * @throws UnsupportedEncodingException 
     */
    public static String base64(String str, String charset) throws UnsupportedEncodingException{
            String encoded = base64Encode(str.getBytes(charset));
            return encoded;    
    }	

    @SuppressWarnings("unused")
    public static String urlEncoder(String str, String charset) throws UnsupportedEncodingException{
            String result = URLEncoder.encode(str, charset);
            return result;
    }
	
	/**
     * 电商Sign签名生成
     * @param content 内容   
     * @param keyValue Appkey  
     * @param charset 编码方式
	 * @throws UnsupportedEncodingException ,Exception
	 * @return DataSign签名
     */
    @SuppressWarnings("unused")
    public static String encrypt (String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception
    {
            if (keyValue != null)
            {
                    return base64(MD5(content + keyValue, charset), charset);
            }
            return base64(MD5(content, charset), charset);
    }    
        
        

    public static final char[] base64EncodeChars = new char[] { 
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 
        'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 
        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 
        'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 
        'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 
        'w', 'x', 'y', 'z', '0', '1', '2', '3', 
        '4', '5', '6', '7', '8', '9', '+', '/' }; 
	
    public static String base64Encode(byte[] data) { 
        StringBuilder sb = new StringBuilder(); 
        int len = data.length; 
        int i = 0; 
        int b1, b2, b3; 
        while (i < len) { 
            b1 = data[i++] & 0xff; 
            if (i == len) 
            { 
                sb.append(base64EncodeChars[b1 >>> 2]); 
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]); 
                sb.append("=="); 
                break; 
            } 
            b2 = data[i++] & 0xff; 
            if (i == len) 
            { 
                sb.append(base64EncodeChars[b1 >>> 2]); 
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]); 
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]); 
                sb.append("="); 
                break; 
            } 
            b3 = data[i++] & 0xff; 
            sb.append(base64EncodeChars[b1 >>> 2]); 
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]); 
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]); 
            sb.append(base64EncodeChars[b3 & 0x3f]); 
        } 
        return sb.toString(); 
    }            
    
    
    
    //提取字符串中的手机号码  
    public static ArrayList<String> getMobile(String str) {  
        
        ArrayList<String> group=new ArrayList<>();
        if(str==null){
            return group;
        }
        //StringBuilder sb = new StringBuilder();  
        Pattern p = Pattern.compile("("+MOBILE+")"+"|("+CM+")"+"|("+CU+")"+"|("+CT+")");  
        if(p!=null){
            Matcher m = p.matcher(str);  
            if(m!=null){
                while (m.find()) {  
                    String mobile=m.group(1);
                    if(mobile!=null){
                        group.add(mobile);
                    }
                }  
            }
        }
        /* 
        * 不加"()"也能将手机号码输出 添加"()"是为了筛选数据添加上去的， 
        * 第一对"()"是为了获取字符串"+86"，代码是System.out.println(m.group(1));， 
        * 第二对"()"是获取11位纯数字电话号码， 本次的输出的手机号码中包含了"+86"，如果只要11位数字号码， 
        * 可将代码改为System.out.println(m.group(2)); 
        */  
        //System.out.println(m.groupCount());// 该行代码是输出有几对"()"，即捕获组个数，本次输出结果是2，因为有两对"()"  
        return group;  
    }      



    public static String addressSensor(String rawAddr){

        String str=rawAddr;
        
        try{
            
            String[] keys=new String[]{"省","市","区","县","州","乡","镇","村","路","街","栋","楼","座","大厦","单元","室","层","广场","局","处","部","大道","集团","有限公司","校","园","厂","中心","店","站","东","西","南","北","左","右","旁","侧","对面"};
            ArrayList<Integer> indexes=new ArrayList<>();

            for (String key : keys) {
                int index = str.indexOf(key);
                while (index>0) {
                    indexes.add(index);
                    index = str.indexOf(key, index+1);
                }
            }

            QuickSort quicksort = new QuickSort();        
            quicksort.quickSort(indexes);


            StringBuilder bufStr=new StringBuilder(str);
            for(int i=0;i<indexes.size();i++){

                  if(i==0){
                      if(indexes.get(i)-2>=0){
                           bufStr.replace(indexes.get(i)-2, indexes.get(i), "**");
                      }else if(indexes.get(i)-1>=0){
                           bufStr.replace(indexes.get(i)-1, indexes.get(i), "*");
                      }
                  }

                  if(i>0){
                      int min=indexes.get(i)-indexes.get(i-1)<5?indexes.get(i)-indexes.get(i-1):5;
                      for(int j=0;j<min;j++){
                          bufStr.setCharAt(indexes.get(i)-min+j, '*');
                      }                          
                  }

            }

            str=bufStr.toString();


            String[] preKeys=new String[]{"www","QQ","微信","旺旺","淘宝","ID"};
            for (String preKey : preKeys) {
                int index = str.indexOf(preKey);
                if (index>0) {
                    String head = str.substring(0, index + preKey.length()).trim();
                    String tail = str.substring(index + preKey.length()).trim();
                    if(tail.length()>=4){
                        tail="***"+tail.substring(4);
                    }else if(tail.length()>=3){
                        tail="***"+tail.substring(3);
                    }else if(tail.length()>=2){
                        tail="***"+tail.substring(2);
                    }else if(tail.length()>=1){
                        tail="***"+tail.substring(1);
                    }
                    str=head+tail;
                }
            }        
            
        }catch(Exception e){
            str="敏感信息过滤出错:"+e.getMessage();
            e.printStackTrace();
        }
        return str;
    
    }
    
    /**
     * 删除所有的HTML标签
     *
     * @param source 需要进行除HTML的文本
     * @return
     */
    public static String deleteAllHTMLTag(String source) {

        if(source == null) {
             return "";
        }

        String s = source;
        /** 删除普通标签  */
        s = s.replaceAll("<(S*?)[^>]*>.*?|<.*? />", "");
        /** 删除转义字符 */
        s = s.replaceAll("&.{2,6}?;", "");
        return s;
    }
    
    
    
    

}
