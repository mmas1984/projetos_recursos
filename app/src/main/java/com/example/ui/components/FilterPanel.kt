package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.model.FilterParams
import com.example.ui.theme.FederalNavyPrimary

val AREAS_TEMATICAS = listOf(
    "Todas as Áreas",
    "Saúde",
    "Educação",
    "Infraestrutura e Obras",
    "Saneamento Básico",
    "Meio Ambiente e Clima",
    "Tecnologia e Inovação",
    "Cultura e Turismo",
    "Segurança Pública",
    "Agricultura e Abastecimento"
)

val TIPOS_ENTES = listOf(
    "Prefeituras e Municípios",
    "Consórcios Públicos",
    "Governos Estaduais",
    "Entidades e Hospitais Públicos"
)

val ESTADOS_E_REGIOES = listOf(
    "Nacional (Todos os Estados)",
    "Região Sudeste (SP, RJ, MG, ES)",
    "Região Sul (PR, SC, RS)",
    "Região Nordeste (BA, PE, CE, etc.)",
    "Região Centro-Oeste (GO, MT, MS, DF)",
    "Região Norte (AM, PA, RO, etc.)",
    "São Paulo (SP)",
    "Minas Gerais (MG)",
    "Rio de Janeiro (RJ)",
    "Bahia (BA)",
    "Paraná (PR)",
    "Rio Grande do Sul (RS)"
)

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FilterPanel(
    filters: FilterParams,
    onFiltersChanged: (FilterParams) -> Unit,
    onSearchClicked: () -> Unit,
    isSearching: Boolean,
    modifier: Modifier = Modifier
) {
    var expandedFilters by remember { mutableStateOf(false) }

    var expandedEnteDropdown by remember { mutableStateOf(false) }
    var expandedEstadoDropdown by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("filter_panel"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Search Input Row with Term & Clear
            OutlinedTextField(
                value = filters.termoBusca,
                onValueChange = { onFiltersChanged(filters.copy(termoBusca = it)) },
                label = { Text("Buscar palavra-chave ou tema estratégico") },
                placeholder = { Text("Ex: pavimentação, UBS, resíduos, telemedicina...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = FederalNavyPrimary
                    )
                },
                trailingIcon = {
                    if (filters.termoBusca.isNotBlank()) {
                        IconButton(onClick = { onFiltersChanged(filters.copy(termoBusca = "")) }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Limpar busca")
                        }
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSearchClicked() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_term_input"),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Quick Horizontal Scroll for Popular Themes
            Text(
                text = "Área Temática Prioritária:",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AREAS_TEMATICAS.forEach { area ->
                    val selected = filters.areaTematica == area
                    FilterChip(
                        selected = selected,
                        onClick = {
                            onFiltersChanged(filters.copy(areaTematica = area))
                        },
                        label = { Text(area) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = FederalNavyPrimary.copy(alpha = 0.15f),
                            selectedLabelColor = FederalNavyPrimary
                        ),
                        modifier = Modifier.testTag("chip_area_$area")
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Toggle for Ente & Região Expansion
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandedFilters = !expandedFilters }
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.FilterAlt,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Filtros de Ente Público e Região",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Icon(
                    imageVector = if (expandedFilters) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (expandedFilters) "Recolher filtros" else "Expandir filtros",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            AnimatedVisibility(visible = expandedFilters) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    // Tipo de Ente Dropdown
                    ExposedDropdownMenuBox(
                        expanded = expandedEnteDropdown,
                        onExpandedChange = { expandedEnteDropdown = it }
                    ) {
                        OutlinedTextField(
                            value = filters.tipoEnte,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Proponente / Ente Público") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEnteDropdown) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                .testTag("dropdown_ente"),
                            shape = RoundedCornerShape(12.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = expandedEnteDropdown,
                            onDismissRequest = { expandedEnteDropdown = false }
                        ) {
                            TIPOS_ENTES.forEach { ente ->
                                DropdownMenuItem(
                                    text = { Text(ente) },
                                    onClick = {
                                        onFiltersChanged(filters.copy(tipoEnte = ente))
                                        expandedEnteDropdown = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Região Dropdown
                    ExposedDropdownMenuBox(
                        expanded = expandedEstadoDropdown,
                        onExpandedChange = { expandedEstadoDropdown = it }
                    ) {
                        OutlinedTextField(
                            value = filters.estadoRegiao,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Estado / Região de Interesse") },
                            leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(18.dp)) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEstadoDropdown) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                .testTag("dropdown_regiao"),
                            shape = RoundedCornerShape(12.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = expandedEstadoDropdown,
                            onDismissRequest = { expandedEstadoDropdown = false }
                        ) {
                            ESTADOS_E_REGIOES.forEach { reg ->
                                DropdownMenuItem(
                                    text = { Text(reg) },
                                    onClick = {
                                        onFiltersChanged(filters.copy(estadoRegiao = reg))
                                        expandedEstadoDropdown = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Main Action Button: Rastrear Editais Vigentes
            Button(
                onClick = onSearchClicked,
                enabled = !isSearching,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("track_editais_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = FederalNavyPrimary
                )
            ) {
                if (isSearching) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Rastreando Editais...")
                } else {
                    Icon(
                        imageVector = Icons.Default.Radar,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Rastrear Editais Vigentes",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
