package com.ian.payback.image.view.ui.getimagesinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

import com.ian.payback.image.R
import com.ian.payback.image.databinding.ItemImageBinding
import com.ian.payback.image.domain.model.ImageInfo
import com.ian.payback.image.util.setItems
import java.util.*

internal class ImageInfoAdapter(val mListener: OnImageAdapterListener) :

    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val imageInfo: MutableList<ImageInfo> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holderPhotoBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.item_image, parent, false
        )
        return ImageInfoViewHolder(holderPhotoBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ImageInfoViewHolder).onBind(getItem(position))
    }

    private fun getItem(position: Int): ImageInfo {
        return imageInfo[position]
    }

    override fun getItemCount(): Int {
        return imageInfo.size
    }

    fun addData(list: List<ImageInfo>) {
        this.imageInfo.clear()
        this.imageInfo.addAll(list)
        notifyDataSetChanged()
    }
    fun clear() {
        this.imageInfo.clear()

        notifyDataSetChanged()
    }


    inner class ImageInfoViewHolder(private val dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(dataBinding.root) {


        fun onBind(image: ImageInfo) {
             val viewModel = ImageItemViewModel(image)
            val holderImageInfoBinding = dataBinding as ItemImageBinding

            holderImageInfoBinding.viewModel = viewModel


            holderImageInfoBinding.chipGroup.setItems(image.tags)

            itemView.setOnClickListener {
                mListener.gotoDetailPage( image)
            }



        }
    }
}
