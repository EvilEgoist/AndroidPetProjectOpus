package com.android.opus.domain

import com.android.opus.model.Skill
import com.android.opus.model.Vacancy

object VacanciesMockData {
    fun getResult(): List<Vacancy>? {
        val vacancy = Vacancy(
            123,
            "IOS Developer",
            "Пышки -баранки",
            "10 000 - 20 000 руб",
            "Ищем разработчика в основную команду. Все требования кандидатам в личку, если у вас нет реального опыта работы, то пропускайте  личку, если у вас нет реального опыта работы, то пропускайте",
            "https://homepages.cae.wisc.edu/~ece533/images/airplane.png",
            listOf(Skill(12, "ios"))
        )
        val vacancy2 = Vacancy(
            124,
            "IOS Developer",
            "Пышки -баранки",
            "10 000 - 20 000 руб",
            "Ищем разработчика в основную команду. Все требования кандидатам в личку, если у вас нет реального опыта работы, то пропускайте  личку, если у вас нет реального опыта работы, то пропускайте",
            "https://homepages.cae.wisc.edu/~ece533/images/airplane.png",
            listOf(Skill(12, "ios"))
        )
        val vacancy3 = Vacancy(
            125,
            "IOS Developer",
            "Пышки -баранки",
            "10 000 - 20 000 руб",
            "Ищем разработчика в основную команду. Все требования кандидатам в личку, если у вас нет реального опыта работы, то пропускайте  личку, если у вас нет реального опыта работы, то пропускайте",
            "https://homepages.cae.wisc.edu/~ece533/images/airplane.png",
            listOf(Skill(12, "ios"))
        )

        return listOf(vacancy, vacancy2, vacancy3)
    }
}