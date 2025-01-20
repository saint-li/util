package com.saint.util.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import java.util.Locale


class MarketUtils {
    private var tools: MarketUtils? = null
    private val schemaUrl: String = "market://details?id="

    fun getTools(): MarketUtils {
        if (null == tools) {
            tools = MarketUtils()
        }
        return tools as MarketUtils
    }

    /***
     * / * 不指定包名
     * / * @param mContext
     */
    fun startMarket(mContext: Context): Boolean {
        val packageName = mContext.packageName //得到包名
        return startMarket(mContext, packageName)
    }

    /**
     * / * 指定包名
     *
     *
     * / *
     *
     *
     * / * @param mContext
     *
     *
     * / * @param packageName
     */
    fun startMarket(mContext: Context, packageName: String): Boolean {
        try {
            val deviceBrand = getDeviceBrand() //获得手机厂商
            //根据厂商获取对应市场的包名
            val brandName = deviceBrand.uppercase(Locale.getDefault()) //大写
            if (TextUtils.isEmpty(brandName)) {
                AppLog.e("没有读取到手机厂商~~")
                return false
            }
            val marketPackageName = getBrandName(brandName)
            AppLog.d("marketPackageName:$marketPackageName")
            AppLog.d("brandName:$brandName")
            AppLog.d("deviceBrand:$deviceBrand")
            if (null == marketPackageName || "" == marketPackageName) {
                return false
            }
            if (marketPackageName == PACKAGE_NAME.SAMSUNG_PACKAGE_NAME) {
                //三星特殊处理
                val uri = Uri.parse("http://www.samsungapps.com/appquery/appDetail.as?appId=$packageName")
                val goToMarket = Intent()
                goToMarket.setClassName("com.sec.android.app.samsungapps", "com.sec.android.app.samsungapps.Main")
                goToMarket.setData(uri)
                try {
                    mContext.startActivity(goToMarket)
                    return true
                } catch (e: ActivityNotFoundException) {
                    return false
                }
            } else {
                return startMarket(mContext, packageName, marketPackageName)
            }
        } catch (anf: ActivityNotFoundException) {
            AppLog.e("要跳转的应用市场不存在!" + anf.message)
        } catch (e: Exception) {
            AppLog.e("其他错误：" + e.message)
        }
        return false
    }

    /***
     * / * 指定包名，指定市场
     * / * @param mContext
     * / * @param packageName
     * / * @param marketPackageName
     */
    fun startMarket(mContext: Context, packageName: String, marketPackageName: String): Boolean {
        try {
            openMarket(mContext, packageName, marketPackageName)
            return true
        } catch (anf: ActivityNotFoundException) {
            AppLog.e("要跳转的应用市场不存在!" + anf.message)
            return false
        } catch (e: Exception) {
            AppLog.e("其他错误：" + e.message)
            return false
        }
    }

    /***
     * / * 打开应用市场
     * / * @param mContext
     * / * @param packageName
     * / * @param marketPackageName
     */
    private fun openMarket(mContext: Context, packageName: String, marketPackageName: String): Boolean {
        try {
            val uri = Uri.parse(schemaUrl + packageName)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage(marketPackageName)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            mContext.startActivity(intent)
            return true
        } catch (anf: ActivityNotFoundException) {
            AppLog.e("要跳转的应用市场不存在!" + anf.message)
            return false
        } catch (e: Exception) {
            AppLog.e("其他错误：" + e.message)
            return false
        }
    }

    /***
     * / * 检测是否是应用宝或者是百度市场
     * / * @param mContext
     * / * @param packageName
     * / * @return
     */
    private fun isCheckBaiduOrYYB(mContext: Context, packageName: String): Boolean {
        val installed = isInstalled(packageName, mContext)
        return installed
    }

    /****
     * / * 检查APP是否安装成功
     * / * @param packageName
     * / * @param context
     * / * @return
     */
    private fun isInstalled(packageName: String, context: Context): Boolean {
        if ("" == packageName || packageName.length <= 0) {
            return false
        }

        var packageInfo = try {
            context.packageManager.getPackageInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }

        return if (packageInfo == null) {
            false
        } else {
            true
        }
    }

