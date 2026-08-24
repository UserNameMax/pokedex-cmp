package band.effective.education.crossplatform.ui.screens.list.model

import band.effective.education.crossplatform.ui.model.PokemonCardUi

/**
 * Состояние экрана списка — **всё**, что нужно нарисовать, одним объектом.
 *
 * Правило, ради которого это так: экран не спрашивает ни у кого «а как там дела».
 * Он получает состояние и рисует его. Если чего-то на экране не видно в этом
 * классе — значит оно нарисовано мимо архитектуры.
 *
 * Сумма, а не набор полей: у загрузки и ошибки нет ни списка, ни поиска, и
 * нарисовать поиск поверх спиннера экран просто не сможет — такого значения нет.
 */
sealed interface PokemonListState {

    /** Показывать пока нечего — первая страница ещё не пришла. */
    data object Loading : PokemonListState

    /** Первая страница не загрузилась, показывать нечего. */
    data class Error(val message: String) : PokemonListState

    /**
     * Список загружен. Пустой [items] — «ничего не нашлось» или пустой каталог:
     * это не отдельное состояние, потому что поиск должен остаться на экране,
     * иначе запрос без совпадений нечем было бы поправить.
     */
    data class Success(
        val query: String,
        val items: List<PokemonCardUi>,
        val isLoadingMore: Boolean = false,
        /**
         * Не догрузилась следующая страница. Это не ошибка экрана: то, что уже
         * загружено, остаётся на месте, а внизу — «не удалось» и «Повторить».
         */
        val loadMoreFailed: Boolean = false,
    ) : PokemonListState
}
