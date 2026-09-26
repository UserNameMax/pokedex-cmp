package band.effective.education.crossplatform.ui.model

import band.effective.education.crossplatform.data.Country

data class CountryCardUI(
    val id: Int,
    val code: String,
    val name: String,
    val popularity: Int,
    val GDP: Long,
)
fun Country.toCardUI(): CountryCardUI = CountryCardUI(
    code = code,
    name = name,
    popularity = popularity,
    id = id,
    GDP = GDP
)

fun List<Country>.toCardsUI(): List<CountryCardUI> = map { it.toCardUI() }