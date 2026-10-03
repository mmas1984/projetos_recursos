package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.FederalNavyPrimary

data class CaucItem(
    val id: String,
    val grupo: String,
    val titulo: String,
    val descricao: String,
    val orgaoValidador: String
)

val CAUC_ITEMS = listOf(
    // Grupo I
    CaucItem("1.1", "Grupo I - Obrigações Financeiras", "Tributos Federais e Dívida Ativa da União", "Certidão Conjunta Negativa da Receita Federal e PGFN.", "Receita Federal / PGFN"),
    CaucItem("1.2", "Grupo I - Obrigações Financeiras", "Contribuições Previdenciárias", "Regularidade das contribuições ao Regime Geral ou Próprio de Previdência (RPPS).", "RFB / MPS"),
    CaucItem("1.3", "Grupo I - Obrigações Financeiras", "Regularidade perante o FGTS (CRF)", "Certificado de Regularidade do FGTS emitido pela Caixa.", "Caixa Econômica"),
    CaucItem("1.4", "Grupo I - Obrigações Financeiras", "CADIN - Cadastro Informativo de Créditos", "Ausência de inscrições restritivas no CADIN Federal.", "Banco Central / STN"),
    // Grupo II
    CaucItem("2.1", "Grupo II - Prestação de Contas", "Prestação de Contas de Convênios da União", "Inexistência de inadimplência em convênios anteriores no Transferegov.", "Ministérios Concedentes"),
    // Grupo III
    CaucItem("3.1", "Grupo III - Transparência e LRF", "Relatório de Gestão Fiscal (RGF)", "Homologação do RGF tempestivo no Siconfi/Tesouro Nacional.", "Secretaria do Tesouro Nacional"),
    CaucItem("3.2", "Grupo III - Transparência e LRF", "Relatório Resumido da Execução Orçamentária (RREO)", "Publicação bimestral no Siconfi.", "Secretaria do Tesouro Nacional"),
    CaucItem("3.3", "Grupo III - Transparência e LRF", "Limite de Despesa com Pessoal", "Cumprimento do limite máximo de 54% (Executivo) previsto na LRF.", "Tribunais de Contas / TCE"),
    CaucItem("3.4", "Grupo III - Transparência e LRF", "Limite da Dívida Consolidada Líquida", "Não ultrapassar os limites fixados pelo Senado Federal.", "STN"),
    // Grupo IV
    CaucItem("4.1", "Grupo IV - Gastos Mínimos Constitucionais", "Aplicação Mínima em Ações e Serviços de Saúde", "Aplicação de no mínimo 15% das receitas de impostos no SIOPS.", "Ministério da Saúde / SIOPS"),
    CaucItem("4.2", "Grupo IV - Gastos Mínimos Constitucionais", "Aplicação Mínima em Manutenção e Desenvolvimento do Ensino", "Aplicação de no mínimo 25% no SIOPE/FNDE.", "FNDE / MEC")
)

@Composable
fun CaucGuideScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val checkedStates = remember { mutableStateMapOf<String, Boolean>() }

    val checkedCount = checkedStates.values.count { it }
    val totalCount = CAUC_ITEMS.size
    val isAllOk = checkedCount == totalCount

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("cauc_guide_screen"),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            // Header card
            ElevatedCard(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isAllOk) Icons.Default.CheckCircle else Icons.Default.Warning,
                            contentDescription = null,
                            tint = if (isAllOk) EmeraldSuccess else FederalNavyPrimary,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Checklist de Regularidade CAUC",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Requisitos obrigatórios para assinar convênios no Transferegov",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Surface(
                        color = if (isAllOk) EmeraldSuccess.copy(alpha = 0.15f) else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Itens validados pela equipe: $checkedCount de $totalCount",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isAllOk) EmeraldSuccess else MaterialTheme.colorScheme.onPrimaryContainer
                            )

                            Button(
                                onClick = {
                                    val caucUrl = "https://cauc.tesouro.gov.br/"
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(caucUrl))
                                    context.startActivity(intent)
                                }
                            ) {
                                Icon(imageVector = Icons.Default.OpenInBrowser, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Consultar CAUC/STN")
                            }
                        }
                    }
                }
            }
        }

        // Checklist Items
        items(CAUC_ITEMS, key = { it.id }) { item ->
            val isChecked = checkedStates[item.id] ?: false

            ElevatedCard(
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { checkedStates[item.id] = it },
                        modifier = Modifier.testTag("checkbox_cauc_${item.id}")
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${item.id} - ${item.titulo}",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = item.grupo,
                            style = MaterialTheme.typography.labelSmall,
                            color = FederalNavyPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.descricao,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Órgão Validador: ${item.orgaoValidador}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }
    }
}
