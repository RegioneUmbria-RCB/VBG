using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Acaris;
using Init.SIGePro.Protocollo.Acaris.Sottofascicolazione;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.DocEr.Pec;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.Serialize;
using Xunit;
using Newtonsoft.Json.Linq;
using PersonalLib2.Data;
using RestSharp.Serializers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using static Init.SIGePro.Protocollo.ProtocolloEnumerators.ProtocolloEnum;

namespace Init.SIGePro.Protocollo.Test.ProtocolloAcaris
{
    public class DescrizioneSottoFascicoloResolverTests
    {
        [Fact]
        public void CalcolaDescrizioneRitornaDescrizioneSostituitaCorrettamente()
        {
            var istanza = new Istanze
            {
                IDCOMUNE = "E256",
                CODICEISTANZA = "42",
                DATA = new DateTime(1983, 7, 26),
                SOFTWARE = "CO"
            };

            var intervento = new AlberoProc
            {
                SC_DESCRIZIONE = "Autorizzazione per la revisione dei veicoli"
            };

            var template = "[INTERVENTO] - [DATA-PRESENTAZIONE]";

            var resolver = new DescrizioneSottofascicoloResolver(istanza, intervento, template);
            var descrizione = resolver.Get();
            var expected = $"Autorizzazione per la revisione dei veicoli - 26/07/1983";


            Assert.Equal(expected, descrizione);

        }
    }
}
