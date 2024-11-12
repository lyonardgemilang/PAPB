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

package com.example.inventory

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.inventory.data.InventoryDatabase
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/*
    ItemDaoTest merupakan kelas testing berbasis JUnit untuk ItemDao yang berfungsi
    untuk memverifikasi CRUD di Room.

    Anotasi @RunWith menunjukkan bahwa testing dijalankan dengan JUnit
 */
@RunWith(AndroidJUnit4::class)
class ItemDaoTest {

    private lateinit var itemDao: ItemDao
    private lateinit var inventoryDatabase: InventoryDatabase
    private val item1 = Item(1, "Apples", 10.0, 20)
    private val item2 = Item(2, "Bananas", 15.0, 97)

    /*
        Anotasi @Before menandakan bahwa createDb ini dijalankan sebelum setiap testing.
        Fungsi ini membangun database in-memory untuk testing.
     */
    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        inventoryDatabase = Room.inMemoryDatabaseBuilder(context, InventoryDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        itemDao = inventoryDatabase.itemDao()
    }

    /*
        Anotasi @After menandakan bahwa closeDb akan dijalnkan setelah setiap testing.
        Fungsi ini menutup database
     */
    @After
    @Throws(IOException::class)
    fun closeDb() {
        inventoryDatabase.close()
    }

    /*
        daoInsert_insertsItemIntoDB menguji bahwa insert di ItemDao dapat menyimpan
        item ke database.
     */
    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsItemIntoDB() = runBlocking {
        addOneItemToDb()
        val allItems = itemDao.getAllItems().first()
        assertEquals(allItems[0], item1)
    }

    /*
        daoGetAllItems_returnsAllItemsFromDB menguji bahwa metode getAllItems mengembalikan
        item dengan ID tertentu.
     */
    @Test
    @Throws(Exception::class)
    fun daoGetAllItems_returnsAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        val allItems = itemDao.getAllItems().first()
        assertEquals(allItems[0], item1)
        assertEquals(allItems[1], item2)
    }

    /*
        daoGetItem_returnsItemFromDB menguji bahwa getItem mengembalikan item dengan id tertentu
     */
    @Test
    @Throws(Exception::class)
    fun daoGetItem_returnsItemFromDB() = runBlocking {
        addOneItemToDb()
        val item = itemDao.getItem(1)
        assertEquals(item.first(), item1)
    }

    /*
        daoDeleteItems_deletesAllItemsFromDB menguji bahwa delete menghapus item dari database
     */
    @Test
    @Throws(Exception::class)
    fun daoDeleteItems_deletesAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        itemDao.delete(item1)
        itemDao.delete(item2)
        val allItems = itemDao.getAllItems().first()
        assertTrue(allItems.isEmpty())
    }

    /*
        daoUpdateItems_updatesItemsInDB menguji bahwa update memperbarui data item pada database
     */
    @Test
    @Throws(Exception::class)
    fun daoUpdateItems_updatesItemsInDB() = runBlocking {
        addTwoItemsToDb()
        itemDao.update(Item(1, "Apples", 15.0, 25))
        itemDao.update(Item(2, "Bananas", 5.0, 50))

        val allItems = itemDao.getAllItems().first()
        assertEquals(allItems[0], Item(1, "Apples", 15.0, 25))
        assertEquals(allItems[1], Item(2, "Bananas", 5.0, 50))
    }

    /*
        addOneItemtoDb menambahkan item1 ke database
     */
    private suspend fun addOneItemToDb() {
        itemDao.insert(item1)
    }

    /*
        addTwoItemsToDb menambahkan item1 dan item2 ke database
     */
    private suspend fun addTwoItemsToDb() {
        itemDao.insert(item1)
        itemDao.insert(item2)
    }
}
