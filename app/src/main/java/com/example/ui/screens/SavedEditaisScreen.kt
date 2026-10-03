package com.example.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.EditalPublico
import com.example.ui.components.EditalCard

@Composable
fun SavedEditaisScreen(
    savedEditais: List<EditalPublico>,
    onToggleSave: (EditalPublico) -> Unit,
    onOpenNotes: (EditalPublico) -> Unit,
    onViewJson: (String) -> Unit,
    onNavigateToRadar: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("saved_editais_screen")
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Editais Monitorados",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${savedEditais.size} oportunidades salvas para acompanhamento",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (savedEditais.isNotEmpty()) {
                FilledTonalButton(
                    onClick = {
                        shareAllSavedSummary(context, savedEditais)
                    },
                    modifier = Modifier.testTag("export_briefing_btn")
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Briefing")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (savedEditais.isEmpty()) {
            EmptySavedCard(onNavigateToRadar)
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(savedEditais, key = { it.id }) { edital ->
                    EditalCard(
                        edital = edital,
                        onToggleSave = { onToggleSave(edital) },
                        onOpenNotes = { onOpenNotes(edital) },
                        onViewJson = { onViewJson(edital.toJsonString()) }
                    )
                }
            }
        }
    }
}

@Composable
fun EmptySavedCard(onNavigateToRadar: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.BookmarkBorder,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(56.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Nenhum edital salvo ainda",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "No radar de busca, toque no ícone de marcador para salvar editais e adicionar notas internas da sua equipe.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onNavigateToRadar,
                modifier = Modifier.testTag("go_to_radar_btn")
            ) {
                Text("Explorar Editais Abertos")
            }
        }
    }
}

private fun shareAllSavedSummary(context: Context, editais: List<EditalPublico>) {
    val stringBuilder = StringBuilder()
    stringBuilder.appendLine("📋 RELATÓRIO EXECUTIVO - EDITAIS MONITORADOS")
    stringBuilder.appendLine("Total de Oportunidades: ${editais.size}")
    stringBuilder.appendLine("Gerado via CaptaEditais\n")

    editais.forEachIndexed { index, edital ->
        stringBuilder.appendLine("----------------------------------------")
        stringBuilder.appendLine("${index + 1}. [${edital.orgaoEmissor}] ${edital.titulo}")
        stringBuilder.appendLine("Objeto: ${edital.resumoObjeto}")
        stringBuilder.appendLine("Prazo: ${edital.prazoSubmissao}")
        stringBuilder.appendLine("Teto/Valor: ${edital.valorTotalOuTeto}")
        stringBuilder.appendLine("Link Oficial: ${edital.urlOficial}")
        if (edital.anotacoes.isNotBlank()) {
            stringBuilder.appendLine("Notas da Equipe: ${edital.anotacoes}")
        }
        stringBuilder.appendLine()
    }

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Relatório de Editais Públicos Monitorados")
        putExtra(Intent.EXTRA_TEXT, stringBuilder.toString())
    }
    context.startActivity(Intent.createChooser(intent, "Compartilhar Relatório"))
}
