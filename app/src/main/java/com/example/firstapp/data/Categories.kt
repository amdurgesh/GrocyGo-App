package com.example.firstapp.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Categories(
    @StringRes val stringResourcesId: Int,
   @DrawableRes val imageResourceId: Int
)