package com.ian.payback.image.view.ui.getimagesinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.snackbar.Snackbar
import com.ian.payback.image.databinding.FragmentGetImagesInfoBinding
import com.ian.payback.image.domain.model.ImageInfo
import com.ian.payback.image.util.Constants.DEFAULT_SEARCH_TERM
import com.ian.payback.image.util.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GetImagesInfoFragment : Fragment(), OnImageAdapterListener {
    private lateinit var adapter: ImageInfoAdapter
    private lateinit var viewModel: GetImagesInfoViewModel
    private lateinit var fragmentGetImagesInfo: FragmentGetImagesInfoBinding
    private lateinit var errorSnackbar: Snackbar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentGetImagesInfo = FragmentGetImagesInfoBinding.inflate(inflater, container, false)
        return fragmentGetImagesInfo.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ImageInfoAdapter(this)

        fragmentGetImagesInfo.imgrecycler.adapter = adapter
        errorSnackbar = Snackbar.make(fragmentGetImagesInfo.homelayout, "", Snackbar.LENGTH_LONG)

        fragmentGetImagesInfo.search.isActivated = true
        fragmentGetImagesInfo.search.queryHint = "Type image keyword to search"
        fragmentGetImagesInfo.search.onActionViewExpanded()
        fragmentGetImagesInfo.search.isIconified = false
        fragmentGetImagesInfo.search.clearFocus()

        fragmentGetImagesInfo.search.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.getImages(query)

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return false
            }
        })

        viewModel = ViewModelProvider(this)[GetImagesInfoViewModel::class.java]
        fragmentGetImagesInfo.viewModel = viewModel


        viewModel.getImages(DEFAULT_SEARCH_TERM)


        fragmentGetImagesInfo.tryAgain.setOnClickListener {
            viewModel.getImages(DEFAULT_SEARCH_TERM)
        }



        setupObservers()

    }

    private fun setupObservers() {
        viewModel.imageListResource.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data!!.isNotEmpty()) {
                        adapter.addData(it.data)
                        fragmentGetImagesInfo.layoutStates.visibility = View.GONE
                        fragmentGetImagesInfo.animationView.visibility = View.GONE
                        fragmentGetImagesInfo.tryAgain.visibility = View.GONE
                    } else {
                        handleEmptyData()
                    }

                }
                Status.ERROR -> {

                    fragmentGetImagesInfo.layoutStates.visibility = View.GONE
                    fragmentGetImagesInfo.animationView.visibility = View.VISIBLE
                    fragmentGetImagesInfo.tryAgain.visibility = View.VISIBLE
                    fragmentGetImagesInfo.animationView.repeatCount = LottieDrawable.INFINITE

                    fragmentGetImagesInfo.animationView.setAnimation("call_failed_animation.json")
                    fragmentGetImagesInfo.animationView.playAnimation()
                    errorSnackbar.setText("There was an error fetching data.Please try again")

                    adapter.clear()
                    errorSnackbar.show()


                }


                Status.LOADING -> {
adapter.clear()
                    fragmentGetImagesInfo.layoutStates.visibility = View.VISIBLE
                    fragmentGetImagesInfo.animationView.visibility = View.GONE

                }


            }
        })
    }

    override fun gotoDetailPage(image: ImageInfo?) {

        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Image Listing")

        builder.setMessage("Are you sure you want to see more details of this image listing")

        builder.setNegativeButton("Cancel") { _, _ ->

        }
        builder.setPositiveButton("YES") { _, _ ->
            val mRootView = fragmentGetImagesInfo.root

            val action =
                GetImagesInfoFragmentDirections.actionGetImageInfoFragmentToImageInfoDetail(
                    image
                )
            mRootView.findNavController().navigate(action)
        }




        val dialog: AlertDialog = builder.create()

        dialog.show()

    }

    private fun handleEmptyData() {

        fragmentGetImagesInfo.layoutStates.visibility = View.GONE
        fragmentGetImagesInfo.animationView.visibility = View.VISIBLE
        fragmentGetImagesInfo.tryAgain.visibility = View.VISIBLE
        fragmentGetImagesInfo.animationView.repeatCount = LottieDrawable.INFINITE

        fragmentGetImagesInfo.animationView.setAnimation("empty_animation.json")
        fragmentGetImagesInfo.animationView.playAnimation()
        errorSnackbar.setText("There are no available images loaded.Please try again")

        adapter.clear()
        errorSnackbar.show()
    }


}