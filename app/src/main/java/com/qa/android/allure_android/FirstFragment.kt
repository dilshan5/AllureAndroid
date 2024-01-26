package com.qa.android.allure_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.qa.android.allure_android.databinding.FragmentFirstBinding

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

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        binding.btnJoke.setOnClickListener {
            // Call the getjokes() method of the ApiCall class,
            // passing a callback function as a parameter.
            ApiCall().getjokes(this) { jokes ->
                // Set the text of the text view to the
                // joke value returned by the API response.
                binding.tvJoke.text = jokes.value
            }
        }
        binding.buttonFirst.setBackgroundColor(resources.getColor(R.color.black))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}