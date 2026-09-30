using Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.ConfigurazioneBackoffice;
using System;
using System.Collections.Generic;
using System.Linq;
using static Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.DatiDinamici.DatiDinamiciOnceOnlyClient;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.DatiDinamici
{
    public class ValoriPrecompilabiliOnceOnly
    {
        public class ValoreCampoOnceOnly
        {
            public class ValoreCampo
            {
                public int Indice { get; set; }
                public int IndiceMolteplicita { get; set; }
                public string Valore { get; set; }
                public string ValoreDecodificato { get; set; }
            }

            private readonly List<ValoreCampo> _valori = new List<ValoreCampo>();

            public ValoreCampoOnceOnly(int idCampo, string nomeCampo)
            {
                this.IdCampo = idCampo;
                this.NomeCampo = nomeCampo;
            }

            public int IdCampo { get; }
            public string NomeCampo { get; }

            public IEnumerable<ValoreCampo> Valori => this._valori;

            public void AggiungiValore(int indice, int indiceMolteplicita, string valore, string valoreDecodificato)
            {
                this._valori.Add(new ValoreCampo
                {
                    Indice = indice,
                    IndiceMolteplicita = indiceMolteplicita,
                    Valore = valore,
                    ValoreDecodificato = valoreDecodificato
                });
            }
        }

        private readonly ListaIdentificativiOnceOnly _identificativiDatiOnceOnly;
        private readonly PostDatoOnceOnlyResponse _valoriDatiOnceOnly;

        public ListaIdentificativiOnceOnly ListaIdentificativi => this._identificativiDatiOnceOnly ?? ListaIdentificativiOnceOnly.Empty;

        private ValoriPrecompilabiliOnceOnly() { }

        public ValoriPrecompilabiliOnceOnly(ListaIdentificativiOnceOnly identificativiDatiOnceOnly, PostDatoOnceOnlyResponse valoriDatiOnceOnly)
        {
            this._identificativiDatiOnceOnly = identificativiDatiOnceOnly;
            this._valoriDatiOnceOnly = valoriDatiOnceOnly;
        }

        public static ValoriPrecompilabiliOnceOnly Vuoto()
        {
            return new ValoriPrecompilabiliOnceOnly();
        }

        public static ValoriPrecompilabiliOnceOnly VuotoMaSupportaPrecompilazione(ListaIdentificativiOnceOnly identificativiDatiOnceOnly)
        {
            if (identificativiDatiOnceOnly is null)
            {
                throw new ArgumentNullException(nameof(identificativiDatiOnceOnly));
            }

            return new ValoriPrecompilabiliOnceOnly(identificativiDatiOnceOnly, null);
        }

        public bool EsistonoValoriPrecompilabili => this._valoriDatiOnceOnly?.Campi?.Any() ?? false;

        internal IEnumerable<ValoreCampoOnceOnly> GetValoriOnceOnly()
        {
            if (!this.EsistonoValoriPrecompilabili)
            {
                return Enumerable.Empty<ValoreCampoOnceOnly>();
            }

            var rval = new List<ValoreCampoOnceOnly>();
            var dictionaryIdentificativi = this._identificativiDatiOnceOnly.CreateDictionary();

            foreach (var campoOnceOnly in this._valoriDatiOnceOnly.Campi)
            {
                if (!dictionaryIdentificativi.TryGetValue(campoOnceOnly.FonteInterna, out var idCampi))
                {
                    // Non dovrebbe succedere eppure siamo qui...
                    continue;
                }

                foreach (var rifCampo in idCampi)
                {
                    var valoreOO = new ValoreCampoOnceOnly(rifCampo.IdCampo, rifCampo.NomeCampo);

                    foreach (var val in campoOnceOnly.Valori)
                    {
                        valoreOO.AggiungiValore(val.Indice, val.IndiceMolteplicita, val.Valore, val.ValoreDecodificato);
                    }

                    rval.Add(valoreOO);
                }
            }

            return rval; ;
        }
    }
}