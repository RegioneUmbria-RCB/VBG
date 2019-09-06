using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Iride.Configuration
{
    public class VerticalizzazioniConfiguration
    {
        ProtocolloLogs _logs;

        public string NumeroPratica { get; private set; }
        public string AggiornaAnagrafiche { get; private set; }
        public string AggiornaClassifica { get; private set; }
        public string Url { get; private set; }
        public string UrlFasc { get; private set; }
        public string ConnectionString { get; private set; }
        public string Provider { get; private set; }
        public string Owner { get; private set; }
        public string View { get; private set; }
        public bool DisabilitaCreaCopie { get; private set; }
        public string CodiceAmministrazione { get; private set; }
        public string UrlPec { get; private set; }
        public string MezzoPec { get; private set; }
        public string MittenteMailPec { get; private set; }
        public string FormatoDataFasc { get; private set; }
        public string MessaggioProtoOk { get; private set; }
        public bool UsaNumAnnoLeggi { get; private set; }
        public string SwapEmail { get; private set; }
        public string Aoo { get; private set; }
        public ProtocolloIrideEnumerators.VersioneEnum Versione { get; private set; }
        public bool WarningPec { get; private set; }
        public string UoSmistamento { get; private set; }

        public VerticalizzazioniConfiguration(ProtocolloLogs logs, VerticalizzazioneProtocolloIride vert)
        {
            if (!vert.Attiva)
                throw new Exception("La verticalizzazione PROTOCOLLO_IRIDE non è attiva");

            _logs = logs;
            EstraiParametri(vert);
        }

        private void EstraiParametri(VerticalizzazioneProtocolloIride vert)
        {
            try
            {
                _logs.Debug("Inizio recupero valori da verticalizzazione");

                NumeroPratica = vert.Numeropratica;
                AggiornaAnagrafiche = vert.Aggiornaanagrafiche;
                AggiornaClassifica = vert.Aggiornaclassifica;
                Url = vert.Url;
                UrlFasc = vert.Urlfasc;
                ConnectionString = vert.Connectionstring;
                Provider = vert.Provider;
                Owner = vert.Owner;
                View = vert.View;
                DisabilitaCreaCopie = vert.DisabilitaCreacopie == "1";
                CodiceAmministrazione = vert.Codiceamministrazione;
                UrlPec = vert.UrlPec;
                MezzoPec = vert.MezzoPec;
                MittenteMailPec = vert.MittentePec;
                FormatoDataFasc = vert.FormatoDataFasc;
                MessaggioProtoOk = vert.WarningDaEliminare;
                UsaNumAnnoLeggi = vert.UsaNumAnnoLeggi == "1";
                Aoo = vert.Aoo;
                Versione = ProtocolloIrideEnumerators.VersioneEnum.IRIDE;
                SwapEmail = vert.SwapEmail;
                ProtocolloIrideEnumerators.VersioneEnum  versioneEnum;
                bool isVersioneParsable = Enum.TryParse(vert.Versione, out versioneEnum);
                WarningPec = vert.Warning_Pec == "1";
                UoSmistamento = vert.UoSmistamento;
                if (isVersioneParsable)
                    Versione = versioneEnum;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_IRIDE", ex);
            }
        }


    }
}
