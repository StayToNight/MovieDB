package com.staynight.moviedb.utils.binding

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

open class BindingActivity<B : ViewBinding> constructor(
    private val inflate: (inflater: LayoutInflater) -> B?
) : AppCompatActivity() {

    private var _binding: B? = null
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate.invoke(layoutInflater)
        if (_binding is ViewDataBinding) (_binding as ViewDataBinding).lifecycleOwner = this
        setContentView(_binding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun changeFragment(fragment: Fragment, tag: String? = null, container: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        if (tag.isNullOrEmpty())
            transaction.replace(container, fragment, tag)
        else
            transaction.replace(container, fragment, tag)
                .addToBackStack(tag)
        transaction.commit()
    }
}