package com.ian.payback.image.util
import com.ian.payback.image.R
import android.content.Context
import android.content.res.ColorStateList
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


fun ChipGroup.setItems(listOTags: String) {
    this.removeAllViews()
    if (this.childCount > 0)
        return;
    val context: Context = this.context
    val listOTagsSplit = listOTags.split(",")
    for (value in listOTagsSplit) {
        val chip = Chip(context)
        chip.text = value

        chip.chipStrokeColor = ColorStateList.valueOf(
            context.resources.getColor(R.color.greybg)
        )
        chip.setTextColor(context.resources.getColor(R.color.white))
        chip.setChipBackgroundColorResource(R.color.purple_500)


        this.addView(chip)
    }
}

