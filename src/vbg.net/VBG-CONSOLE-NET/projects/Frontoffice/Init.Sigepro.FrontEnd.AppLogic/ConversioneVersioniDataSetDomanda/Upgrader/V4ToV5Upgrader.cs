using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda.Utils;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda.Upgrader
{
    class V4ToV5Upgrader : IDatiDomandaUpgrader
    {
        V4DataSetSerializer _origineSerializer = new V4DataSetSerializer();
        V5DataSetSerializer _destinazioneSerializer = new V5DataSetSerializer();

        public byte[] Ugrade(byte[] dati)
        {
            var origine = _origineSerializer.Deserialize(dati);
            var destinazione = MapOrigineToDestinazione(origine);

            return _destinazioneSerializer.Serialize(destinazione);
        }

        protected virtual PresentazioneIstanzaDbV2 MapOrigineToDestinazione(PresentazioneIstanzaDbV2 origine)
        {
            var cloneHelper = new DataSetCloneHelper<PresentazioneIstanzaDbV2, PresentazioneIstanzaDbV2>();

            var destinazione = cloneHelper.CreateFrom(origine);

            UpgradeDatiPagamenti(destinazione);

            return destinazione;
        }

        private void UpgradeDatiPagamenti(PresentazioneIstanzaDbV2 destinazione)
        {
            var righeDaAggiornare = destinazione.OneriDomanda.Where(x => x["ModalitaPagamento"] == DBNull.Value);

            foreach (var riga in righeDaAggiornare)
            {
                var modalitaPagamento = ModalitaPagamentoOnereEnum.GiaPagato;

                if (!riga.IsNonPagatoNull() && riga.NonPagato)
                {
                    modalitaPagamento = ModalitaPagamentoOnereEnum.NonDovuto;
                }

                riga.ModalitaPagamento = ((int)modalitaPagamento).ToString();
                riga.IdPagamentoOnline = String.Empty;
                riga.StatoPagamentoOnline = ((int)StatoPagamentoOnereEnum.ProntoPerPagamentoOnline).ToString();
            }

            destinazione.AcceptChanges();
        }

    }
}
