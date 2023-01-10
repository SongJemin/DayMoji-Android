package com.songjem.data.mapper

import com.songjem.data.datasource.local.model.TestEntity
import com.songjem.domain.model.TestItem

fun mapperToTest(testEntities: List<TestEntity>): List<TestItem> {
    return testEntities.toList().map {
        TestItem(
            it.id,
            it.testVal!!
        )
    }
}