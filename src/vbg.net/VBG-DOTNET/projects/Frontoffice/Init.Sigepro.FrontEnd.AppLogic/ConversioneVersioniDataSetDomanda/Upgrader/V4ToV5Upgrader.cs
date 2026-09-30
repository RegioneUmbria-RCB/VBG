using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda.Utils;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using System;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda.Upgrader
{
    internal class V4ToV5Upgrader : IDatiDomandaUpgrader
    {
        private readonly V4DataSetSerializer _origineSerializer = new V4DataSetSerializer();
        private readonly V5DataSetSerializer _destinazioneSerializer = new V5DataSetSerializer();

        public byte[] Ugrade(byte[] dati)
        {
            var origine = this._origineSerializer.Deserialize(dati);
            var destinazione = this.MapOrigineToDestinazione(origine);

            return this._destinazioneSerializer.Serialize(destinazione);
        }

        protected virtual PresentazioneIstanzaDbV2 MapOrigineToDestinazione(PresentazioneIstanzaDbV2 origine)
        {
            var cloneHelper = new DataSetCloneHelper<PresentazioneIstanzaDbV2, PresentazioneIstanzaDbV2>();

            var destinazione = cloneHelper.CreateFrom(origine);

            this.UpgradeDatiPagamenti(destinazione);

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
