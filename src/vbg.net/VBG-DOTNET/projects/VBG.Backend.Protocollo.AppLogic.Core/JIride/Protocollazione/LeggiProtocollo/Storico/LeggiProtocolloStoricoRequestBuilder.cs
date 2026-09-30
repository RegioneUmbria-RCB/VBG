using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIride.Protocollazione.LeggiProtocollo.Storico
{
    public class LeggiProtocolloStoricoRequestBuilder : IJIrideRequestBuilder<LeggiProtocolloRequest>
    {
        private string _idProtocolloOriginale;
        private string _numeroProtocolloOriginale;
        private int? _numeroProtocollo;
        private string _annoProtocollo;
        private VerticalizzazioneProtocolloStorico _verticalizzazione;

        public LeggiProtocolloStoricoRequestBuilder(VerticalizzazioneProtocolloStorico verticalizzazione, string idProtocollo, string numeroProtocollo, string annoProtocollo)
        {
            this._idProtocolloOriginale = idProtocollo;
            this._verticalizzazione = verticalizzazione;
            this._numeroProtocolloOriginale = numeroProtocollo;
            this._numeroProtocollo = String.IsNullOrEmpty(this._numeroProtocolloOriginale) ? (int?)null : Convert.ToInt32(this._numeroProtocolloOriginale.Split(Convert.ToChar("/"))[0]);
            this._annoProtocollo = annoProtocollo;
        }

        public LeggiProtocolloRequest Build()
        {
            var isCopia = false;
            var idProtocollo = (int?)null;

            if (!String.IsNullOrEmpty(this._idProtocolloOriginale))
            {
                var arrIdProtocollo = this._idProtocolloOriginale.Split('-');
                isCopia = arrIdProtocollo.Length > 1 && arrIdProtocollo[1] == "COPIA";
                idProtocollo = Convert.ToInt32(arrIdProtocollo[0]);
            }

            return new LeggiProtocolloRequest
            {
                AnnoProtocollo = String.IsNullOrEmpty(this._annoProtocollo) ? (short?)null : short.Parse(this._annoProtocollo),
                AOO = this._verticalizzazione.AOO,
                CodiceAmministrazione = this._verticalizzazione.CodiceUfficio,
                IdProtocollo = idProtocollo,
                IdProtocolloVBG = this._idProtocolloOriginale,
                IsCopia = isCopia,
                NumeroProtocollo = this._numeroProtocollo,
                NumeroProtocolloOriginale = this._numeroProtocolloOriginale,
                Operatore = this._verticalizzazione.Operatore,
                Ruolo = this._verticalizzazione.Ruolo,
                Url = this._verticalizzazione.URLLeggiAllegati,
                UrlPec = this._verticalizzazione.URLLeggiProtocollo,
                UsaNumeroAnnoPerLettura = this._verticalizzazione.UsaNumAnnoLeggi,
                VisualizzaRicevutePec = this._verticalizzazione.VisualizzaRicevutePEC,
            };
        }
    }
}
