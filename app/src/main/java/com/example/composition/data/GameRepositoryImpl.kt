package com.example.composition.data

import com.example.composition.domain.entities.GameSettings
import com.example.composition.domain.entities.Level
import com.example.composition.domain.entities.Question
import com.example.composition.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, maxSumValue)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = max(rightAnswer - countOfOptions, MIN_ANSWER_VALUE)
        val to = min(rightAnswer + countOfOptions, maxSumValue)
        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum = sum, visibleNumber = visibleNumber, options = options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
       return when (level) {
            Level.TEST -> GameSettings(
                maxSumValue = 10,
                minCountOfRightAnswers = 3,
                minPercentOfRightAnswers = 50,
                gameTimeInSeconds = 8
            )
            Level.EASY -> GameSettings(
                maxSumValue = 10,
                minCountOfRightAnswers = 10,
                minPercentOfRightAnswers = 70,
                gameTimeInSeconds = 60
            )
            Level.NORMAL -> GameSettings(
                maxSumValue = 20,
                minCountOfRightAnswers = 20,
                minPercentOfRightAnswers = 80,
                gameTimeInSeconds = 40
            )
            Level.HARD -> GameSettings(
                maxSumValue = 30,
                minCountOfRightAnswers = 30,
                minPercentOfRightAnswers = 90,
                gameTimeInSeconds = 40
            )
        }
    }
}