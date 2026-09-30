// -----------------------------------------------------------------------
// <copyright file="DatiDinamiciMovimentoTest.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using Init.Sigepro.FrontEnd.GestioneMovimenti;
using Xunit;

namespace MovimentiTest
{
    public class DatiDinamiciMovimentoTest
    {
        [Fact]
        public void Un_valore_dinamico_vuoto_e_uguale_ad_un_campo_che_ha_come_valore_e_come_valore_decodificato_una_stringa_vuota()
        {
            var v1 = ValoreDatoDinamico.Empty;
            var v2 = new ValoreDatoDinamico("", "");

            Assert.Equal(v1, v2);
            Assert.True(v1 == v2);
        }

        [Fact]
        public void Due_valori_dinamici_sono_uguali_se_hanno_lo_stesso_valore_e_valore_decodificato()
        {
            var v1 = new ValoreDatoDinamico("1", "v1");
            var v2 = new ValoreDatoDinamico("1", "v1");

            Assert.Equal(v1, v2);
            Assert.True(v1 == v2);

            var v3 = new ValoreDatoDinamico("2", "v2");

            Assert.NotEqual(v1, v3);
            Assert.True(v1 != v3);
        }


        [Fact]
        public void Quando_in_un_campo_dinamico_si_accede_ad_un_indice_non_presente_viene_restituito_un_valore_corrispondente_ad_un_valore_vuoto()
        {
            var campo = new CampoDinamico(1);
            var v1 = campo.GetValoreAllIndice(1);

            Assert.Equal(v1, ValoreDatoDinamico.Empty);
        }

        [Fact]
        public void Impostando_un_valore_su_un_campo_dinamico_il_valore_all_indice_corrispondente_diventa_uguale_al_valore_assegnato()
        {
            var campo = new CampoDinamico(1);

            campo.ImpostaValore(1, new ValoreDatoDinamico("1", "v1"));

            Assert.Equal(campo.GetValoreAllIndice(1), new ValoreDatoDinamico("1", "v1"));

            campo.ImpostaValore(1, new ValoreDatoDinamico("2", "v2"));

            Assert.NotEqual(campo.GetValoreAllIndice(1), new ValoreDatoDinamico("1", "v1"));
            Assert.Equal(campo.GetValoreAllIndice(1), new ValoreDatoDinamico("2", "v2"));

        }


    }
}
