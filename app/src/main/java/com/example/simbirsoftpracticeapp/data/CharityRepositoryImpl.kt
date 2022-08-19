package com.example.simbirsoftpracticeapp.data

import android.util.Log
import com.example.simbirsoftpracticeapp.data.database.categories.CategoriesDao
import com.example.simbirsoftpracticeapp.data.database.events.EventsDao
import com.example.simbirsoftpracticeapp.domain.entity.CharityEvent
import com.example.simbirsoftpracticeapp.domain.entity.CharityEvents
import com.example.simbirsoftpracticeapp.domain.entity.FilterCategories
import com.example.simbirsoftpracticeapp.domain.entity.FilterCategory
import com.example.simbirsoftpracticeapp.domain.repository.CharityRepository
import com.example.simbirsoftpracticeapp.presentation.search.SearchResult
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CharityRepositoryImpl @Inject constructor(
    private val charityApi: CharityApi,
    private val categoriesDao: CategoriesDao,
    private val eventsDao: EventsDao,
    private val mapper: CharityMapper
) : CharityRepository {

    override fun getCategories(): Single<FilterCategories> {
        val categoriesByApi = charityApi.getCategories()
            .delay(1000, TimeUnit.MILLISECONDS)
            .map { categoriesList ->
                FilterCategories(categoriesList.map { categoryResponse ->
                    mapper.mapCategoryResponse(
                        categoryResponse
                    )
                })
            }
        val categoriesByDb = categoriesDao.getAll()
            .delay(100, TimeUnit.MILLISECONDS)
            .map { categoryEntityList ->
                FilterCategories(categoryEntityList.map { categoryEntity ->
                    mapper.mapCategoryEntity(
                        categoryEntity
                    )
                })
            }
        return categoriesByDb
            .toObservable()
            .filter { categories ->
                categories.categories.isNotEmpty()
            }
            .singleOrError()
            .onErrorResumeWith(categoriesByApi)
            .doOnSuccess { categoriesList ->
                categoriesDao.insertAll(categoriesList.categories.map { filterCategory ->
                    mapper.mapCategory(filterCategory)
                })
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        { Log.e("Saving data: ", "categories saved") },
                        { e -> Log.e("Saving categories", e.message.orEmpty()) })
            }
    }

    override fun getEventById(id: Int): Single<CharityEvent> {
        val eventByApi = charityApi.getEventById(id)
            .delay(500, TimeUnit.MILLISECONDS)
            .map { eventsList ->
                mapper.mapEventResponse(eventsList.first())
            }
        val eventByDb = eventsDao.getById(id)
            .delay(100, TimeUnit.MILLISECONDS)
            .map { eventEntity -> mapper.mapCharityEventEntity(eventEntity) }
        return eventByDb
            .toObservable()
            .singleOrError()
            .onErrorResumeWith(eventByApi)
            .doOnSuccess { event ->
                eventsDao.insertAll(listOf(mapper.mapCharityEvent(event)))
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        { Log.e("", "Event saved") },
                        { e -> Log.e("", e.message.orEmpty()) }
                    )
            }
    }

    override fun getEvents(): Single<CharityEvents> {
        val eventsByApi = charityApi.getEvents()
            .delay(2000, TimeUnit.MILLISECONDS)
            .map { eventsList ->
                CharityEvents(eventsList.map { eventResponse ->
                    mapper.mapEventResponse(
                        eventResponse
                    )
                })
            }
        val eventByDb = eventsDao.getAll()
            .delay(500, TimeUnit.MILLISECONDS)
            .map { eventsEntityList ->
                CharityEvents(eventsEntityList.map { eventEntity ->
                    mapper.mapCharityEventEntity(
                        eventEntity
                    )
                })
            }

        return eventByDb
            .toObservable()
            .filter { events -> events.events.isNotEmpty() }
            .singleOrError()
            .onErrorResumeWith(eventsByApi)
            .doOnSuccess { events ->
                eventsDao.insertAll(events.events.map { event ->
                    mapper.mapCharityEvent(
                        event
                    )
                })
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        { Log.e("", "Events saved") },
                        { e -> Log.e("", e.message.orEmpty()) })
            }
    }

    override fun updateCategories(categories: List<FilterCategory>) {
        categoriesDao.updateAll(categories.map { category -> mapper.mapCategory(category) })
            .subscribeOn(Schedulers.io())
            .subscribe(
                { Log.e("", "Categories updated") },
                { e -> Log.e("Update categories", e.message.orEmpty()) }
            )
    }

    override fun searchEvents(request: String): Single<List<CharityEvent>> {
        return charityApi.searchEvents(request)
            .map { eventsList ->
                eventsList.map { eventResponse ->
                    mapper.mapEventResponse(
                        eventResponse
                    )
                }
            }
    }
}