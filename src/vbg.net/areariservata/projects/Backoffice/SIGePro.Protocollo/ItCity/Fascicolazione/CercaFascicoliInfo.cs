using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ItCity.Fascicolazione
{
    public class CercaFascicoliInfo
    {
        public readonly string Titolo;
        public readonly string Classe;
        public readonly string SottoClasse;
        public readonly int? Anno;
        public readonly string Oggetto;
        public readonly int? NumeroFascicolo;

        public CercaFascicoliInfo(string classifica, int? anno, string oggetto, int? numeroFascicolo)
        {
            if (!string.IsNullOrEmpty(classifica) && classifica.IndexOf(".") >= 0)
            {
                var tcs = classifica.Split('.');
                this.Titolo = tcs[0];
                this.Classe = tcs[1];
                this.SottoClasse = tcs[2];
            }

            this.Anno = anno;
            this.Oggetto = oggetto;
            this.NumeroFascicolo = numeroFascicolo;

        }
    }
}
