package com.example.numbertesttask2.numbers.data.cache

import com.example.numbertesttask2.numbers.data.NumberData
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class NumbersCacheDataSourceTest {

    @Test
    fun test_all_numbers_empty() = runBlocking {
        val dao = TestDao()
        val dataSource = NumbersCacheDataSource.Base(dao = dao, dataToCache = TestMapper(5))

        val actual = dataSource.allNumbers()
        val expected = emptyList<NumberCache>()
        assertEquals(expected, actual)
    }

    @Test
    fun test_all_numbers_not_empty() = runBlocking {
        val dao = TestDao()
        val dataSource = NumbersCacheDataSource.Base(dao = dao, dataToCache = TestMapper(5))

        dao.data.add(NumberCache("1", "fact1", 1))
        dao.data.add(NumberCache("2", "fact2", 2))

        val actualList = dataSource.allNumbers()
        val expectedList = listOf(
            NumberData("1", "fact1"),
            NumberData("2", "fact2")
        )
        actualList.forEachIndexed { index, actual ->
            assertEquals(expectedList[index], actual)
        }
    }

    @Test
    fun test_contains() = runBlocking {
        val dao = TestDao()
        val dataSource = NumbersCacheDataSource.Base(dao = dao, dataToCache = TestMapper(5))

        dao.data.add(NumberCache("6", "fact6", 6))

        val actual = dataSource.contains("6")
        val expected = true
        assertEquals(expected, actual)
    }

    @Test
    fun test_not_contains() = runBlocking {
        val dao = TestDao()
        val dataSource = NumbersCacheDataSource.Base(dao = dao, dataToCache = TestMapper(5))

        dao.data.add(NumberCache("7", "fact7", 7))

        val actual = dataSource.contains("6")
        val expected = false
        assertEquals(expected, actual)
    }

    @Test
    fun test_save() = runBlocking {
        val dao = TestDao()
        val dataSource = NumbersCacheDataSource.Base(dao, TestMapper(8))

        dataSource.saveNumber(NumberData("8", "fact8"))
        assertEquals(NumberCache("8", "fact8", 8), dao.data[0])
    }

    @Test
    fun test_number_contains() = runBlocking {
        val dao = TestDao()
        val dataSource = NumbersCacheDataSource.Base(dao, TestMapper(9))

        dao.data.add(NumberCache("10", "fact10", 10))

        val actual = dataSource.number("10")
        val expected = NumberData("10", "fact10")
        assertEquals(expected, actual)
    }

    @Test
    fun test_number_not_exist() = runBlocking {
        val dao = TestDao()
        val dataSource = NumbersCacheDataSource.Base(dao, TestMapper(9))

        dao.data.add(NumberCache("10", "fact10", 10))

        val actual = dataSource.number("32")
        val expected = NumberData("", "")
        assertEquals(expected, actual)
    }
}

private class TestDao : NumbersDao {

    val data = mutableListOf<NumberCache>()

    override fun allNumbers(): List<NumberCache> {
        return data
    }

    override fun insert(number: NumberCache) {
        data.add(number)
    }

    override fun update(number: NumberCache) {

    }

    override fun number(number: String): NumberCache? {
        return data.find { it.number == number }
    }

}

private class TestMapper(private val date: Long) : NumberData.Mapper<NumberCache> {
    override fun map(id: String, fact: String): NumberCache {
        return NumberCache(id, fact, date)
    }
}