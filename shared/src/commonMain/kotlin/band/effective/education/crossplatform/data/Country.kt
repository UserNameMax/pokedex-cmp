package band.effective.education.crossplatform.data

/**
 * Одна запись каталога.
 *
 * Поля названы так же, как в ответе PokéAPI (`/api/v2/pokemon/{id}` и
 * `/pokemon-species/{id}`), чтобы в В2 сюда встала сериализация без переименований.
 * Единицы там же: рост в дециметрах, вес в гектограммах — переводить в человеческие
 * придётся самим.
 */
data class Country(
    val id: Int,
    val code: String,
    val name: String,
    val capital: String,
    val region: String,
    val popularity: Int,
    val urb_tot: Float,
    val rur_tot: Float,
    val GDP: Long,
)
