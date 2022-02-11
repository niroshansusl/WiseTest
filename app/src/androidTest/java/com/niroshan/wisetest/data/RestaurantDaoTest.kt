package com.niroshan.wisetest.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.codinginflow.simplecachingexample.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class RestaurantDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database : RestaurantDatabase
    private lateinit var dao : RestaurantDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RestaurantDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.restaurantDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertRestaurantsTest() = runBlockingTest {
        val restaurant1 = Restaurant("Restaurent1", "type1", "image1", "address1")
        val restaurant2 = Restaurant("Restaurent2", "type2", "image2", "address2")
        val restaurant3 = Restaurant("Restaurent3", "type3", "image3", "address3")
        dao.insertRestaurants(listOf(restaurant1, restaurant2, restaurant3))

        val allRestaurant = dao.getAllRestaurants().asLiveData().getOrAwaitValue()

        assertThat(allRestaurant.count()).isEqualTo(3)
    }

    @Test
    fun deleteRestaurantsTest() = runBlockingTest {
        val restaurant1 = Restaurant("Restaurent1", "type1", "image1", "address1")
        dao.insertRestaurants(listOf(restaurant1))
        dao.deleteAllRestaurants()

        val allRestaurant = dao.getAllRestaurants().asLiveData().getOrAwaitValue()

        assertThat(allRestaurant).doesNotContain(restaurant1)
    }

}