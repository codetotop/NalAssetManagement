package com.example.nalassetmanagement.base

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheet<VB : ViewBinding> : BottomSheetDialogFragment() {
    private var isShowing = false
    protected lateinit var viewBinding: VB

    open val canceledOnTouchOutside: Boolean = false
    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = getViewBinding(inflater, container)
        viewBinding.apply {
        }
        if (savedInstanceState != null) {
            if (isAdded) {
                dismiss()
            }
        }
        return viewBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.decorView?.background = ColorDrawable(Color.TRANSPARENT)
        dialog?.setCancelable(canceledOnTouchOutside)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (isShowing) return
        isShowing = true
        super.show(manager, tag)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        isShowing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isShowing = false
    }

    open fun showDialog(
        fragmentManager: FragmentManager?,
        tag: String?
    ) {
        fragmentManager?.let {
            show(fragmentManager,tag)
        }
    }
}