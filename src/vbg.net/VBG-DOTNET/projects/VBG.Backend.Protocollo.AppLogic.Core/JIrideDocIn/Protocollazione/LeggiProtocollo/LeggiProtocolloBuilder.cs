using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Protocollazione.LeggiProtocollo
{
    public class LeggiProtocolloBuilder : IJIrideDocInRequestBuilder<LeggiProtocolloRequest>
    {
        private string _idProtocolloOriginale;
        private string _numeroProtocolloOriginale;
        private int? _numeroProtocollo;
        private string _annoProtocollo;
        private string _operatore;
        private string _ruolo;
        private ParametriRegoleInfo _parametriRegoleInfo;
        
        

        public LeggiProtocolloBuilder(ParametriRegoleInfo parametriRegoleInfo, string idProtocollo, string numeroProtocollo, string annoProtocollo, string operatore, string ruolo)
        {
            this._idProtocolloOriginale = idProtocollo;
            this._parametriRegoleInfo = parametriRegoleInfo;
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
                AOO = this._parametriRegoleInfo.Aoo,
                CodiceAmministrazione = this._parametriRegoleInfo.CodiceAmministrazione,
                IdProtocollo = idProtocollo,
                IdProtocolloVBG = this._idProtocolloOriginale,
                IsCopia = isCopia,
                NumeroProtocollo = this._numeroProtocollo,
                NumeroProtocolloOriginale = this._numeroProtocolloOriginale,
                Operatore = this._operatore,
                Ruolo = this._ruolo,
                Url = this._parametriRegoleInfo.Url,
                UrlPec = this._parametriRegoleInfo.UrlPec,
                UsaNumeroAnnoPerLettura = this._parametriRegoleInfo.UsaNumAnnoLeggi,
                VisualizzaRicevutePec = this._parametriRegoleInfo.VisualizzaRicevutePec,
            };
        }
    }
}
