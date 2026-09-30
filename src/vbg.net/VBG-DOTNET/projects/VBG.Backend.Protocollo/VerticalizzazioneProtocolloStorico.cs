using SIGePro.Manager.VerticalizzazioniBase;
using System;

namespace VBG.Backend.Protocollo.Verticalizzazioni.Shared
{
    public class VerticalizzazioneProtocolloStorico : Verticalizzazione, IVerticalizzazioneProtocollo
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_STORICO";

        private static class Constants
        {
            public const string AOO = "AOO";
            public const string CodiceRegistro = "CODICEREGISTRO";
            public const string CodiceUfficio = "CODICEUFFICIO";
            public const string DataUltimaProtocollazione = "DATAULTIMAPROTOCOLLAZIONE";
            public const string Operatore = "OPERATORE";
            public const string Ruolo = "RUOLO";
            public const string Tipoprotocollo = "TIPOPROTOCOLLO";
            public const string URLLeggiProtocollo = "URLLEGGIPROTOCOLLO";
            public const string URLLeggiAllegati = "URLLEGGIALLEGATI";
            public const string UsaNumAnnoLeggi = "USA_NUM_ANNO_LEGGI";
            public const string Utente = "UTENTE";
            public const string VisualizzaRicevutePEC = "VISUALIZZA_RICEVUTE_PEC";

        }

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;



        public VerticalizzazioneProtocolloStorico()
        {

        }

        public VerticalizzazioneProtocolloStorico(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software, codiceComune)
        {

        }

        public string AOO => this.GetString(Constants.AOO);
        public string CodiceRegistro => this.GetString(Constants.CodiceRegistro);
        public string CodiceUfficio => this.GetString(Constants.CodiceUfficio);
        public DateTime? DataUltimaProtocollazione => this.GetDate(Constants.DataUltimaProtocollazione);
        public string Operatore => this.GetString(Constants.Operatore);
        public string Ruolo => this.GetString(Constants.Ruolo);
        public string Tipoprotocollo => this.GetString(Constants.Tipoprotocollo);
        public string URLLeggiProtocollo => this.GetString(Constants.URLLeggiProtocollo);
        public string URLLeggiAllegati => this.GetString(Constants.URLLeggiAllegati);
        public bool UsaNumAnnoLeggi => this.GetBool(Constants.UsaNumAnnoLeggi);
        public string Utente => this.GetString(Constants.Utente);
        public bool VisualizzaRicevutePEC => this.GetBool(Constants.VisualizzaRicevutePEC);
        public string ProxyAddress => throw new NotImplementedException("Attualmente non gestita ma prevista a livello di interfaccia");

    }
}
