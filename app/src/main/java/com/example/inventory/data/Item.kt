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

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
    Anotasi @Entity menandakan bahwa kelas Item ini merupakan entitas
    dalam Room Database. items merupakan nama tabel yang akan
    dibuat dalam database untuk menyimpan entitas Item.

    Data class Item mewakili satu baris data dalam tabel items. Properti seperti
    id, name, price, quantity akan menjadi kolom dalam tabel items.
 */
@Entity(tableName = "items")
data class Item(
    /*
        @Anotasi PrimaryKey menandakan bahwa id merupakan primary key untuk
        items dan bersifat unik. autoGenerate = true memungkinkan Room
        menghasilkan ID unik untuk setiap entri baru.

        name di sini mewakili nama dari item yang ditentukan sebagai kolom string,
        price mewakili harga per unit dari item yang ditentukan sebagai kolom double,
        dan quantity mewakili banyaknya unit yang tersedia yang ditentukan sebagai kolom integer
     */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: Double,
    val quantity: Int
)
