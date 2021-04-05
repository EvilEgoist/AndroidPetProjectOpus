package com.android.opus.domain

import com.android.opus.model.WorkPlace

object WorkExperienceMockData {
    fun getResult(): List<WorkPlace>? {
        return listOf(
            WorkPlace(
                123,
                "https://homepages.cae.wisc.edu/~ece533/images/arctichare.png",
                "Мастерская фотографии СВЕТОПИСЬ",
                "Более 500 тыс. человек",
                "iOS Developer",
                "Август 2018 - май 2020",
                null,
                null
            ),
            WorkPlace(
                124,
                "https://homepages.cae.wisc.edu/~ece533/images/pool.png",
                "Мастерская фотографии СВЕТОПИСЬ",
                "Более 500 тыс. человек",
                "iOS Developer",
                "Август 2018 - май 2020",
                null,
                null
            ),
            WorkPlace(
                125,
                "https://homepages.cae.wisc.edu/~ece533/images/fruits.png",
                "Мастерская фотографии СВЕТОПИСЬ",
                "Более 500 тыс. человек",
                "iOS Developer",
                "Август 2018 - май 2020",
                null,
                null
            ),
            WorkPlace(
                125,
                "https://i.kym-cdn.com/photos/images/original/001/482/076/bab.png",
                "Мастерская фотографии СВЕТОПИСЬ",
                "Более 500 тыс. человек",
                "iOS Developer",
                "Август 2018 - май 2020",
                "Гачи мастер",
                null
            )

        )
    }
}