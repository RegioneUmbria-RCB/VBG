using Init.SIGePro.Protocollo.ItCity.Classificazione;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ItCity.Fascicolazione
{
    public class CercaFascicoloInfo
    {
        public readonly string Titolo;
        public readonly string Classe;
        public readonly string SottoClasse;
        public readonly int AnnoFascicolo;
        public readonly int NumeroFascicolo;
        public readonly int? NumeroSottoFascicolo;

        public CercaFascicoloInfo(string classifica, int anno, string numero, ClassificheServiceWrapper classificheService)
        {
            this.AnnoFascicolo = anno;
            if (String.IsNullOrEmpty(classifica))
            {
                throw new Exception("LA CLASSIFICA NON E' STATA VALORIZZATA");
            }

/*
            int valoreClassifica;

            bool isParsableClassifica = int.TryParse(classifica, out valoreClassifica);
            if (!isParsableClassifica)
            {
                throw new Exception("LA CLASSIFICA DEVE AVERE UN VALORE NUMERICO");
            }

            var classificaProtocollo = classificheService.GetClassificaById(valoreClassifica);
*/
            
            var datiClassifica = classifica.Split('.');
            this.Titolo = datiClassifica[0];

            if (datiClassifica.Length > 1)
            {
                this.Classe = datiClassifica[1];
            }

            if (datiClassifica.Length > 2)
            {
                this.SottoClasse = datiClassifica[2];
            }

            if (String.IsNullOrEmpty(numero))
            {
                throw new Exception("NUMERO FASCICOLO NON VALORIZZATO, NON E' POSSIBILE CERCARE IL FASCICOLO");
            }

            var datiNumeroFascicolo = numero.Split('.');

            int numeroFascicolo;
            bool isParsableNumeroFascicolo = Int32.TryParse(datiNumeroFascicolo[0], out numeroFascicolo);
            if (!isParsableNumeroFascicolo)
            {
                throw new Exception($"IL NUMERO DEL FASCICOLO DEVE AVERE UN VALORE NUMERICO, VALORE IMPOSTATO: {datiNumeroFascicolo[0]}");
            }

            this.NumeroFascicolo = numeroFascicolo;
            this.NumeroSottoFascicolo = 0;

            if (datiNumeroFascicolo.Length > 1)
            {
                int numeroSottoFascicolo;
                bool isParsableNumeroSottoFascicolo = Int32.TryParse(datiNumeroFascicolo[1], out numeroSottoFascicolo);
                if (!isParsableNumeroSottoFascicolo)
                {
                    throw new Exception($"IL NUMERO DEL FASCICOLO DEVE AVERE UN VALORE NUMERICO, VALORE IMPOSTATO: {datiNumeroFascicolo[1]}");
                }

                this.NumeroSottoFascicolo = numeroSottoFascicolo;
            }

        }
    }
}
