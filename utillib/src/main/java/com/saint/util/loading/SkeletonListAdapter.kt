package com.saint.util.loading

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saint.util.R
import com.saint.util.loading.LoadingHelper.Adapter
import com.saint.util.loading.LoadingHelper.ViewHolder
import io.supercharge.shimmerlayout.ShimmerLayout

/**
 * @author Saint  2020/8/20
 */
class SkeletonListAdapter() : Adapter<ViewHolder>() {
    var layoutId = R.layout.loading_layout_skeleton_recycler_item
    var itemCount = 3
    var orientation = RecyclerView.VERTICAL

    constructor(@LayoutRes layoutId: Int) : this() {
        this.layoutId = layoutId
    }

    constructor(@LayoutRes layoutId: Int, itemCount: Int) : this() {
        this.layoutId = layoutId
        this.itemCount = itemCount
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.loading_layout_skeleton_recycler, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder) {
        val recyclerView =
            holder.rootView.findViewById<RecyclerView>(R.id.loading_skeleton_recycler)
        val layoutManager = recyclerView.layoutManager
        if (layoutManager != null) {
            recyclerView.layoutManager = object : LinearLayoutManager(recyclerView.context) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
        }
        recyclerView.adapter = ShimmerListAdapter(itemCount, layoutId)
        val layout =
            holder.rootView.findViewById<ShimmerLayout>(R.id.loading_skeleton_shimmer_layout)
        layout.startShimmerAnimation()
    }
}

class ShimmerListAdapter(private val count: Int, private val itemLayoutId: Int) :
    RecyclerView.Adapter<ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context), parent, itemLayoutId)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return count
    }

}

class ListViewHolder(inflater: LayoutInflater, parent: ViewGroup, innerViewResId: Int) :
    RecyclerView.ViewHolder(inflater.inflate(innerViewResId, parent, false)) {
}


