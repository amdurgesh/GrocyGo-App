package com.example.firstapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InternetItem(
    @SerialName("stringResourceId") val itemName: String = "",
    @SerialName("itemCategoryId") val itemCategory: String = "",
    @SerialName("itemQuantity") val itemQuantity: String = "",
    @SerialName("item_price") val itemPrice: Int = 0,
    @SerialName("imageResourceId") val imageUrl: String = ""
)


