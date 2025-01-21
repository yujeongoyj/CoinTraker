package com.project.cointraker.view.intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.project.cointraker.R
import com.project.cointraker.databinding.FragmentIntro1Binding


class IntroFragment1 : Fragment() {

    private var _binding : FragmentIntro1Binding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    // fragement의 뷰가 처음 생성될 때 호출
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIntro1Binding.inflate(inflater, container, false)
        return binding.root
    }

    // 뷰가 완전히 생성된 후 호출 (버튼, 리스너 등 초기화 작업은 여기서 하는게 적합)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextBtn.setOnClickListener {
            // Navigation의 action의 id
            Navigation.findNavController(view).navigate(R.id.action_introFragment1_to_introFragment2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}