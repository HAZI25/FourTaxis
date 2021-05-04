package com.example.fourtaxis.models

class RideModel(
    var creatorID: String = "",
    var from: String = "",
    var where: String = "",
    var people: MutableList<String> = mutableListOf(),
    var date: String = "",
    var time: String = ""
)