using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.EGrammata.Adapters
{
    public class EGrammataVerticalizzazioniAdapter
    {
        ProtocolloLogs _logs;

        public string UrlProto { get; private set; }
        public string UrlLeggi { get; private set; }
        public string UserId { get; private set; }
        public string Password { get; private set; }
        public string IdUnita { get; private set; }
        public string LivelliUnita { get; private set; }
        public string Registro { get; private set; }

        public EGrammataVerticalizzazioniAdapter(ProtocolloLogs logs, VerticalizzazioneProtocolloEgrammata paramVert)
        {
            if (!paramVert.Attiva)
                throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_EGRAMMATA NON È ATTIVA");

            _logs = logs;
            EstraiParametri(paramVert);
        }

        private void VerificaIntegritaParametri(VerticalizzazioneProtocolloEgrammata paramVert)
        {
            try
            {
                if (String.IsNullOrEmpty(paramVert.UrlProto))
                    throw new Exception("IL PARAMETRO URLPROTO NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.UserId))
                    throw new Exception("IL PARAMETRO USERID NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Password))
                    throw new Exception("IL PARAMETRO PASSWORD NON E' STATO VALORIZZATO");

                if (String.IsNullOrEmpty(paramVert.Registro))
                    throw new Exception("IL PARAMETRO REGISTRO NON E' STATO VALORIZZATO");
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        private VerticalizzazioneProtocolloEgrammata EstraiParametri(VerticalizzazioneProtocolloEgrammata paramVert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione");

                VerificaIntegritaParametri(paramVert);

                UrlProto = paramVert.UrlProto;
                UrlLeggi = paramVert.UrlLeggi;
                UserId = paramVert.UserId;
                Password = paramVert.Password;
                IdUnita = paramVert.IdUnita;
                LivelliUnita = paramVert.LivelliUnita;
                Registro = paramVert.Registro;
                
                return paramVert;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_EGRAMMATA", ex);
            }
        }

    }
}
