package band.effective.education.crossplatform.navigation


sealed interface AppRoute {
    data object CountryList : AppRoute
    data class CountryDetail(val countryId: Int) : AppRoute
}
