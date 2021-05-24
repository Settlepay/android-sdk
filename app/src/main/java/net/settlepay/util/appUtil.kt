package net.settlepay.util

fun getRandomNumber(): String{
    return (0..99999999).random().toString()
}

fun getRandomPrice(): String{
    return (0..1000).random().toString()
}