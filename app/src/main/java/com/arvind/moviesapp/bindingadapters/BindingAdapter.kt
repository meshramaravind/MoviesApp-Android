package com.arvind.moviesapp.bindingadapters

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class BindingAdapter @Inject constructor() {
    companion object {
        @BindingAdapter("android:imageURL")
        @JvmStatic
        fun setImageURL(imageView: ImageView, URL: String?) {
            try {
                imageView.alpha = 0f
                Picasso.get().load(URL).noFade().into(imageView, object : Callback {
                    override fun onSuccess() {
                        imageView.animate().setDuration(300).alpha(1f).start()
                    }

                    override fun onError(e: Exception?) {}
                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        @BindingAdapter("start_date")
        @JvmStatic
        fun SetstartdateFormat(textview: TextView, start_date: String?) {
            if (start_date != null) {
                val parts = start_date.split("T00:00:00.000Z".toRegex()).toTypedArray()
                val datesplitnexthearingdate = parts[0]

                @SuppressLint("SimpleDateFormat") val dateFormatprev =
                    SimpleDateFormat("yyyy-MM-dd")
                var d: Date? = null
                try {
                    d = dateFormatprev.parse(datesplitnexthearingdate)
                    @SuppressLint("SimpleDateFormat") val dateFormat =
                        SimpleDateFormat("dd MMM yyyy")
                    val formatconvertDate = dateFormat.format(d)
                    textview.setText(formatconvertDate)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
        }


        @SuppressLint("SetTextI18n")
        @BindingAdapter("air_date")
        @JvmStatic
        fun SetairdateFormat(textview: TextView, air_date: String?) {
            if (air_date != null) {
                val parts = air_date.split("T00:00:00.000Z".toRegex()).toTypedArray()
                val datesplitnexthearingdate = parts[0]

                @SuppressLint("SimpleDateFormat") val dateFormatprev =
                    SimpleDateFormat("yyyy-MM-dd")
                var d: Date? = null
                try {
                    d = dateFormatprev.parse(datesplitnexthearingdate)
                    @SuppressLint("SimpleDateFormat") val dateFormat =
                        SimpleDateFormat("dd MMM yyyy")
                    val formatconvertDate = dateFormat.format(d)
                    textview.setText("Air Date:" + formatconvertDate)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
        }

    }

}