package com.saint.util.binding

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.viewbinding.ViewBinding

/**
 * @author
 */
abstract class BaseBindingDialog<VB : ViewBinding>(context: Context) : Dialog(context) {

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateBindingWithGeneric(layoutInflater)
        setContentView(binding.root)
    }
}