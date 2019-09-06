using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ApSystems
{
    public class VerticalizzazioniWrapper
    {
        public bool EscludiClassifica { get; private set; }
        public string Password { get; private set; }
        public string Username { get; private set; }
        public string Url { get; private set; }
        public TipoProtocollazione TipoProtocollazionePartenza { get; private set; }

        public enum TipoProtocollazione { INSERIMENTO_NORMALE_PARTENZA, INSERIMENTO_BOZZA_INTERNA_PARTENZA };

        VerticalizzazioneProtocolloApsystems _paramVert;

        public VerticalizzazioniWrapper(VerticalizzazioneProtocolloApsystems paramVert)
        {
            if (!paramVert.Attiva)
                throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_APSYSTEMS NON E' ATTIVA");

            _paramVert = paramVert;

            EstraiParametri();
        }

        private void VerificaIntegritaParametri()
        {
            if (String.IsNullOrEmpty(_paramVert.Username))
                throw new Exception("IL PARAMETRO USERNAME NON E' STATO VALORIZZATO, QUESTO PARAMETRO E' NECESSARIO PER ESEGUIRE L'AUTENTICAZIONE");

            if (String.IsNullOrEmpty(_paramVert.Password))
                throw new Exception("IL PARAMETRO PASSWORD NON E' STATO VALORIZZATO, QUESTO PARAMETRO E' NECESSARIO PER ESEGUIRE L'AUTENTICAZIONE");

            if (String.IsNullOrEmpty(_paramVert.Url))
                throw new Exception("IL PARAMETRO URL RIGUARDANTE L'ENDPOINT DEL WEB SERVICE NON E' STATO VALORIZZATO");
        }

        private void EstraiParametri()
        {
            VerificaIntegritaParametri();

            Username = _paramVert.Username;
            Password = _paramVert.Password;
            EscludiClassifica = _paramVert.EscludiClassifica == "1";
            Url = _paramVert.Url;

            TipoProtocollazionePartenza = TipoProtocollazione.INSERIMENTO_NORMALE_PARTENZA;

            if (_paramVert.TipoProtocollazionePartenza == "1")
                TipoProtocollazionePartenza = TipoProtocollazione.INSERIMENTO_BOZZA_INTERNA_PARTENZA;

        }
    }
}
