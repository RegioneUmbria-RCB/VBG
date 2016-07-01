using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.Folium.Assegnazione
{
    public class FoliumAssegnazioneInputConfiguration
    {
        public readonly string CodiceAssegnazione;
        public readonly long IdProtocollo;
        public readonly string UfficioAssegnatario;
        public readonly string UtenteAssegnatario;

        public FoliumAssegnazioneInputConfiguration(string codiceAssegnazione, string ufficioAssegnatario, long idProtocollo, string utenteAssegnatario)
        {
            CodiceAssegnazione = codiceAssegnazione;
            IdProtocollo = idProtocollo;
            UfficioAssegnatario = ufficioAssegnatario;
            UtenteAssegnatario = String.IsNullOrEmpty(utenteAssegnatario) ? null : utenteAssegnatario;
        }
    }
}
