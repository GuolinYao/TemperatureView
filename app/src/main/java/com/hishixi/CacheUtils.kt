package com.hishixi

import android.content.Context
import android.content.SharedPreferences

/**
 * 常用小数据的保存
 *
 * @author Seamus
 */
object CacheUtils {
    private var userPreferences: SharedPreferences? = null// 个人信息

    /**
     * 保存是否已经登录
     *
     * @param context   context
     * @param isLogin 是否登录 1 登录 0 未登录
     */
    @JvmStatic
    fun saveIfLogin(context: Context, isLogin: String) {
        userPreferences = context.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE)
        val editor = userPreferences!!.edit()
        editor.putString("isLogin", isLogin)
        editor.apply()
    }

    @JvmStatic
    fun getIfLogin(context: Context): String {
        userPreferences = context.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE)
        return userPreferences!!.getString("isLogin", "0")
    }
    /**
     * 保存是否已经复制完毕
     *
     * @param context   context
     * @param isCopy 是否复制 1 复制 0 未复制
     */
    @JvmStatic
    fun saveIfCopy(context: Context, isCopy: String) {
        userPreferences = context.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE)
        val editor = userPreferences!!.edit()
        editor.putString("isCopy", isCopy)
        editor.apply()
    }

    @JvmStatic
    fun getIfCopy(context: Context): String {
        userPreferences = context.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE)
        return userPreferences!!.getString("isCopy", "0")
    }


}
