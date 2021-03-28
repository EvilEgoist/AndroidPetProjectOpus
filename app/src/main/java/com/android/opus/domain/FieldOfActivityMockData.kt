package com.android.opus.domain

import com.android.opus.model.FieldOfActivity

object FieldOfActivityMockData {
    fun getResult(): List<FieldOfActivity>? {
        return listOf(FieldOfActivity(1,"Администрирование", false),
            FieldOfActivity(2,"Офис", false),
            FieldOfActivity(3,"Аналитика", false),
            FieldOfActivity(4,"Поддержка", false),
            FieldOfActivity(54,"Бэкенд", false),
            FieldOfActivity(5,"Приложения", false),
            FieldOfActivity(6,"Дизайн", false),
            FieldOfActivity(7,"Продажи", false),
            FieldOfActivity(8,"Информационная безопасность", false),
            FieldOfActivity(9,"Разработка ПО", false),
            FieldOfActivity(10,"Кадры", false),
            FieldOfActivity(11,"Телеком", false),
            FieldOfActivity(12,"Контент", false),
            FieldOfActivity(13,"Тестирование", false),
            FieldOfActivity(14,"Маркетинг", false),
            FieldOfActivity(15,"Фронтенд", false),
            FieldOfActivity(16,"Менеджмент", false),
            FieldOfActivity(17,"Другое", false))
    }
}
