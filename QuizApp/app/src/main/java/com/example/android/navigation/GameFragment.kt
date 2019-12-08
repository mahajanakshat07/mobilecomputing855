package com.example.android.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.android.navigation.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    // A class question with Question name as a String and Answers as the list opf Strings.
    data class Question(
            val text: String,
            val answers: List<String>)

    // The first answer is the correct one. So need to shuffle the answers before showing it on the screen.
    // Creating a Mutuable list of objects of Question Class and initializing their values
    private val questions: MutableList<Question> = mutableListOf(
            Question(text = "In which year did Maradona score a goal with his hand?",
                    answers = listOf("1986","1983","1988","1978")),
            Question(text = "Which city became capital of West-Germany in 1949?",
                    answers = listOf("Bonn", "Munich", "Stuttgart", "Berlin")),
            Question(text = "How many stars has the American flag got?",
                    answers = listOf("50", "48", "46", "52")),
            Question(text = "How many oscars did the Titanic movie got?",
                    answers = listOf("11", "10", "15", "9")),
            Question(text = "Which country did Spain beat to win davis cup 2019 ?",
                    answers = listOf("Canada", "Australia", "Serbia", "Belgium")),
            Question(text = "In Australian football, what is the maximum number of players allowed on the field at a time?",
                    answers = listOf("36", "42", "32", "38")),
            Question(text = "Who does Phoebe marry in FRIENDS ?",
                    answers = listOf("Mike", "Joey", "Ross", "Chandler")),
            Question(text = "What player was the first to win five straight Wimbledon tennis titles?",
                    answers = listOf("Bjon Borg", "Roger Federer", "Andre Aggasi", "Rafael Nadal")),
            Question(text = "How many Rings are on Olympoic logo",
                    answers = listOf("5", "7", "4", "3")),
            Question(text = "Who won 2019 UEFA Champions League?",
                    answers = listOf("Liverpool", "Real Madrid", "Barcelona", "Bayern Munich"))
    )

    lateinit var currentQuestion: Question //to get the value of question and tell the compiler its value is not null
    lateinit var answers: MutableList<String> // to fetch the answers
    private var questionIndex = 0
    private val numQuestions = 10  //number of questions i want in the application

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
                inflater, R.layout.fragment_game, container, false)

        // Calling the function to shuffle the questions.
        randomizeQuestions()

        // Bind this fragment class to the layout
        binding.game = this

        // Set the onClickListener for the submitButton
        binding.submitButton.setOnClickListener()
        { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId)
            {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }
                // I have set the first answer as correct answer so if answers matches it will advance to next question
                if (answers[answerIndex] == currentQuestion.answers[0])
                {
                    questionIndex++
                    if (questionIndex < numQuestions)
                    {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        //invalidateAll on the FragmentGameBinding updates the data
                        binding.invalidateAll()
                    }
                    else
                    {
                        //Game is won. Redirecting user to game won page.
                        Navigation.findNavController(view).navigate(R.id.action_gameFragment_to_gameWonFragment)
                    }
                }
                else
                {
                    //User has answered a question wrongly. Redirecting user to game lost page.
                    Navigation.findNavController(view).navigate(R.id.action_gameFragment_to_gameOverFragment2)
                }
            }
        }
        return binding.root
    }

    // randomize the questions and set the first question
    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    // Sets the first question on the screen and shuffles the answers.
    private fun setQuestion()
    {
        currentQuestion = questions[questionIndex]
        // randomize the answers into a new mutuable list
        answers = currentQuestion.answers.toMutableList()

        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions)
    }
}
