package com.example.catkittykingdom

data class Stuff(val questions: List<Question>) {
    var i = 0
    var prompt = questions.get(i).prompt
    var answer1 = questions.get(i).choices.get(0)
    var answer2 = questions.get(i).choices.get(1)
    var answer3 = questions.get(i).choices.get(2)
}
