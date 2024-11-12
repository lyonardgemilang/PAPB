/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.inventory.data.Item
import com.example.inventory.data.ItemsRepository
import java.text.NumberFormat

/*
    ItemEntryViewModel bertanggung jawab untuk memvalidasi dan menyimpan
    item baru ke dalam Room melalui ItemsRepository.
 */
class ItemEntryViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {

    var itemUiState by mutableStateOf(ItemUiState())
        private set

    /*
        updateUiState memperbarui itemUiState dengan nilai itemDetails yang
        diberikan sebagai argumen. Fungsi ini juga memvaliadsi input agar data
        yang dimasukkan memenuhi syarat.
     */
    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    /*
        saveItem menyimpan item baru ke dalam database dengan memvalidasi input
        terlebih dahulu.
     */
    suspend fun saveItem() {
        if (validateInput()) {
            itemsRepository.insertItem(itemUiState.itemDetails.toItem())
        }
    }

    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
    }
}

/*
    data class ItemUiState mewakili status UI untuk entri item baru
 */
data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean = false
)

/*
    data class ItemDetails menyimpan informasi detail dari item
 */
data class ItemDetails(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
)

/*
    fungsi toItem mengonversi ItemDetails ke Item
    Apabila price bukan nilai Double yang valid, maka nilai diatur
    menjadi 0.0. Apabila quantity bukan int yang valid, maka nilai
    diatur menjadi 0.
 */
fun ItemDetails.toItem(): Item = Item(
    id = id,
    name = name,
    price = price.toDoubleOrNull() ?: 0.0,
    quantity = quantity.toIntOrNull() ?: 0
)

/*
    formatedPrice untuk Item mengonversi price menjadi format mata uang
 */
fun Item.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}

/*
    toItemUiState mengonversi Item menjadi ItemUiState
 */
fun Item.toItemUiState(isEntryValid: Boolean = false): ItemUiState = ItemUiState(
    itemDetails = this.toItemDetails(),
    isEntryValid = isEntryValid
)

/*
    toItemDetails mengonversi Item menjadi ItemDetails.
 */
fun Item.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    name = name,
    price = price.toString(),
    quantity = quantity.toString()
)
