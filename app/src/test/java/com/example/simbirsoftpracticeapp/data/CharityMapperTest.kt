package com.example.simbirsoftpracticeapp.data

import com.example.simbirsoftpracticeapp.data.database.categories.CategoryEntity
import com.example.simbirsoftpracticeapp.data.database.events.CharityEventEntity
import com.example.simbirsoftpracticeapp.domain.entity.CharityEvent
import com.example.simbirsoftpracticeapp.domain.entity.EventMember
import com.example.simbirsoftpracticeapp.domain.entity.FilterCategory
import com.google.gson.Gson
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CharityMapperTest {

    lateinit var gson: Gson

    lateinit var charityMapper: CharityMapper

    @Before
    fun setup() {
        gson = Gson()

        charityMapper = CharityMapper(gson)
    }

    @Test
    fun mapCharityEventWithEmptyPhoneNumbers() {
        val testData = mockk<CharityEvent>(relaxed = true)
        every { testData.phoneNumbers } returns listOf()

        val result = charityMapper.mapCharityEvent(testData)

        assertEquals(result.phoneNumbers, "{\"phone_numbers\":[]}")
    }

    @Test
    fun mapCharityEventWithPhoneNumbers() {
        val testData = mockk<CharityEvent>(relaxed = true)
        every { testData.phoneNumbers } returns listOf("88005553535")

        val result = charityMapper.mapCharityEvent(testData)

        assertEquals(result.phoneNumbers, "{\"phone_numbers\":[\"88005553535\"]}")
    }

    @Test
    fun mapCharityEventWithEmptyMembers() {
        val testData = mockk<CharityEvent>(relaxed = true)
        every { testData.members } returns listOf()

        val result = charityMapper.mapCharityEvent(testData)

        assertEquals(result.members, "{\"events\":[]}")
    }

    @Test
    fun mapCharityEventWithMembers() {
        val testData = mockk<CharityEvent>(relaxed = true)
        every { testData.members } returns listOf(EventMember(0, "test"))

        val result = charityMapper.mapCharityEvent(testData)

        assertEquals(result.members, "{\"events\":[{\"id\":0,\"icon_src\":\"test\"}]}")
    }

    @Test
    fun mapCharityEventEntityWithEmptyPhoneNumbers() {
        val testData = mockk<CharityEventEntity>(relaxed = true)
        every { testData.phoneNumbers } returns "{\"phone_numbers\":[]}"
        every { testData.members } returns "{\"events\":[]}"

        val result = charityMapper.mapCharityEventEntity(testData)

        assertEquals(result.phoneNumbers, listOf<String>())
    }

    @Test
    fun mapCharityEventEntityWithPhoneNumbers() {
        val testData = mockk<CharityEventEntity>(relaxed = true)
        every { testData.phoneNumbers } returns "{\"phone_numbers\":[\"88005553535\"]}"
        every { testData.members } returns "{\"events\":[]}"

        val result = charityMapper.mapCharityEventEntity(testData)

        assertEquals(result.phoneNumbers, listOf("88005553535"))
    }

    @Test
    fun mapCharityEventEntityWithEmptyMembers() {
        val testData = mockk<CharityEventEntity>(relaxed = true)
        every { testData.phoneNumbers } returns "{\"phone_numbers\":[\"88005553535\"]}"
        every { testData.members } returns "{\"events\":[]}"

        val result = charityMapper.mapCharityEventEntity(testData)

        assertEquals(result.members, listOf<EventMember>())
    }

    @Test
    fun mapCharityEventEntityWithMembers() {
        val testData = mockk<CharityEventEntity>(relaxed = true)
        every { testData.phoneNumbers } returns "{\"phone_numbers\":[\"88005553535\"]}"
        every { testData.members } returns "{\"events\":[{\"id\":0,\"icon_src\":\"test\"}]}"

        val result = charityMapper.mapCharityEventEntity(testData)

        assertEquals(result.members, listOf<EventMember>(EventMember(0, "test")))
    }

    @Test
    fun mapCategoryNotEmpty() {
        val testData = FilterCategory(0, "test", false)

        val result = charityMapper.mapCategory(testData)

        assertEquals(result, CategoryEntity(0, "test", false))
    }

    @Test
    fun mapCategoryEntityNotEmpty() {
        val testData = CategoryEntity(0, "test", false)

        val result = charityMapper.mapCategoryEntity(testData)

        assertEquals(result, FilterCategory(0, "test", false))
    }
}