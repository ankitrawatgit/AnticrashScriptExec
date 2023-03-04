package com.ar.myapplicationfortest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import java.io.BufferedReader
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var st:String=""
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.setTitle("Script Executer")
       // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val path= findViewById<EditText>(R.id.path)
        val script= findViewById<EditText>(R.id.script)
        val textView=findViewById<TextView>(R.id.textView4)


        val checkBox=findViewById<CheckBox>(R.id.checkBox)
        val sharedPreference =  getSharedPreferences("PATH", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        val spath=sharedPreference.getString("path","")
        val sname=sharedPreference.getString("script","")
        if(spath!=null){
            checkBox.isChecked=true
            path.setText(spath)
        }
        if(sname!=null){
            script.setText(sname)
        }




        Runtime.getRuntime().exec("su")
        findViewById<Button>(R.id.button).setOnClickListener {
            st=""
            val path= path.text.toString()
            val script= script.text.toString()
            if(checkBox.isChecked){
                editor.putString("path",path)
                editor.putString("script",script)
                editor.apply()
            }
            val s= Runtime.getRuntime().exec("su -c cd $path && nohup ./$script &")
            findViewById<Button>(R.id.button).setText("Executing...")
                textView.setText("")
                val inputStream = s.inputStream
                val errorStream =s.errorStream
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                 val errorReader = BufferedReader(InputStreamReader(errorStream))
                var line: String?
                var errorLine: String?
                Thread(Runnable {
                        while (bufferedReader.readLine().also { line = it } != null) {
                            st+=line+"\n"
                            runOnUiThread(Runnable {  textView.setText(st)
                                findViewById<Button>(R.id.button).setText("Execute")
                            })

                        }
                        while (errorReader.readLine().also { errorLine = it } != null) {
                            st += line + "\n"
                            runOnUiThread(Runnable {
                                textView.setText(st)
                                findViewById<Button>(R.id.button).setText("Execute")
                            })


                        }
                }).start()



        }

     }}
