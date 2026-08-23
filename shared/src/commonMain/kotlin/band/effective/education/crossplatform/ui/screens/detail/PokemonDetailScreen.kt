package band.effective.education.crossplatform.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.education.crossplatform.detail.PokemonDetailIntent
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.section_abilities
import band.effective.education.crossplatform.resources.section_evolution
import band.effective.education.crossplatform.resources.section_stats
import band.effective.education.crossplatform.ui.components.AbilityColumn
import band.effective.education.crossplatform.ui.components.EvolutionRow
import band.effective.education.crossplatform.ui.components.PokemonFactRow
import band.effective.education.crossplatform.ui.components.PokemonFlavor
import band.effective.education.crossplatform.ui.components.PokemonGenus
import band.effective.education.crossplatform.ui.components.PokemonImage
import band.effective.education.crossplatform.ui.components.PokemonName
import band.effective.education.crossplatform.ui.components.PokemonNumber
import band.effective.education.crossplatform.ui.components.PokemonStats
import band.effective.education.crossplatform.ui.components.Section
import band.effective.education.crossplatform.ui.components.TypeRow
import band.effective.education.crossplatform.ui.model.PokemonDetailUi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun PokemonDetailScreen(
    pokemon: PokemonDetailUi,
    onIntent: (PokemonDetailIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val accent = pokemon.accent

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        PokemonImage(
            painter = painterResource(pokemon.sprite),
            bgColor = accent.copy(alpha = 0.16f),
            size = 200.dp,
            pokemonSize = 164.dp
        )
        PokemonNumber(pokemon.number)
        PokemonName(pokemon.name)
        PokemonGenus(pokemon.genus)
        TypeRow(pokemon.types)
        PokemonFactRow(
            height = pokemon.height,
            weight = pokemon.weight,
            baseExperience = pokemon.baseExperience
        )
        PokemonFlavor(pokemon.flavorText)
        Section(stringResource(Res.string.section_stats))
        PokemonStats(stats = pokemon.stats, accent = accent)
        Section(stringResource(Res.string.section_abilities))
        AbilityColumn(pokemon.abilities)
        // Связанные элементы: вся цепочка эволюции, включая саму запись.
        // Нажатие открывает соседа — и бэкстек растёт, что на Navigation 3
        // видно буквально: это просто список, в который добавили элемент.
        Section(stringResource(Res.string.section_evolution))
        EvolutionRow(
            evolution = pokemon.evolution,
            onClick = { id -> onIntent(PokemonDetailIntent.EvolutionStageClicked(id)) },
        )
    }
}
