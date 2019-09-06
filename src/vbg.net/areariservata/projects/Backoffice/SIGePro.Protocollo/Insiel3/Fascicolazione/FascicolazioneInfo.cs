using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Insiel3.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Insiel3.Fascicolazione
{
    public class FascicolazioneInfo
    {
        public string AnnoFascicolo { get; private set; }
        public string NumeroFascicolo { get; private set; }
        public string Classifica { get; private set; }
        public string CodiceUfficio { get; private set; }
        public string CodiceUfficioOperante { get; private set; }
        public bool UsaLivelliClassifica { get; private set; }
        public string Oggetto { get; private set; }
        public string NumeroProtocollo { get; set; }
        public string AnnoProtocollo { get; set; }
        public string Flusso { get; private set; }
        public string CodiceRegistro { get; private set; }

        public FascicolazioneInfo(int? annoFascicolo, string numeroFascicolo, string classificaFascicolo, string oggettoFascicolo, string codiceUfficio, InsielVerticalizzazioniConfiguration vert, string numeroProtocollo, string annoProtocollo, string flusso)
        {
            this.AnnoFascicolo = annoFascicolo.GetValueOrDefault(DateTime.Now.Year).ToString();
            this.NumeroFascicolo = numeroFascicolo;
            this.Classifica = classificaFascicolo;
            this.CodiceUfficio = codiceUfficio;
            this.CodiceUfficioOperante = vert.CodiceUfficioOperante;
            this.UsaLivelliClassifica = vert.UsaLivelliClassifica;
            this.Oggetto = oggettoFascicolo;
            this.NumeroProtocollo = numeroProtocollo;
            this.AnnoProtocollo = annoProtocollo;
            this.Flusso = flusso;
            this.CodiceRegistro = vert.CodiceRegistro;
        }
    }
}
