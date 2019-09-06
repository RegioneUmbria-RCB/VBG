using Init.SIGePro.Protocollo.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Fascicolazione
{
    public class FascicolazioneInfo
    {
        public string NumeroFascicolo { get; private set; }
        public string AnnoFascicolo { get; private set; }
        public string DataFascicolo { get; private set; }
        public string ClassificaFascicolo { get; private set; }
        public string OggettoFascicolo { get; set; }
        public string NumeroProtocollo { get; set; }
        public string AnnoProtocollo { get; set; }
        public string Utente { get; private set; }
        public string Uo { get; set; }
        public string TipoRegistro { get; private set; }

        public FascicolazioneInfo(Fascicolo datiFascicolo, string utente, string uo, string numeroProtocollo, string annoProtocollo, string tipoRegistro)
        {
            this.NumeroFascicolo = datiFascicolo.NumeroFascicolo;
            this.AnnoFascicolo = datiFascicolo.AnnoFascicolo.HasValue ? datiFascicolo.AnnoFascicolo.Value.ToString() : DateTime.Now.Year.ToString();
            this.DataFascicolo = String.IsNullOrEmpty(datiFascicolo.DataFascicolo) ? DateTime.Now.ToString("dd/MM/yyyy") : datiFascicolo.DataFascicolo;
            this.ClassificaFascicolo = datiFascicolo.Classifica;
            this.OggettoFascicolo = datiFascicolo.Oggetto;
            this.Utente = utente;
            this.Uo = uo;
            this.NumeroProtocollo = numeroProtocollo;
            this.AnnoProtocollo = annoProtocollo;
            this.TipoRegistro = tipoRegistro;
        }
    }
}
