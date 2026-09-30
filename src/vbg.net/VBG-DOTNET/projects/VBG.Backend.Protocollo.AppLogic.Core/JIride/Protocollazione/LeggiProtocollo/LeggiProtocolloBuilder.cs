using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIride.Protocollazione.LeggiProtocollo
{
    public class LeggiProtocolloBuilder : IJIrideRequestBuilder<LeggiProtocolloRequest>
    {
        private string _idProtocolloOriginale;
        private string _numeroProtocolloOriginale;
        private int? _numeroProtocollo;
        private string _annoProtocollo;
        private string _operatore;
        private string _ruolo;
        private VerticalizzazioneProtocolloJIride _verticalizzazione;
        
        

        public LeggiProtocolloBuilder(VerticalizzazioneProtocolloJIride verticalizzazione, string idProtocollo, string numeroProtocollo, string annoProtocollo, string operatore, string ruolo)
        {
            this._idProtocolloOriginale = idProtocollo;
            this._verticalizzazione = verticalizzazione;
            this._numeroProtocolloOriginale = numeroProtocollo;
            this._numeroProtocollo = String.IsNullOrEmpty(this._numeroProtocolloOriginale) ? (int?) null : Convert.ToInt32( this._numeroProtocolloOriginale.Split(Convert.ToChar("/"))[0]);
            this._annoProtocollo = annoProtocollo;
            this._operatore = operatore.ToUpper();
            this._ruolo = ruolo;
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
                AOO = this._verticalizzazione.Aoo,
                CodiceAmministrazione = this._verticalizzazione.Codiceamministrazione,
                IdProtocollo = idProtocollo,
                IdProtocolloVBG = this._idProtocolloOriginale,
                IsCopia = isCopia,
                NumeroProtocollo = this._numeroProtocollo,
                NumeroProtocolloOriginale = this._numeroProtocolloOriginale,
                Operatore = this._operatore,
                Ruolo = this._ruolo,
                Url = this._verticalizzazione.Url,
                UrlPec = this._verticalizzazione.UrlPec,
                UsaNumeroAnnoPerLettura = this._verticalizzazione.UsaNumAnnoLeggi == "1",
                VisualizzaRicevutePec = this._verticalizzazione.VisualizzaRicevutePec == "1",
            };
        }
    }
}
