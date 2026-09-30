
using SIGePro.Manager.VerticalizzazioniBase;
using System;

namespace VBG.Backend.Protocollo.Verticalizzazioni.Core
{
    public class VerticalizzazioneProtocolloElios : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "PROTOCOLLO_ELIOS";

        private const string AREAOMOGENEA = "AREAOMOGENEA";
        private const string ENTE = "ENTE";
        private const string PASSWORD = "PASSWORD";
        private const string URL_AUTENTICAZIONE = "URL_AUTENTICAZIONE";
        private const string URL_CONFIGURAZIONE = "URL_CONFIGURAZIONE";
        private const string URL_FASCICOLAZIONE = "URL_FASCICOLAZIONE";
        private const string URL_PROTOCOLLAZIONE = "URL_PROTOCOLLAZIONE";
        private const string UTENTE = "UTENTE";

        public override string NomeVerticalizzazione => NOME_VERTICALIZZAZIONE;

        public VerticalizzazioneProtocolloElios()
        {
            
        }

        public VerticalizzazioneProtocolloElios(string idComuneAlias, string software, string codiceComune) : base(idComuneAlias, VerticalizzazioneProtocolloElios.NOME_VERTICALIZZAZIONE, software, codiceComune)
        {

        }
        public int AreaOmogenea
        {
            get
            {
                var area = this.GetInt(AREAOMOGENEA);
                if (!area.HasValue)
                {
                    throw new Exception($"Manca la configurazione del parametro {AREAOMOGENEA} della verticalizzazione {NOME_VERTICALIZZAZIONE}");
                }

                return area.Value;
            }
        }

        public string Ente => this.GetString(ENTE);
        public string Password => this.GetString(PASSWORD);
        public string UrlAutenticazione => this.GetString(URL_AUTENTICAZIONE);
        public string UrlConfigurazione => this.GetString(URL_CONFIGURAZIONE);
        public string UrlFascicolazione => this.GetString(URL_FASCICOLAZIONE);
        public string UrlProtocollazione => this.GetString(URL_PROTOCOLLAZIONE);
        public string Utente => this.GetString(UTENTE);

    }
}
