package com.niroshan.wisetest.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.room.withTransaction
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.codinginflow.simplecachingexample.getOrAwaitValue
import com.google.common.truth.Truth
import com.niroshan.wisetest.api.RestaurantApi
import com.niroshan.wisetest.util.networkBoundResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class RestaurantRepositoryTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database : RestaurantDatabase
    private lateinit var dao : RestaurantDao
    private lateinit var api: RestaurantApi

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
    fun getRestaurantSuccessFullyFromDBTest() = runBlockingTest {

            val restaurant1 = Restaurant("Restaurent1", "type1", "image1", "address1")
            dao.insertRestaurants(listOf(restaurant1))

            val result = networkBoundResource(
                query = {
                    dao.getAllRestaurants().asLiveData().getOrAwaitValue().asFlow()
                },
                fetch = {
                    api.getRestaurants()
                },
                saveFetchResult = { restaurants ->
                    database.withTransaction {
                        dao.deleteAllRestaurants()
                        dao.insertRestaurants(restaurants)
                    }
                }
            )

        Truth.assertThat(result.count()).isEqualTo(1)
        }

    @Test
    fun getRestaurantNoSavedValuesInDBTest() = runBlockingTest {

        val result = networkBoundResource(
            query = {
                dao.getAllRestaurants().asLiveData().getOrAwaitValue().asFlow()
            },
            fetch = {
                api.getRestaurants()
            },
            saveFetchResult = { restaurants ->
                database.withTransaction {
                    dao.deleteAllRestaurants()
                    dao.insertRestaurants(restaurants)
                }
            }
        )

        Truth.assertThat(result.catch { it.cause })
    }
}