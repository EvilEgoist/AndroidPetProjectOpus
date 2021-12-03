package com.android.opus.domain

import com.android.opus.model.ExpandedVacancy
import com.android.opus.model.ShortDescription
import com.android.opus.model.Skill

object ExpandedVacancyMockData {

    suspend fun getResult(): ExpandedVacancy {
        return ExpandedVacancy(
            id = 123,
            titleVacancy = "IOS Developer",
            employerName = "Мастерская фотографии СВЕТОПИСЬ",
            salary = "10 000 - 20 000 руб",
            description = "Ищем разработчика в основную команду. Все требования кандидатам в личку, если у вас нет реального опыта работы, то пропускайте  личку, если у вас нет реального опыта работы, то пропускайте",
            workFullDay = true,
            level = "Senior",
            skills = listOf(
                Skill(12, "iOS"),
                Skill(12, "Администрирование"),
                Skill(12, "Android"),
                Skill(12, "Администрирование")
            ),
            companyDescr = "Амбициозный стартап в сфере музыки и обработки звука. Мы собираем команду лучших из лучших, для того чтобы в кратчайшие сроки реализовать уникальный продукт, аналогов которому еще нет на рынке.",
            location = "Санкт-Петербург",
            typeOfEmployment = "Полный рабочий день",
            responsibilities = listOf(
                ShortDescription(
                    1,
                    "Настройка и сопровождение проектов, приложений и систем мониторинга;"
                ),
                ShortDescription(2, "Внедрение практик и инструментов DevOps;"),
                ShortDescription(
                    3,
                    "Взаимодействие с отделами тестирования, безопасности и разработки;"
                ),
                ShortDescription(4, "Построение процессов CI/CD;"),
            ),
            requirements = listOf(
                ShortDescription(1, "Знания дистрибутивов: CentOS, Debian, Ubuntu;"),
                ShortDescription(2, "Опыт работы с zabbix, prometheus;"),
                ShortDescription(3, "Опыт работы с git;"),
                ShortDescription(
                    4,
                    "Опыт настройки и сопровождение веб-серверов (LNAMP), в том числе, высоконагруженные и распределенные системы;"
                ),
            ),
            conditions = listOf(
                ShortDescription(1, "Уютный офис с удобным расположением"),
                ShortDescription(2, "Гибкий график;"),
                ShortDescription(3, "ДМС"),
                ShortDescription(4, "Внимание ко всем вашим пожеланиям"),
            )
        )
    }
}