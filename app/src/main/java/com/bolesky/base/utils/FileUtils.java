package com.bolesky.base.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class FileUtils {
    /**
     * 删除文件
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        // If path == null will throw NullPointException.
        if (TextUtils.isEmpty(path)) {
            return false;
        }

        File file = new File(path);
        if (!file.exists()) {
            return false;
        }

        boolean flag = file.delete();
        File parent = file.getParentFile();
        if (isEmptyDirectory(parent)) {
            parent.delete();
        }
        return flag;
    }

    /**
     * 判断目录是否为空
     * @param directory
     * @return
     */
    public static boolean isEmptyDirectory(File directory) {
        String[] names = directory.list();
        return names == null || names.length == 0;
    }

    /**
     * 获取写入文件目录：SDCard或者内部存储
     * @param context
     * @return
     */
    public static File getWritableFileDir(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = context.getExternalFilesDir(null);
            if (file == null) {
                file = context.getFilesDir();
            }
            return file;
        } else {
            return context.getFilesDir();
        }
    }

    /**
     * 读取输入流
     * @param is
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream is) throws IOException {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        baos.close();
        is.close();
        return baos.toByteArray();
    }

    /**
     * 输入流转换为String
     * @param is
     * @return
     * @throws IOException
     */
    public static String readInputStreamToString(InputStream is) throws IOException {
        return new String(readInputStream(is));
    }

    /**
     * 读取Text文本
     * @param file
     * @return
     */
    public static String readTextFile(File file) {
        if (!file.exists()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * 写入Text文本
     * @param file
     * @param text
     */
    public static void writeTextFile(File file, String text) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(text);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入文件
     * @param data
     * @param file
     */
    public static void writeFile(String data, File file) {
        writeFile(data.getBytes(Charset.forName("UTF-8")), file);
    }

    /**
     * 文件写入操作
     * @param data
     * @param file
     */
    public static void writeFile(byte[] data, File file) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file, false));
            bos.write(data);
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