    private fun getBrandName(brandName: String): String {
        if (BRAND.HUAWEI_BRAND == brandName) {
            //华为
            return PACKAGE_NAME.HUAWEI_PACKAGE_NAME
        } else if (BRAND.OPPO_BRAND == brandName) {
            //oppo
            return PACKAGE_NAME.OPPO_PACKAGE_NAME
        } else if (BRAND.REALME_BRAND == brandName) {
            //realme
            return PACKAGE_NAME.OPPO_PACKAGE_NAME
        } else if (BRAND.VIVO_BRAND == brandName) {
            //vivo
            return PACKAGE_NAME.VIVO_PACKAGE_NAME
        } else if (BRAND.XIAOMI_BRAND == brandName) {
            //小米
            return PACKAGE_NAME.XIAOMI_PACKAGE_NAME
        } else if (BRAND.REDMI_BRAND == brandName) {
            //红米
            return PACKAGE_NAME.XIAOMI_PACKAGE_NAME
        } else if (BRAND.LENOVO_BRAND == brandName) {
            //联想
            return PACKAGE_NAME.LIANXIANG_PACKAGE_NAME
        } else if (BRAND.MEIZU_BRAND == brandName) {
            //魅族
            return PACKAGE_NAME.MEIZU_PACKAGE_NAME
        } else if (BRAND.HONOR_BRAND == brandName) {
            //荣耀
            return PACKAGE_NAME.HONOR_PACKAGE_NAME
        } else if (BRAND.ZTE_BRAND == brandName) {
            //zte
            return PACKAGE_NAME.ZTE_PACKAGE_NAME
        } else if (BRAND.NIUBIA_BRAND == brandName) {
            //努比亚
            return PACKAGE_NAME.NIUBIA_PACKAGE_NAME
        } else if (BRAND.ONE_PLUS_BRAND == brandName) {
            //OnePlus
            return PACKAGE_NAME.OPPO_PACKAGE_NAME
        } else if (BRAND.SONY_BRAND == brandName) {
            //索尼
            return PACKAGE_NAME.GOOGLE_PACKAGE_NAME
        } else if (BRAND.SAMSUNG_BRAND == brandName) {
            return PACKAGE_NAME.SAMSUNG_PACKAGE_NAME
        } else if (BRAND.GOOGLE_BRAND == brandName) {
            //google
            return PACKAGE_NAME.GOOGLE_PACKAGE_NAME
        }
        return ""
    }

    /**
     * / * 获取手机厂商
     */
    private fun getDeviceBrand(): String {
        return Build.BRAND
    }

    object BRAND {
        const val HUAWEI_BRAND: String = "HUAWEI" //HUAWEI_PACKAGE_NAME

        const val HONOR_BRAND: String = "HONOR" //HUAWEI_PACKAGE_NAME

        const val OPPO_BRAND: String = "OPPO" //OPPO_PACKAGE_NAME

        const val REALME_BRAND: String = "REALME" //OPPO_PACKAGE_NAME

        const val MEIZU_BRAND: String = "MEIZU" //MEIZU_PACKAGE_NAME

        const val VIVO_BRAND: String = "VIVO" //VIVO_PACKAGE_NAME

        const val XIAOMI_BRAND: String = "XIAOMI" //XIAOMI_PACKAGE_NAME
        const val REDMI_BRAND: String = "REDMI" //XIAOMI_PACKAGE_NAME

        const val LENOVO_BRAND: String = "LENOVO" //LIANXIANG_PACKAGE_NAME //Lenovo

        const val ZTE_BRAND: String = "ZTE" //ZTE_PACKAGE_NAME
        const val XIAOLAJIAO_BRAND: String = "XIAOLAJIAO" //ZHUOYI_PACKAGE_NAME
        const val QH360_BRAND: String = "360" //QH360_PACKAGE_NAME
        const val NIUBIA_BRAND: String = "NUBIA" //NIUBIA_PACKAGE_NAME
        const val ONE_PLUS_BRAND: String = "ONEPLUS" //OPPO_PACKAGE_NAME
        const val MEITU_BRAND: String = "MEITU" //MEITU_PACKAGE_NAME
        const val SONY_BRAND: String = "SONY" //GOOGLE_PACKAGE_NAME
        const val GOOGLE_BRAND: String = "GOOGLE" //GOOGLE_PACKAGE_NAME
        const val HTC_BRAND: String = "HTC" //未知应用商店包名
        const val ZUK_BRAND: String = "ZUK" //未知应用商店包名
        const val SAMSUNG_BRAND: String = "SAMSUNG" //未知应用商店包名
    }


    /** Redmi */
    /**
     * / * 华为，oppo,vivo,小米，360，联想，魅族，安智，百度，阿里，应用宝，goog，豌豆荚，pp助手
     */
    object PACKAGE_NAME {
        const val OPPO_PACKAGE_NAME: String = "com.heytap.market" //oppo
        const val VIVO_PACKAGE_NAME: String = "com.bbk.appstore" //vivo
        const val HUAWEI_PACKAGE_NAME: String = "com.huawei.appmarket" //华为
        const val HONOR_PACKAGE_NAME: String = "com.hihonor.appmarket" //荣耀
        const val XIAOMI_PACKAGE_NAME: String = "com.xiaomi.market" //小米
        const val MEIZU_PACKAGE_NAME: String = "com.meizu.mstore" //，魅族
        const val LIANXIANG_PACKAGE_NAME: String = "com.lenovo.leos.appstore" //联想
        const val ZTE_PACKAGE_NAME: String = "zte.com.market" //zte
        const val GOOGLE_PACKAGE_NAME: String = "com.android.vending" //google
        const val NIUBIA_PACKAGE_NAME: String = "com.nubia.neostore" //努比亚
        const val SAMSUNG_PACKAGE_NAME: String = "com.sec.android.app.samsungapps"
    }

    /**
     * / * 启动到应用商店app详情界面
     *
     *
     * / * @param appPkg 目标App的包名
     *
     *
     * / * @param marketPkg 应用商店包名 ,如果为"" 则由系统弹出应用商店
     *
     *
     * / * 列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    fun launchAppDetail(context: Context, appPkg: String, marketPkg: String?) {
        try {
            if (TextUtils.isEmpty(appPkg)) return
            val uri = Uri.parse("market://details?id=$appPkg")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            if (!TextUtils.isEmpty(marketPkg)) {
                intent.setPackage(marketPkg)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}