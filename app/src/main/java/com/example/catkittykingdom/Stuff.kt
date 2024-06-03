package com.example.catkittykingdom

data class Stuff(val questions: List<Question?>, val effect: List<Int?>) {
    var i = 0
    var prompt = questions.get(i)?.prompt
    var answer1 = questions.get(i)?.choices?.get(0)
    var answer2 = questions.get(i)?.choices?.get(1)
    var answer3 = questions.get(i)?.choices?.get(2)

    var w1 = effect?.get(0)
    var ar1 = effect?.get(1)
    var w2 = effect?.get(2)
    var ar2 = effect?.get(3)
    var w3 = effect?.get(4)
    var ar3 = effect?.get(5)
}
