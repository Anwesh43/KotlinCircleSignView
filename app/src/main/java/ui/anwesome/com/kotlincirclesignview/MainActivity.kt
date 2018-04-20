package ui.anwesome.com.kotlincirclesignview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.circlesignview.CircleSignView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CircleSignView.create(this)
    }
}
