using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Xunit;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda;
using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda.Upgrader;

namespace MovimentiTest.TestConversioneVersioniDataSetDomanda
{
    public class V3ToV4UpgraderTests
    {
        public class V2ToV3Mapping
        {
            V3DataSetSerializer _serializer;
            V3ToV4Upgrader _upgrader;
            PresentazioneIstanzaDbV2 _origine;

            public V2ToV3Mapping()
            {
                _serializer = new V3DataSetSerializer();
                _upgrader = new V3ToV4Upgrader();
                _origine = new PresentazioneIstanzaDbV2();
            }

            [Fact]
            public void I_dati_dinamici_privi_di_indice_scheda_vengono_aggiornati_con_indice_scheda_0()
            {
                var row = _origine.Dyn2Dati.NewDyn2DatiRow();
                row.IdCampo = 1;
                row.IndiceMolteplicita = 0;
                row.Valore = "valore";
                row.ValoreDecodificato = "valoreDecodificato";
                _origine.Dyn2Dati.AddDyn2DatiRow(row);
                var destinazioneBin = _upgrader.Ugrade(_serializer.Serialize(_origine));
                var destinazione = _serializer.Deserialize(destinazioneBin);
                Assert.Equal(1, destinazione.Dyn2Dati.Count);
                Assert.NotEqual(DBNull.Value, destinazione.Dyn2Dati[0]["IndiceScheda"]);
                Assert.Equal(0, destinazione.Dyn2Dati[0].IndiceScheda);
            }
        }
    }
}
