package com.finalcola.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: zhuangyuanyou
 * @date: 2019-05-25 12:41
 */
@Slf4j
final public class FileUtils {

    public static File getClassPathFile(String fileName){
        URL url = ClassLoader.getSystemResource(fileName);
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            log.debug("读取文件失败", e);
            return null;
        }
        return file;
    }

    public static void writeToTempDir(String content) throws IOException {
        String tempDir = System.getProperty("java.io.tmpdir");
        String fileName = System.currentTimeMillis() + "";
        String path = tempDir + File.separator + fileName;
        writeToFile(content, path);
    }

    public static void writeToFile(String content,String path) throws IOException {
        FileWriter fileWriter = new FileWriter(new File(path));
        BufferedWriter writer = new BufferedWriter(fileWriter);
        writer.write(content);
//        Files.write(Paths.get(path), content.getBytes(StandardCharsets.UTF_8));
    }

    public static void writeToFile(InputStream in,String path) throws IOException {
        OutputStream out = Files.newOutputStream(Paths.get(path));
        byte[] buffer = new byte[2048];
        int read = 0;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public static byte[] read(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        return bytes;
    }

    public static String getClassPath(){
        File file = getClassPathFile("application.properties");
        String path = file.getParentFile().getAbsolutePath();
        return path;
    }

    // 删除目录下的文件
    public static int deleteAllFiles(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            return 0;
        }
        List<File> files = Arrays.stream(file.listFiles())
                .filter(File::isFile)
                .collect(Collectors.toList());
        int size = files.size();
        files.forEach(File::delete);
        return size;
    }

}
