/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meteplus.utils;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;

/**
 *
 * @author HuangMing
 */
public class UIUtils {


    public static String readJSONString(File jsonFile){
                    
        if(jsonFile!=null&&jsonFile.getName().endsWith(".json")&&jsonFile.exists()){
            byte[] bytes=new byte[1024*1024];
            try{
                FileInputStream fis=new FileInputStream(jsonFile);
                int len=0;
                int totalLen=0;
                while((len=fis.read(bytes,totalLen,bytes.length-totalLen))>0){
                    totalLen+=len;
                }
                fis.close();
                return new String(bytes,"UTF-8");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }            
    
    
    public static String readJSONFromDir(File cdrDir){
                    
        if(cdrDir.exists()&&cdrDir.isDirectory()){
            File[] subfiles=cdrDir.listFiles();
            if(subfiles!=null){
                byte[] bytes=new byte[1024*1024];
                for (File file : subfiles) {
                    if(file.getName().endsWith(".json")){
                        try{
                            try (FileInputStream fis = new FileInputStream(file)) {
                                int len=0;
                                int totalLen=0;
                                while((len=fis.read(bytes,totalLen,bytes.length-totalLen))>0){
                                    totalLen+=len;
                                }
                            }
                            return new String(bytes,"UTF-8");
                        }catch(IOException e){
                            e.printStackTrace();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }        
        return null;
    }        
    
    public static void ExploreFile(String filePath) {
        try{
            Runtime.getRuntime().exec("explorer.exe /select," + filePath);
        }catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }    

    
    
    public static void makeTextEditorToLabel(JTextComponent field){
        field.setBorder(null); 
        field.setEditable(false); 
        field.setForeground(UIManager.getColor("Label.foreground"));
        field.setBackground(UIManager.getColor("Label.background"));
        field.setFont(UIManager.getFont("Label.font"));        
    }
    /*
     * 图片缩放,w，h为缩放的目标宽度和高度
     * src为源文件目录，dest为缩放后保存目录
     */
    public static Image zoomImage(String src,int w,int h) throws Exception {
        
        
        //double wr=0,hr=0;
        File srcFile = new File(src);

        BufferedImage bufImg = ImageIO.read(srcFile); //读取图片
        
        if(bufImg==null){
            return null;
        }
        
        if(w<=0){
            w=h*bufImg.getWidth()/bufImg.getHeight();
        }
        
        if(h<0){
            h=w*bufImg.getHeight()/bufImg.getWidth();
        }
        
        
        bufImg.getScaledInstance(w, h, BufferedImage.SCALE_SMOOTH);//设置缩放目标图片模板
        
        double wr=w*1.0/bufImg.getWidth();     //获取缩放比例
        double hr=h*1.0 / bufImg.getHeight();

        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        Image Itemp = ato.filter(bufImg, null);
        return Itemp;
        
    }
    

    /*
     * 图片缩放,w，h为缩放的目标宽度和高度
     * src为源文件目录，dest为缩放后保存目录
     */
    public static Image zoomImage(BufferedImage bufImg,int w,int h) throws Exception {
        
        if(bufImg==null){
            return null;
        }
        
        //double wr=0,hr=0;
        //File srcFile = new File(src);

        //BufferedImage bufImg = ImageIO.read(srcFile); //读取图片
        
        if(w<=0){
            w=h*bufImg.getWidth()/bufImg.getHeight();
        }
        
        if(h<0){
            h=w*bufImg.getHeight()/bufImg.getWidth();
        }
        
        
        bufImg.getScaledInstance(w, h, BufferedImage.SCALE_SMOOTH);//设置缩放目标图片模板
        
        double wr=w*1.0/bufImg.getWidth();     //获取缩放比例
        double hr=h*1.0 / bufImg.getHeight();

        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        Image Itemp = ato.filter(bufImg, null);
        return Itemp;
    }
        
    
    
    
/**
     * 从内存字节数组中读取图像
     * 
     * @param imgBytes
     *            未解码的图像数据
     * @return 返回 {@link BufferedImage}
     * @throws IOException
     *             当读写错误或不识别的格式时抛出
     */
    public static final BufferedImage readMemoryImage(byte[] imgBytes) throws IOException {
        if (null == imgBytes || 0 == imgBytes.length)
            throw new NullPointerException("the argument 'imgBytes' must not be null or empty");
        // 获取所有能识别数据流格式的ImageReader对象
        try ( // 将字节数组转为InputStream，再转为MemoryCacheImageInputStream
                ImageInputStream imageInputstream = new MemoryCacheImageInputStream(new ByteArrayInputStream(imgBytes))) {
            // 获取所有能识别数据流格式的ImageReader对象
            Iterator<ImageReader> it = ImageIO.getImageReaders(imageInputstream);
            // 迭代器遍历尝试用ImageReader对象进行解码
            while (it.hasNext()) {
                ImageReader imageReader = it.next();
                // 设置解码器的输入流
                imageReader.setInput(imageInputstream, true, true);
                // 图像文件格式后缀
                String suffix = imageReader.getFormatName().trim().toLowerCase();
                // 图像宽度
                int width = imageReader.getWidth(0);
                // 图像高度
                int height = imageReader.getHeight(0);
                System.out.printf("format %s,%dx%d\n", suffix, width, height);
                try {
                    // 解码成功返回BufferedImage对象
                    // 0即为对第0张图像解码(gif格式会有多张图像),前面获取宽度高度的方法中的参数0也是同样的意思
                    return imageReader.read(0, imageReader.getDefaultReadParam());
                } catch (IOException e) {
                    imageReader.dispose();
                    // 如果解码失败尝试用下一个ImageReader解码
                } catch (Exception e) {
                    imageReader.dispose();
                    // 如果解码失败尝试用下一个ImageReader解码
                }
            }
        }
        // 没有能识别此数据的图像ImageReader对象，抛出异常
        throw new IOException("unsupported image format");
    }
    public static final BufferedImage readMemoryImage1(byte[] imgBytes) throws IOException {
        if (null == imgBytes || 0 == imgBytes.length)
            throw new NullPointerException("the argument 'imgBytes' must not be null or empty");
        // 将字节数组转为InputStream，再转为MemoryCacheImageInputStream
        ImageInputStream imageInputstream = new MemoryCacheImageInputStream(new ByteArrayInputStream(imgBytes));
        // 直接调用ImageIO.read方法解码
        BufferedImage bufImg = ImageIO.read(imageInputstream);
        if(null==bufImg)
            // 没有能识别此数据的图像ImageReader对象，抛出异常
            throw new IOException("unsupported image format");
        return bufImg;
    }
    /**
     * 从{@link InputStream}读取字节数组<br>
     * 结束时会关闭{@link InputStream}<br>
     * {@code in}为{@code null}时抛出{@link NullPointerException}
     * 
     * @param in
     * @return 字节数组
     * @throws IOException
     */
    public static final byte[] readBytes(InputStream in) throws IOException {
        if (null == in)
            throw new NullPointerException("the argument 'in' must not be null");
        try {
            int buffSize = Math.max(in.available(), 1024 * 8);
            byte[] temp = new byte[buffSize];
            ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);
            int size;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            return out.toByteArray();
        } finally {
            in.close();
        }
    }    
    

    /**
     * Downloads a file from a URL
     * @param fileURL HTTP URL of the file to be downloaded
     * @return 
     * @throws IOException
     */
    public static BufferedImage downloadImage(String fileURL) throws IOException {
        
        if(fileURL==null){
            return null;
        }
        
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();
        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            
            BufferedImage bufImg;
            // 将字节数组转为InputStream，再转为MemoryCacheImageInputStream
            try ( // opens input stream from the HTTP connection
                    InputStream inputStream = httpConn.getInputStream()) {
                // 将字节数组转为InputStream，再转为MemoryCacheImageInputStream
                ImageInputStream imageInputstream = new MemoryCacheImageInputStream(inputStream);
                // 直接调用ImageIO.read方法解码
                bufImg = ImageIO.read(imageInputstream);
                if(null==bufImg){
                    // 没有能识别此数据的图像ImageReader对象，抛出异常
                    // throw new IOException("unsupported image format");
                }
            }

            return bufImg;

            
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
        
        return null;
    }    
        
    
    
}
