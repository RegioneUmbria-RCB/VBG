using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ItCity
{
    public class ParametriRegoleInfo
    {
        public string Url { get; private set; }
        public string Username { get; private set; }
        public string Password { get; private set; }
        public string Sigla { get; private set; }

        public ParametriRegoleInfo(ProtocolloLogs logs, VerticalizzazioneProtocolloItCity vert)
        {
            if (!vert.Attiva)
                throw new Exception("La verticalizzazione PROTOCOLLO_ITCITY non è attiva");

            try
            {
                this.Url = vert.Url;
                this.Username = vert.Username;
                this.Password = vert.Password;
                this.Sigla = vert.Sigla;
            }
            catch (Exception ex)
            {
                throw new Exception($"RECUPERO DEI VALORI DALLA VERTICALIZZAZIONE PROTOCOLLO_ITCITY FALLITO, {ex.Message}", ex);
            }
        }
    }
}
