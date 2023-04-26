package com.example.cafe.domain.product

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CosonantTest {

    companion object {
        private val COSONANTS = listOf(
            "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"
        )
    }

    @Test
    fun cosonantExtractTest() {
        val str = "아메리카노"
        val expected = listOf("ㅇ", "ㅁ", "ㄹ", "ㅋ", "ㄴ")

        val intList = str.map {
            (it - 0xAC00).code / 28 / 21
        }

        for (i in intList.indices) {
            Assertions.assertEquals(expected[i], COSONANTS[intList[i]])
        }
    }
}
