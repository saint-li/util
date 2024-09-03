/*
 * Copyright (c) 2019. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.saint.util.loading

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saint.util.databinding.LoadingAyoutCustomToolbarBinding
import com.saint.util.util.AppUtil

/**
 * @author Dylan Cai
 */
class CustomToolbarAdapter(private val titleView: View) :
    LoadingHelper.Adapter<CustomToolbarAdapter.ViewHolder>() {
    private lateinit var binding: LoadingAyoutCustomToolbarBinding

    private var colorBar = 0

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        binding = LoadingAyoutCustomToolbarBinding.inflate(inflater)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder) {
        setStatusBarHeight(binding.loadingViewStatusBar)
        if (colorBar > 0) {
            binding.loadingToolbar.setBackgroundColor(colorBar)
        }
        binding.loadingContainer.addView(titleView)
    }

    /**
     * 设置状态栏高度
     */
    fun setStatusBarHeight(view: View) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
        val height = AppUtil.getStatusBarHeight()
        val params: ViewGroup.LayoutParams = view.layoutParams
        params.height = height
        view.layoutParams = params
    }

    class ViewHolder(rootView: View) : LoadingHelper.ViewHolder(rootView)
}
