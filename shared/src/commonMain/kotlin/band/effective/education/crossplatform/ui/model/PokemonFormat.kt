package band.effective.education.crossplatform.ui.model

/** Номер по национальному покедексу: 1 показывается как 001. */
internal fun displayNumber(id: Int): String = id.toString().padStart(3, '0')

/** Имена в данных PokeAPI записаны строчными: bulbasaur, а не Bulbasaur. */
internal fun displayName(name: String): String = name.replaceFirstChar { it.uppercase() }
