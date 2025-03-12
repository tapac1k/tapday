package com.tapac1k.day.data

import com.tapac1k.day.domain.DayService
import javax.inject.Inject

class DayServiceImpl @Inject constructor(

): DayService {
    override fun getCurrentDay(): Long {
        return System.currentTimeMillis() / 1000 / 60 / 60 / 24
    }
}