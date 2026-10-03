package com.example.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.JsonViewerDialog
import com.example.ui.components.NotesEditDialog
import com.example.ui.screens.CaucGuideScreen
import com.example.ui.screens.PortaisOficiaisScreen
import com.example.ui.screens.RadarScreen
import com.example.ui.screens.SavedEditaisScreen
import com.example.ui.theme.FederalNavyPrimary
import com.example.viewmodel.EditalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScaffold(
    viewModel: EditalViewModel,
    modifier: Modifier = Modifier
) {
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val searchUiState by viewModel.searchUiState.collectAsStateWithLifecycle()
    val filterParams by viewModel.filterParams.collectAsStateWithLifecycle()
    val savedEditais by viewModel.savedEditais.collectAsStateWithLifecycle()
    val activeJsonView by viewModel.activeJsonView.collectAsStateWithLifecycle()
    val editalForNotes by viewModel.editalForNotes.collectAsStateWithLifecycle()

    var showInfoDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Radar,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "  CaptaEditais",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showInfoDialog = true },
                        modifier = Modifier.testTag("app_info_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Sobre o Rastreador",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = FederalNavyPrimary
                )
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.testTag("bottom_nav_bar")
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { viewModel.setSelectedTab(0) },
                    icon = { Icon(Icons.Default.Radar, contentDescription = "Radar") },
                    label = { Text("Radar") },
                    modifier = Modifier.testTag("tab_radar")
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { viewModel.setSelectedTab(1) },
                    icon = {
                        if (savedEditais.isNotEmpty()) {
                            BadgedBox(badge = { Badge { Text("${savedEditais.size}") } }) {
                                Icon(Icons.Default.Bookmark, contentDescription = "Monitorados")
                            }
                        } else {
                            Icon(Icons.Default.Bookmark, contentDescription = "Monitorados")
                        }
                    },
                    label = { Text("Monitorados") },
                    modifier = Modifier.testTag("tab_saved")
                )

                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { viewModel.setSelectedTab(2) },
                    icon = { Icon(Icons.Default.Checklist, contentDescription = "CAUC") },
                    label = { Text("Checklist CAUC") },
                    modifier = Modifier.testTag("tab_cauc")
                )

                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { viewModel.setSelectedTab(3) },
                    icon = { Icon(Icons.Default.Public, contentDescription = "Portais") },
                    label = { Text("Fontes Oficiais") },
                    modifier = Modifier.testTag("tab_sources")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> RadarScreen(
                    searchUiState = searchUiState,
                    filters = filterParams,
                    onFiltersChanged = { viewModel.updateFilters(it) },
                    onSearchClicked = { viewModel.performSearch() },
                    onToggleSave = { viewModel.toggleSaveEdital(it) },
                    onOpenNotes = { viewModel.openNotesDialog(it) },
                    onViewJson = { viewModel.showJson(it) }
                )
                1 -> SavedEditaisScreen(
                    savedEditais = savedEditais,
                    onToggleSave = { viewModel.toggleSaveEdital(it) },
                    onOpenNotes = { viewModel.openNotesDialog(it) },
                    onViewJson = { viewModel.showJson(it) },
                    onNavigateToRadar = { viewModel.setSelectedTab(0) }
                )
                2 -> CaucGuideScreen()
                3 -> PortaisOficiaisScreen()
            }
        }
    }

    // Modal JSON Dialog
    activeJsonView?.let { json ->
        JsonViewerDialog(
            jsonContent = json,
            onDismiss = { viewModel.dismissJson() }
        )
    }

    // Notes Dialog
    editalForNotes?.let { edital ->
        NotesEditDialog(
            edital = edital,
            onDismiss = { viewModel.closeNotesDialog() },
            onSaveNotes = { notes ->
                viewModel.saveNotes(edital.id, notes)
            }
        )
    }

    // Info Dialog
    if (showInfoDialog) {
        AlertDialog(
            onDismissRequest = { showInfoDialog = false },
            title = { Text("Sobre o CaptaEditais", fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "O CaptaEditais é um rastreador e analista especialista em captação de recursos públicos no Brasil.\n\n" +
                    "• Rastreamento ativo de editais vigentes voltados a Municípios, Estados e Consórcios Públicos.\n" +
                    "• Monitoramento de fontes oficiais: Transferegov, BNDES, FINEP, Novo PAC, FNS e Ministérios.\n" +
                    "• Validação rigorosa de URLs oficiais diretas para acesso aos editais.\n" +
                    "• Checklist integrado de regularidade fiscal (CAUC/SIAFI) para habilitação prévia de propostas.\n" +
                    "• Exportação de dados estruturados em JSON estrito e relatórios para equipes de convênios."
                )
            },
            confirmButton = {
                TextButton(onClick = { showInfoDialog = false }) {
                    Text("Entendido")
                }
            }
        )
    }
}
