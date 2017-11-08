package com.hy.wrap

import android.content.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    @JvmField
    val sTAG = "Test"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun translate(view: View) {
        val str = et.text.toString()
        Log.e(sTAG, str)

        val unicode = UnicodeUtil.string2Unicode(str)
        Log.e(sTAG, "unicode:\n" + unicode)

        val newUnicode = unicode.replace("\\ud", "\\ua")

        Log.e(sTAG, "newUnicode:\n" + newUnicode)

        val newStr = UnicodeUtil.unicode2String(newUnicode)

        Log.e(sTAG, "newStr:\n" + newStr)

        et.setText(newStr)


    }

    fun copy(view: View) {
        val cmb = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val mClipData = ClipData.newPlainText("Label", et.text)
        cmb.primaryClip = mClipData
        Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show()
    }

    fun translateAndCopy(view: View) {
        translate(view)
        copy(view)
        jumpToWX()
    }

    private fun jumpToWX() {
        try {
            val intent = Intent()
            val cmp = ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.action = Intent.ACTION_MAIN
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.component = cmp
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "跳转失败", Toast.LENGTH_SHORT).show()
        }
    }


}
