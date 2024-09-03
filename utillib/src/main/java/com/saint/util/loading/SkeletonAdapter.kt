package com.saint.util.loading

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.ethanhua.skeleton.SkeletonScreen
import com.saint.util.R
import com.saint.util.loading.LoadingHelper.Adapter
import com.saint.util.loading.LoadingHelper.ViewHolder
import io.supercharge.shimmerlayout.ShimmerLayout

/**
 * @author Saint  2020/8/20
 */
class SkeletonAdapter() : Adapter<ViewHolder>() {
    private var skeletonScreen: SkeletonScreen? = null
    var layoutId = R.layout.loading_layout_loading

    constructor(@LayoutRes layoutId: Int) : this() {
        this.layoutId = layoutId
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return if (layoutId == R.layout.loading_layout_loading) {
            ViewHolder(inflater.inflate(layoutId, parent, false))
        } else {
            ShimmerViewHolder(inflater, layoutId)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder) {
        if (layoutId == R.layout.loading_layout_loading) {
            val layoutParams = holder.rootView.layoutParams
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            holder.rootView.layoutParams = layoutParams
            val ivLoading = holder.rootView.findViewById<ImageView>(R.id.img_loading)
            Glide.with(holder.rootView.context)
                .asGif()
                .load(R.drawable.loading)
                .into(ivLoading)
        } else {
            val layout = holder.rootView as ShimmerLayout
            layout.setShimmerAnimationDuration(1000)
            layout.setShimmerAngle(30)
            layout.setShimmerColor(ContextCompat.getColor(holder.rootView.context, R.color.skeleton_shimmer))
            layout.startShimmerAnimation()
        }
    }

    fun hide() {
        if (skeletonScreen != null) skeletonScreen!!.hide()
    }

    class ShimmerViewHolder(inflater: LayoutInflater, innerViewResId: Int) :
        ViewHolder(inflater.inflate(R.layout.layout_shimmer, null)) {
        init {
            val layout = rootView as ViewGroup
            val view = inflater.inflate(innerViewResId, layout, false)
            val lp = view.layoutParams
            if (lp != null) {
                layout.layoutParams = lp
            }
            layout.addView(view)
        }
    }
}


