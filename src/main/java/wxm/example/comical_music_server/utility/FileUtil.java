package wxm.example.comical_music_server.utility;

import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaMetadataKeys;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alex Wang
 * @date 2020/05/10
 */
public class FileUtil {

    /**
     * @param file 文件
     * @param path 文件存放路径
     * @param fileName 源文件名
     * @return
     */
    public static File upload(MultipartFile file, String path, String fileName){
        String realPath = path + "/"+fileName;
        File dest = new File(realPath);
        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            //保存文件
            file.transferTo(dest);
            return dest;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

//    /**
//     * 获取类型
//     * @param file
//     * @return
//     * @throws IOException
//     *
//     */
//
    public static String getMimeType(File file) {
        if (file.isDirectory()) {
            return null;
        }

        AutoDetectParser parser = new AutoDetectParser();
        parser.setParsers(new HashMap<MediaType, Parser>());
        Metadata metadata = new Metadata();
        metadata.add(TikaMetadataKeys.RESOURCE_NAME_KEY, file.getName());
        try (InputStream stream = new FileInputStream(file)) {
            parser.parse(stream, new DefaultHandler(), metadata, new ParseContext());
        }catch (Exception e){
            return null;
        }
        return metadata.get(HttpHeaders.CONTENT_TYPE);
    }
//
//    /**
//     * 判断是否是图片
//     * @param file
//     * @return
//     */
//    public static boolean isImage(File file){
//        String type = getMimeType(file);
//        //System.out.println(type);
//        Pattern p = Pattern.compile("image/.*");
//        Matcher m = p.matcher(type);
//        return m.matches();
//    }
//
//    public static boolean isAudio(File file){
//        String type = getMimeType(file);
//        //System.out.println(type);
//        Pattern p = Pattern.compile("audio/.*");
//        Matcher m = p.matcher(type);
//        return m.matches();
//    }
//
//    public static boolean isText(File file){
//        String type = getMimeType(file);
//        //System.out.println(type);
//        Pattern p = Pattern.compile("text/.*");
//        Matcher m = p.matcher(type);
//        return m.matches();
//    }

    public static String getSuffix(File file){
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return suffix;
    }
    public static String getSuffix(String fileName){
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return suffix;
    }


    public static boolean isAudio(File file){
        String suffix=getSuffix(file);
        ArrayList<String> suffixs= new ArrayList<>();
        suffixs.add("mp3");
        suffixs.add("acc");
        suffixs.add("flac");
        suffixs.add("wmv");
        suffixs.add("ape");
        suffixs.add("ogg");

        for (String s:
             suffixs) {
            if (suffix.toLowerCase().equals(s)){
                return true;
            }
        }

        return false;
    }

    public static boolean isAudio(String file){
        String suffix=getSuffix(file);
        ArrayList<String> suffixs= new ArrayList<>();
        suffixs.add("mp3");
        suffixs.add("acc");
        suffixs.add("flac");
        suffixs.add("wmv");
        suffixs.add("ape");
        suffixs.add("ogg");

        for (String s:
             suffixs) {
            if (suffix.toLowerCase().equals(s)){
                return true;
            }
        }

        return false;
    }

    public static boolean isImage(File file){
        String suffix=getSuffix(file);
        ArrayList<String> suffixs= new ArrayList<>();
        suffixs.add("png");
        suffixs.add("jpg");
        suffixs.add("jpeg");
        suffixs.add("gif");
        for (String s:
             suffixs) {
            if (suffix.toLowerCase().equals(s)){
                return true;
            }
        }

        return false;
    }
    public static boolean isImage(String file){
        String suffix=getSuffix(file);
        ArrayList<String> suffixs= new ArrayList<>();
        suffixs.add("png");
        suffixs.add("jpg");
        suffixs.add("jpeg");
        suffixs.add("gif");
        for (String s:
             suffixs) {
            if (suffix.toLowerCase().equals(s)){
                return true;
            }
        }

        return false;
    }
    public static boolean isLrc(File file){
        return getSuffix(file).toLowerCase().equals("lrc");
    }
    public static boolean isLrc(String file){
        return getSuffix(file).toLowerCase().equals("lrc");
    }



}