using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.GeProt.Verticalizzazioni;
using Init.SIGePro.Protocollo.GeProt.Services;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using PersonalLib2.Data;

namespace Init.SIGePro.Protocollo.GeProt.Fascicolazione
{
    public class DatiFascicolazioneConfiguration
    {
        public string CodiceAmministrazione { get; private set; }
        public string CodiceAoo { get; private set; }
        public string NumeroProtocollo { get; private set; }
        public string AnnoProtocollo { get; private set; }
        public string Classifica { get; private set; }
        public string CodiceUfficio { get; private set; }
        public ProtocollazioneService Wrapper { get; private set; }
        public string Oggetto { get; private set; }
        public ProtocolloEnum.AmbitoProtocollazioneEnum Ambito { get; private set; }

        public DatiFascicolazioneConfiguration(VerticalizzazioniConfiguration vert, string[] response, string classifica, string codiceUfficio, string oggetto, ProtocollazioneService service, ProtocolloEnum.AmbitoProtocollazioneEnum ambito)
        {
            this.CodiceAmministrazione = vert.CodiceAmministrazione;
            this.CodiceAoo = vert.CodiceAoo;
            
            this.NumeroProtocollo = Convert.ToInt32(response[3]).ToString();
            this.AnnoProtocollo = DateTime.Parse(response[4]).Year.ToString();
            this.Classifica = classifica;
            this.CodiceUfficio = codiceUfficio;
            this.Wrapper = service;
            this.Oggetto = oggetto;
            this.Ambito = ambito;
        }
    }
}
