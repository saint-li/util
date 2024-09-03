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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import com.saint.util.R
import com.saint.util.databinding.LayoutEmptyBinding

/**
 * @author Dylan Cai
 */
class EmptyAdapter : LoadingHelper.Adapter<LoadingHelper.ViewHolder>() {
    private lateinit var binding: LayoutEmptyBinding
    var tips: String? = null

    @DrawableRes
    var tipsIcon = R.drawable.content_empty

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): LoadingHelper.ViewHolder {
        binding = LayoutEmptyBinding.inflate(inflater)
        return LoadingHelper.ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: LoadingHelper.ViewHolder) {
        if (tips != null && tips!!.isNotEmpty()) {
            binding.tvEmptyTips.text = tips
        }
        if (tipsIcon > 0) {
            binding.ivEmptyTips.setImageResource(tipsIcon)
        }
    }

    fun refreshTips(tips: String, @DrawableRes tipsIcon: Int) {
        this.tips = tips
        this.tipsIcon = tipsIcon
        notifyDataSetChanged()
    }

}