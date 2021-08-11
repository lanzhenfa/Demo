package com.me.demo.wallpaper;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.service.wallpaper.WallpaperService;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 壁纸设置工具
 * 设置静态和动态壁纸
 */
public class WallPaperUtil {

    /**
     * 使用反射机制强制设置动态壁纸
     *
     * @param context              上下文
     * @param wallPaperServiceName 动态壁纸服务名,可在服务下用getCanonicalName()方法获取
     */
    public static void setLiveWallPaper(Context context, String wallPaperServiceName) {
        try {
            WallpaperManager manager = WallpaperManager.getInstance(context.getApplicationContext());

            //获取IWallpaperManager
            Method method = WallpaperManager.class.getMethod("getIWallpaperManager", new Class[]{});
            Object objIWallpaperManager = method.invoke(manager, new Object[]{});
            Class[] param = new Class[1];
            param[0] = ComponentName.class;

            //取得关键方法setWallpaperComponent，该方法真正实施壁纸设置
            method = objIWallpaperManager.getClass().getMethod("setWallpaperComponent", param);

            //使用Intent转入系统服务接口
            Intent intent = new Intent(WallpaperService.SERVICE_INTERFACE);
            intent.setClassName(context.getApplicationContext().getPackageName(), wallPaperServiceName);

            //invoke调用IWallpaperManager底层方法
            //获得系统app权限才能使用
            method.invoke(objIWallpaperManager, intent.getComponent());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过跳转预览界面设置动态壁纸
     *
     * @param context              上下文
     * @param wallPaperServiceName 动态壁纸服务名,可在服务下用getCanonicalName()方法获取
     */
    public static void setLiveToPreview(Context context, String wallPaperServiceName) {
        Intent intent = new Intent(
                WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                new ComponentName(context, wallPaperServiceName));
        context.startActivity(intent);
    }

    /**
     * 设置静态壁纸
     *
     * @param context    上下文
     * @param bitmapData 静态壁纸资源
     */
    public static void setStaticWallPaper(Context context, InputStream bitmapData) {
        try {
            WallpaperManager manager = WallpaperManager.getInstance(context.getApplicationContext());
            manager.setStream(bitmapData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除壁纸
     *
     * @param context 上下文对象
     */
    public static void clearWallPaper(Context context) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        try {
            wallpaperManager.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
