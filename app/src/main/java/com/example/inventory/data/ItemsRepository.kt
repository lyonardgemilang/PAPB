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

import kotlinx.coroutines.flow.Flow

/*
    ItemsRepository merupakan interface yang mengelola entitas Item
    dalam aplikasi termasuk operasi CRUD. Repo ini membantu menjaga
    pemisahan tanggung jawab karena bertindak sebagai layer
    antara data dan layer lain dalam aplikasi.
 */
interface ItemsRepository {
    /*
        getAllItemsStream merupakan fungsi untuk mendapatkan flow semua
        item dari sumber data. Flow di sini digunakan agar aplikasi dapat
        memantau perubahan data secara real time.

        getItemStream merupakan fungsi untuk mendapatkan flow data item
        sesuai id.

        insertItem merupakan fungsi untuk menambahkan item baru ke sumber data.
        deleteItem merupakan fungsi untuk menghapus item dari sumber data.
        updateItem merupakan fungsi untuk memperbarui item pada sumber data.
     */
    fun getAllItemsStream(): Flow<List<Item>>

    fun getItemStream(id: Int): Flow<Item?>

    suspend fun insertItem(item: Item)

    suspend fun deleteItem(item: Item)

    suspend fun updateItem(item: Item)
}
