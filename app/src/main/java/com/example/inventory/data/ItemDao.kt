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

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/*
    Anotasi @Dao menandakan bahwa ItemDao merupakan interface Data Access Object
    untuk Room. DAO menyediakan fungsi-fungsi yang memetakan operasi database,
    seperti CRUD.
 */
@Dao
interface ItemDao {

    /*
        Anotasi @Query berguna untuk membuat query SQL.
        getAllItems berfungsi untuk mendapatkan semua entri Item dari items,
        getItem berfungsi untuk mendapatkan entri Item berdasarkan id
     */
    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>

    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    /*
        Anotasi @Insert dengan OnConflictStrategy.IGNORE yang berguna untuk
        memgabaikan item baru tanpa mengubah data yang ada apabila terdapat
        konflik seperti item id yang sama sudah ada.

        insert berguna untuk menambahkan entri Item baru ke dalam tabel,
        update berguna untuk memperbarui entri Item yang ada pada tabel.
        dan delete berguna untuk menghapus entri Item yang ada pada tabel

        Untuk setiap fungsi CRUD digunakan anotasi seperti @Update, @Delete sehingga
        secara otomatis menjalankan query update dan delete tanpa perlu
        menuliskan query sql secara manual.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)
}
