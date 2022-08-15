package com.example.simbirsoftpracticeapp.data.database.events

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface EventsDao {
    @Query("SELECT * FROM charity_events")
    fun getAll(): Single<List<CharityEventEntity>>

    @Query("SELECT * FROM charity_events WHERE id=:eventId")
    fun getById(eventId: Int): Maybe<CharityEventEntity>

    @Insert
    fun insertAll(events: List<CharityEventEntity>): Completable

    @Query("DELETE FROM charity_events WHERE id=:eventId")
    fun deleteById(eventId: Int): Completable
}