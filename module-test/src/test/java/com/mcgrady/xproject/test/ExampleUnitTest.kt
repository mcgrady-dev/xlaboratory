package com.mcgrady.xproject.test

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private lateinit var mockList: MutableList<String>

    @Before
    fun setUp() {
        mockList = mock(MutableList::class.java) as MutableList<String>
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun mockTest() {

        mockList.add("one")
        mockList.clear()

        verify(mockList).add("one")
        verify(mockList).clear()
    }
}