package fr.arnaud_camus.leren.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.View
import android.widget.TextView
import fr.arnaud_camus.leren.R
import kotlinx.android.synthetic.main.adapter_dictionary.view.*


/**
 * Created by arnaud on 3/11/17.
 */
class CustomDecorationDivider(val context: Context) : RecyclerView.ItemDecoration() {

    private val paint: Paint
    private val heightDp: Int = dpToPx(1)
    private val leftInset: Int = dpToPx(40)

    init {
        paint = Paint()
        paint.setStyle(Paint.Style.FILL)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(0, 0, 0, heightDp)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (i in 0..parent.childCount - 1) {
            val view = parent.getChildAt(i)
            val firstLetterView = view.findViewById(R.id.firstLetter) as TextView
            val position = parent.getChildAdapterPosition(view)
            val nextFirstLetterView : TextView? = if (parent.adapter.itemCount > position+1) {
                parent.getChildAt(i+1)?.findViewById(R.id.firstLetter) as? TextView
            } else {
                null
            }

            var inset = 0
            if (nextFirstLetterView?.text == firstLetterView.text) {
                inset = leftInset

                paint.setColor(Color.WHITE)
                c.drawRect(view.left.toFloat(), view.bottom.toFloat(),
                        view.left.toFloat() + inset, view.bottom.toFloat() + heightDp, paint)
            }

            paint.setColor(ContextCompat.getColor(context, R.color.divider))
            c.drawRect(view.left.toFloat() + inset, view.bottom.toFloat(),
                    view.right.toFloat(), view.bottom.toFloat() + heightDp, paint)
        }
    }

    fun dpToPx(dp: Int): Int {
        val displayMetrics = Resources.getSystem().getDisplayMetrics()
        return (dp * (displayMetrics.densityDpi / 160f)).toInt()
    }
}