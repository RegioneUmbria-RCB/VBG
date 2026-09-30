// -----------------------------------------------------------------------
// <copyright file="CondizioneWhereToNomiCampiTests.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace SIGePro.DatiDinamici.v1.Tests.WebControls
{
    using Xunit;
    using System.Linq;
    using VBG.DatiDinamici.Utils;

    public class CondizioneWhereToNomiCampiTests
    {
        [Fact]
        public void RecuperonomiCampi()
        {
            var expectedLength = 2;
            var expected1 = "ID_CAMPO_DINAMICO_1";
            var expected2 = "ID_CAMPO_DINAMICO_2";
            var condizione = "CAMPO_DB1={ID_CAMPO_DINAMICO_1} and CAMPO_DB2={ID_CAMPO_DINAMICO_2}";

            var campi = new CondizioneWhereToNomiCampi(condizione).GetNomiCampi();

            Assert.Equal(expectedLength, campi.Count());
            Assert.Equal(expected1, campi.ElementAt(0));
            Assert.Equal(expected2, campi.ElementAt(1));
        }
    }
}
