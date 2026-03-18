package com.liquidglass.localsend

import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import com.kyant.liquidglass.liquidGlass
import com.kyant.liquidglass.rememberLiquidGlassState
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.XposedBridge

class MainHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: IXposedHookLoadPackage.LoadPackageParam) {
        if (lpparam.packageName != "org.localsend.localsend_app") return
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return

        try {
            val composeViewClass = Class.forName(
                "androidx.compose.ui.platform.ComposeView",
                false,
                lpparam.classLoader
            )

            val composableClass = Class.forName(
                "androidx.compose.runtime.Composable",
                false,
                lpparam.classLoader
            )

            XposedHelpers.findAndHookMethod(
                composeViewClass,
                "setContent",
                composableClass,
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        val composeView = param.thisObject as ComposeView
                        composeView.setContent {
                            MaterialTheme {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .liquidGlass(
                                            rememberLiquidGlassState(
                                                blur = 70,
                                                refraction = -60,
                                                tint = Color.White.copy(alpha = 0.15f)
                                            )
                                        )
                                ) {
                                    (param.args[0] as () -> Unit).invoke()
                                }
                            }
                        }
                    }
                }
            )
        } catch (e: Exception) {
            XposedBridge.log("LiquidGlass 错误: ${e.message}")
        }
    }
}
