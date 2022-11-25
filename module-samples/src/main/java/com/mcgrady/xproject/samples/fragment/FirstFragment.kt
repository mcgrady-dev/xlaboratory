package com.mcgrady.xproject.samples.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mcgrady.xproject.samples.R
import com.mcgrady.xproject.samples.databinding.FragmentFirstBinding
import com.mcgrady.xproject.samples.lifecycle.FragmentLifecycleCallbacksImpl

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.edittext.isFocusableInTouchMode = true
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            binding.edittext.focusable = View.FOCUSABLE_AUTO
//        } else {
//            binding.edittext.isFocusable = true
//        }
//        binding.edittext.requestFocus()

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.i(FragmentLifecycleCallbacksImpl.TAG, "${this::class.simpleName}: onHiddenChanged $hidden")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = FirstFragment()
    }
}