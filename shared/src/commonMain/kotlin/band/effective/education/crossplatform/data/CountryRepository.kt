package band.effective.education.crossplatform.data

import band.effective.education.crossplatform.ui.model.CountryCardUI
import band.effective.education.crossplatform.ui.model.toCardsUI

fun get_countries(): List<Country> = CountryMocks

fun get_neighbours(country: Country): List<CountryCardUI>
{
    val maxSize: Int = 5

    return get_countries().
    filter { it.region == country.region && it.id != country.id }.
    take(maxSize).
    toCardsUI()
}