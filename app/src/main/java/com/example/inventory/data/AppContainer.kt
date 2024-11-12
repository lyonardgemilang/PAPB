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

package com.example.inventory.data

import android.content.Context

/*
    AppContainer merupakan interface yang mendefinisikan itemsRepository.
    Dengan digunakan interface ini dependensi yang diperlukan oleh aplikasi
    dapat disusun secara modular (terpisah).
 */
interface AppContainer {
    val itemsRepository: ItemsRepository
}

/*
    AppDataContainer merupakan implementasi dari AppContainer yang menyediakan
    instance dari OfflineItemsRepository, yang berfungsi untuk menyediakan dependensi
    yang dibutuhkan terutama itemsRepository yang akan digunakan di seluruh aplikasi.
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /*
        itemsRepository diinisialisasi menggunakan lazy yang berarti akan diinisialisasi
        hanya pada saat pertama kali digunakan

        OfflineItemsRepository diinisialisasi dengan menggunakan DAO dari InventoryDatabase
        InventoryDatabase.getDatabase(context) menyediakan instance singleton dari Room
        dan itemDao() mengembalikan instance DAO untuk Item.
     */
    override val itemsRepository: ItemsRepository by lazy {
        OfflineItemsRepository(InventoryDatabase.getDatabase(context).itemDao())
    }
}
