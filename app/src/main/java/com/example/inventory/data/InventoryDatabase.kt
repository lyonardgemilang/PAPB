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
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
    Anotasi @Database menandakan bahwa kelas InventoryData merupakan database Room.
    Di dalam fungsi ini juga didefinisikan entitities beserta version. Entities yang digunakan adalah
    Item dan versionnya adalah 1. Version disini berguna untuk mengelola migrasi data.
 */
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    /*
        Fungsi abstrak itemDao() berfungsi untuk mendefinisikan akses ke objek DAO untuk Item.
        Room akan menghasilkan implementasi dari ItemDao yang menyediakan fungsi-fungsi seperti CRUD.
     */
    abstract fun itemDao(): ItemDao

    /*
        Objek companion di sini digunakan untuk mengimplementasikan singleton, yang memastikan bahwa
        hanya ada satu instance yaitu InventoryDatabase pada seluruh aplikasi/
     */
    companion object {
        @Volatile
        private var Instance: InventoryDatabase? = null
        /*
            Fungsi getDatabase befungsi untuk mengakses atau membuat instance InventoryDatabase
            Jika instance sudah ada, maka metode ini akan mengembalikan instance berikut, apabila
            masih null, maka instance baru akan dibuat.
         */
        fun getDatabase(context: Context): InventoryDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    /*
                        Opsi fallbackToDesctructiveMigration merupakan opsi untuk menghapus seluruh data
                        apabila migrasi terjadi tanpa rute migrasi yang didefinisikan.
                     */
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
