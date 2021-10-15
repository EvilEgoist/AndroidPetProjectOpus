package com.android.opus.domain

import com.android.opus.model.Profile
import kotlinx.coroutines.Dispatchers

object ProfileMockData {
    private val skillsInteractor = SkillsInteractor(Dispatchers.Default)
    private val workExperinceInteractor = WorkExperienceInteractor(Dispatchers.Default)

    suspend fun getResult(): Profile {
        return Profile(
            id = 123,
            imageUrl = "https://homepages.cae.wisc.edu/~ece533/images/watch.png",
            username = "Чарльз Дарвин",
            age = "20 лет",
            experience = "3 года 4 месяца",
            level = "Ph.D",
            status = "Человек, решивший растратить хотя бы один час своего времени, еще не дорос до того, чтобы понимать всю ценность жизни",
            selfDescription = "В 1831 году, по окончании университета, Дарвин в качестве натуралиста отправился в кругосветное путешествие на экспедиционном судне королевского флота «Бигль», откуда вернулся в Англию лишь 2 октября 1836 года. Путешествие продолжалось без малого пять лет. Большую часть времени Дарвин проводит на берегу, изучая геологию и собирая коллекции по естественной истории, в то время как «Бигль» под руководством Фицроя осуществлял гидрографическую и картографическую съёмку побережья",
            skills = skillsInteractor.loadSkills(),
            workExperience = workExperinceInteractor.loadWorkPlaces()
        )
    }
}