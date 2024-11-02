package app.ditodev.ceritain.utils

import org.junit.Assert
import org.junit.Test
import java.time.format.DateTimeParseException


class UtilsTest {
    @Test
    fun `given correct ISO 8601 format then should format correctly`() {
        val currentDate = "2024-11-02T22:20:10Z"
        Assert.assertEquals("03 Nov 2024 | 05:20", Utils.formatDate(currentDate, "Asia/Jakarta"))
        Assert.assertEquals("03 Nov 2024 | 06:20", Utils.formatDate(currentDate, "Asia/Makassar"))
    }

    @Test
    fun `given WRONG ISO 8601 then should throw error`() {
        val wrongFormat = "2024-02-11T10:10"
        Assert.assertThrows(DateTimeParseException::class.java) {
            Utils.formatDate(wrongFormat, "Asia/Jakarta")
        }
    }
}