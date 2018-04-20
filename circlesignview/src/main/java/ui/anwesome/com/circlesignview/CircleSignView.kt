package ui.anwesome.com.circlesignview

/**
 * Created by anweshmishra on 20/04/18.
 */

import android.view.*
import android.content.*
import android.graphics.*

class CircleSignView(ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State (private var prevScale : Float = 0f, var dir : Float = 0f, var j : Int = 0) {

        val scales : Array<Float> = arrayOf(0f, 0f, 0f)

        fun update(stopcb : (Float) -> Unit) {
            scales[j] += 0.1f * this.dir
            if (Math.abs(scales[j] - prevScale) > 1) {
                scales[j] = prevScale + dir
                j += this.dir.toInt()
                if (j == scales.size || j == -1) {
                    j -= dir.toInt()
                    dir = 0f
                    prevScale = scales[j]
                }
            }
        }

        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }

    data class Animator (private var view : View, private var animated : Boolean = false) {

        fun animate(updatecb : () -> Unit) {
            if (animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch (ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class CircleSign (var i : Int , private val state : State = State()) {

        fun draw(canvas : Canvas, paint : Paint) {
            val w : Float = canvas.width.toFloat()
            val h : Float = canvas.height.toFloat()
            val r1 : Float = Math.min(w,h)/3
            val r2 : Float = Math.min(w,h)/20
            canvas.save()
            canvas.translate(w/2, h/2)
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = Math.min(w, h)/60
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = Color.parseColor("#e74c3c")
            for (i in 0..1) {
                canvas.drawArc(RectF(-r1, -r1, r1, r1), 180f * i, 180f * state.scales[0], false, paint)
            }
            canvas.save()
            canvas.rotate(180f * this.state.scales[4])
            paint.style = Paint.Style.FILL
            paint.color = Color.parseColor("#2ecc71")
            for(i in 0..1) {
                canvas.drawCircle(0f, (r1/4) * (1 - 2 * i) * state.scales[2], r2 * this.state.scales[1], paint)
            }
            val x : Float = (r1/5) * state.scales[3]
            canvas.drawLine(-x, 0f, x, 0f, paint)
            canvas.restore()
            canvas.restore()
        }

        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }

        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }

    data class Renderer(var view : CircleSignView) {

        private val animator : Animator = Animator(view)

        private val circleSign : CircleSign = CircleSign(0)

        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            circleSign.draw(canvas, paint)
            animator.animate {
                circleSign.update {
                    animator.stop()
                }
            }
        }

        fun handleTap() {
            circleSign.startUpdating {
                animator.start()
            }
        }
    }
}