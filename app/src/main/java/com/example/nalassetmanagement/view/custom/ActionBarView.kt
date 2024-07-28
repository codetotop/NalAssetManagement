package com.example.nalassetmanagement.view.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.nalassetmanagement.R
import com.example.nalassetmanagement.databinding.ActionBarBinding

class ActionBarView : LinearLayout {
    private var binding: ActionBarBinding =
        ActionBarBinding.inflate(LayoutInflater.from(context), this, false)

    private var listener: ActionBarViewListener? = null

    fun setActionBarViewListener(listener: ActionBarViewListener) {
        this.listener = listener
    }

    interface ActionBarViewListener {
        fun onClickLeftButton()
        fun onClickRightButton()
    }

    init {
        addView(binding.root)
    }

    constructor(context: Context) : super(context) {
        initView(context,)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView(context, attributeSet, 0)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        initView(context, attributeSet, defStyleAttr)
    }

    @SuppressLint("Recycle")
    private fun initView(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int? = null
    ) {

        defStyleAttr?.let {
            val array =
                context.obtainStyledAttributes(attributeSet, R.styleable.ActionBarView, it, 0)

            val title = array.getString(R.styleable.ActionBarView_title).toString()
            val imgLeftRes = array.getDrawable(R.styleable.ActionBarView_img_left)
            val imgRightRes = array.getDrawable(R.styleable.ActionBarView_img_right)
            setTitle(title)
            binding.imgLeft.setImageDrawable(imgLeftRes)
            binding.imgRight.setImageDrawable(imgRightRes)

            binding.imgLeft.setOnClickListener {
                listener?.onClickLeftButton()
            }

            binding.imgRight.setOnClickListener {
                listener?.onClickRightButton()
            }

            array.recycle()
        }
    }

    fun setTitle(title: String) {
        binding.tvTitle.text = title
    }
}
