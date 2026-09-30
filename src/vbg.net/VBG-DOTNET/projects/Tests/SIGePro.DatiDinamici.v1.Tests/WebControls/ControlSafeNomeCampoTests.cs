// -----------------------------------------------------------------------
// <copyright file="ControlSafeNomeCampoTests.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace SIGePro.DatiDinamici.v1.Tests.WebControls
{
    using Xunit;
    using VBG.DatiDinamici.Utils;

    public class ControlSafeNomeCampoTests
    {
        [Fact]
        public void I_caratteri_non_testuali_vengono_sostituiti_con_underscore()
        {
            var test = "Nome-campo/con caratteri strani'";
            var expected = "NOME_CAMPO_CON_CARATTERI_STRANI_";

            var nomeCampo = new ControlSafeNomeCampo(test);

            Assert.Equal(expected, nomeCampo.ToString());
        }
    }
}
