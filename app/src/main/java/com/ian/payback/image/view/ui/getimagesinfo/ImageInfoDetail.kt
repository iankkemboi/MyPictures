package com.ian.payback.image.view.ui.getimagesinfo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.ian.payback.image.R
import com.ian.payback.image.databinding.FragmentGetImagesInfoBinding
import com.ian.payback.image.databinding.ImageInfoDetailFragmentBinding
import com.ian.payback.image.util.setItems

class ImageInfoDetail : Fragment() {

    private lateinit var fragmentGetImagesInfo: ImageInfoDetailFragmentBinding
    private lateinit var viewModel: ImageInfoDetailViewModel
    private val args: ImageInfoDetailArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentGetImagesInfo = ImageInfoDetailFragmentBinding.inflate(inflater, container, false)
        return fragmentGetImagesInfo.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ImageInfoDetailViewModel::class.java]
        fragmentGetImagesInfo.viewModel = viewModel
        val imageSelected = args.imageSelected
        if (imageSelected != null) {

            viewModel.bind(imageSelected)
        }

        viewModel.getTags().observe(viewLifecycleOwner, Observer {
            it?.let {
                fragmentGetImagesInfo.listOfTags.setItems(it)
            }
        })

    }

}