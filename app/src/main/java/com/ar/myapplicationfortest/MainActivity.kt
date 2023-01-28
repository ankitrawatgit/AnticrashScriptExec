package com.ar.myapplicationfortest

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tananaev.adblib.AdbConnection
import com.tananaev.adblib.AdbCrypto
import org.w3c.dom.Text
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var st:String=""
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.setTitle("Anti crash script")
        val path= findViewById<EditText>(R.id.path)
        val script= findViewById<EditText>(R.id.script)
        val textView=findViewById<TextView>(R.id.textView4)
        val textView8=findViewById<TextView>(R.id.textView8)
        val textView2= findViewById<TextView>(R.id.textView7)


        Runtime.getRuntime().exec("su")
        findViewById<Button>(R.id.button).setOnClickListener {
            st=""
            val path= path.text.toString()
            val script= script.text.toString()
            val s= Runtime.getRuntime().exec("su -c cd $path && nohup ./$script &")
            findViewById<Button>(R.id.button).setText("Running...")
                val inputStream = s.inputStream
                val errorStream =s.errorStream
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                 val errorReader = BufferedReader(InputStreamReader(errorStream))
                var line: String?
                var errorLine: String?
                    Thread(Runnable {
                        while (bufferedReader.readLine().also { line = it } != null) {
                            st+=line+"\n"
                            runOnUiThread(Runnable {  textView.setText(st) })

                        }
                        while (errorReader.readLine().also { errorLine = it } != null) {
                            st+=line+"\n"
                            runOnUiThread(Runnable {  textView8.setText(st) })

                    }}).start()





        }

        findViewById<Button>(R.id.button2).setOnClickListener {
            var ss:String=""
            findViewById<Button>(R.id.button).setText("Start")
            val s= Runtime.getRuntime().exec("killall $script")
            val errorStream = s.errorStream
            val bufferedReader = BufferedReader(InputStreamReader(errorStream))
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                ss+=line+"\n"
            }
            ss+="If not stoped You can use STOP button on your script window or Restart device!"
            textView2.setText(ss)
    }}}
