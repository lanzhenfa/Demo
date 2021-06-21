package com.me.demo.util;

import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Create by lzf on 6/10/21
 */
public class FileUtils {

    private static final String TAG = "FileUtils";

    public static byte[] readFileToByteArray(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        while ((n = fis.read(b)) != -1) {
            bos.write(b, 0, n);
        }
        fis.close();
        bos.close();
        return bos.toByteArray();
    }

    public static boolean writeFile(InputStream inputStream, String path) throws Exception {
        Log.d(TAG, ">>writeFile");
        boolean result = false;
        if (inputStream == null) {
            Log.e(TAG, "inputStream null, return");
            return result;
        }
        if (TextUtils.isEmpty(path)) {
            Log.e(TAG, "path null, return");
            return result;
        }
        byte[] bytes = new byte[1024 * 4];
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        int length;
        while ((length = inputStream.read(bytes)) != -1) {
            fileOutputStream.write(bytes, 0, length);
            fileOutputStream.flush();
        }

        fileOutputStream.close();
        inputStream.close();

        result = true;

        Log.e(TAG, ">>writeFile finish");
        return result;
    }
}
