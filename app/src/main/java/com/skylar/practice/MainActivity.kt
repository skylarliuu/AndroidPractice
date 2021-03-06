package com.skylar.practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.skylar.annotation.MethodTime
import com.skylar.practice.view.PracticeViewActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test()
    }

    @MethodTime
    private fun test() {
        Thread.sleep(1000)
    }

    fun click(view: View) {
        if(view.id == R.id.practice_view) {
            startActivity(Intent(this, PracticeViewActivity::class.java))
        }
    }
}