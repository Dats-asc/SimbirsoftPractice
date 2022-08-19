package com.example.simbirsoftpracticeapp.data

import com.example.simbirsoftpracticeapp.data.database.categories.CategoryEntity
import com.example.simbirsoftpracticeapp.data.database.events.CharityEventEntity
import com.example.simbirsoftpracticeapp.data.database.events.EventMembersList
import com.example.simbirsoftpracticeapp.data.database.events.PhoneNumbersList
import com.example.simbirsoftpracticeapp.data.entity.CategoriesResponseItem
import com.example.simbirsoftpracticeapp.data.entity.EventsResponseItem
import com.example.simbirsoftpracticeapp.domain.entity.CharityEvent
import com.example.simbirsoftpracticeapp.domain.entity.EventMember
import com.example.simbirsoftpracticeapp.domain.entity.FilterCategory
import com.google.gson.Gson
import javax.inject.Inject

class CharityMapper @Inject constructor(
    private val gson: Gson
) {

    fun mapCharityEvent(event: CharityEvent): CharityEventEntity {
        return CharityEventEntity(
            id = event.id,
            title = event.title,
            date = event.date,
            charitableFoundation = event.charitableFoundation,
            address = event.address,
            phoneNumbers = gson.toJson(PhoneNumbersList(event.phoneNumbers)),
            img1 = event.img1,
            img2 = event.img2,
            img3 = event.img3,
            description = event.description,
            members = gson.toJson(EventMembersList(event.members)),
            membersCount = event.membersCount,
            sourceUrl = event.sourceUrl,
            email = event.email,
            categoryId = event.categoryId,
        )
    }

    fun mapCharityEventEntity(eventEntity: CharityEventEntity): CharityEvent {
        return CharityEvent(
            id = eventEntity.id,
            title = eventEntity.title,
            date = eventEntity.date,
            charitableFoundation = eventEntity.charitableFoundation,
            address = eventEntity.address,
            phoneNumbers = gson.fromJson(
                eventEntity.phoneNumbers,
                PhoneNumbersList::class.java
            ).list,
            img1 = eventEntity.img1,
            img2 = eventEntity.img2,
            img3 = eventEntity.img3,
            description = eventEntity.description,
            members = gson.fromJson(eventEntity.members, EventMembersList::class.java).list,
            membersCount = eventEntity.membersCount,
            sourceUrl = eventEntity.sourceUrl,
            email = eventEntity.email,
            categoryId = eventEntity.categoryId
        )
    }

    fun mapCategory(category: FilterCategory): CategoryEntity {
        return CategoryEntity(
            id = category.id,
            title = category.title,
            isChecked = category.isChecked
        )
    }

    fun mapCategoryEntity(categoryEntity: CategoryEntity): FilterCategory {
        return FilterCategory(
            id = categoryEntity.id,
            title = categoryEntity.title,
            isChecked = categoryEntity.isChecked
        )
    }

    fun mapEventResponse(eventResponse: EventsResponseItem): CharityEvent {
        return CharityEvent(
            id = eventResponse.id,
            title = eventResponse.title,
            date = eventResponse.date,
            charitableFoundation = eventResponse.charitableFoundation,
            address = eventResponse.address,
            phoneNumbers = eventResponse.phoneNumbers,
            img1 = eventResponse.img1,
            img2 = eventResponse.img2,
            img3 = eventResponse.img3,
            description = eventResponse.description,
            members = eventResponse.members.map { memberResponse ->
                EventMember(
                    memberResponse.id,
                    memberResponse.icoSrc
                )
            },
            membersCount = eventResponse.membersCount,
            sourceUrl = eventResponse.sourceUrl,
            email = eventResponse.email,
            categoryId = eventResponse.categoryId
        )
    }

    fun mapCategoryResponse(categoryResponse: CategoriesResponseItem): FilterCategory {
        return FilterCategory(
            id = categoryResponse.id,
            title = categoryResponse.title,
            isChecked = categoryResponse.isChecked
        )
    }
}