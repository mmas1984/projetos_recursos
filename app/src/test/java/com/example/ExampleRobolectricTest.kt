package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.EditalPublico
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("CaptaEditais", appName)
  }

  @Test
  fun `test edital json serialization conforms to spec`() {
    val edital = EditalPublico(
      id = "TEST-01",
      orgaoEmissor = "FINEP",
      titulo = "Chamada Teste",
      resumoObjeto = "Objeto teste",
      elegibilidade = "Prefeituras",
      valorTotalOuTeto = "R$ 1.000.000,00",
      prazoSubmissao = "Fluxo Contínuo",
      fonteRecurso = "FNDCT",
      urlOficial = "https://www.finep.gov.br"
    )
    val json = edital.toJsonString()
    assertTrue(json.contains("\"id\": \"TEST-01\""))
    assertTrue(json.contains("\"orgao_emissor\": \"FINEP\""))
    assertTrue(json.contains("\"url_oficial\": \"https://www.finep.gov.br\""))
  }
}
