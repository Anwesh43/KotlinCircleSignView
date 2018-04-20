package ui.anwesome.com.kotlincirclesignview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import ui.anwesome.com.circlesignview.CircleSignView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var view : CircleSignView = CircleSignView.create(this)
        view.addCompleteListener({
            Toast.makeText(this, "finished", Toast.LENGTH_SHORT).show()
        },{
            Toast.makeText(this, "restarted", Toast.LENGTH_SHORT).show()
        })
        fullScreen()
    }
}
fun MainActivity.fullScreen() {
    supportActionBar?.hide()
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}