using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.JIride
{
    public class ParametriRegoleInfo
    {
        private class Constants
        {
            public const string TIPORECAPITOEMAIL = "EMAIL";
        }

        ProtocolloLogs _logs;

        public string NumeroPratica { get; private set; }
        public string AggiornaAnagrafiche { get; private set; }
        public string AggiornaClassifica { get; private set; }
        public string Url { get; private set; }
        public string UrlFasc { get; private set; }
        public bool DisabilitaCreaCopie { get; private set; }
        public string CodiceAmministrazione { get; private set; }
        public string UrlPec { get; private set; }
        public string MezzoPec { get; private set; }
        public string MittenteMailPec { get; private set; }
        public string FormatoDataFasc { get; private set; }
        public string MessaggioProtoOk { get; private set; }
        public bool UsaNumAnnoLeggi { get; private set; }
        public string Aoo { get; private set; }
        public bool WarningPec { get; private set; }
        public string UoSmistamento { get; private set; }
        public bool UsaRifProtocolloOggettoPec { get; private set; }
        public bool UsaInvioInteroperabilePec { get; private set; }
        public bool DisabilitaCaricoPartenza { get; private set; }
        public string TipoRecapitoEmail { get; private set; }

        public ParametriRegoleInfo(ProtocolloLogs logs, VerticalizzazioneProtocolloJIride vert)
        {
            if (!vert.Attiva)
                throw new Exception("La verticalizzazione PROTOCOLLO_JIRIDE non è attiva");

            _logs = logs;
            EstraiParametri(vert);
        }

        private void EstraiParametri(VerticalizzazioneProtocolloJIride vert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione");

                this.NumeroPratica = vert.Numeropratica;
                this.AggiornaAnagrafiche = vert.Aggiornaanagrafiche;
                this.AggiornaClassifica = vert.Aggiornaclassifica;
                this.Url = vert.Url;
                this.UrlFasc = vert.Urlfasc;
                this.DisabilitaCreaCopie = vert.DisabilitaCreacopie == "1";
                this.CodiceAmministrazione = vert.Codiceamministrazione;
                this.UrlPec = vert.UrlPec;
                this.MezzoPec = vert.MezzoPec;
                this.MittenteMailPec = vert.MittentePec;
                this.FormatoDataFasc = vert.FormatoDataFasc;
                this.MessaggioProtoOk = vert.WarningDaEliminare;
                this.UsaNumAnnoLeggi = vert.UsaNumAnnoLeggi == "1";
                this.Aoo = vert.Aoo;
                this.WarningPec = vert.Warning_Pec == "1";
                this.UoSmistamento = vert.UoSmistamento;
                this.UsaRifProtocolloOggettoPec = vert.ProtoOggettoPec == "1";
                this.UsaInvioInteroperabilePec = vert.PecInterop == "1";
                this.DisabilitaCaricoPartenza = vert.DisabilitaCaricoPartenza == "1";
                this.TipoRecapitoEmail = String.IsNullOrEmpty(vert.TipoRecapitoEmail) ? Constants.TIPORECAPITOEMAIL : vert.TipoRecapitoEmail;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_JIRIDE", ex);
            }
        }
    }
}
