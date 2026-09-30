using Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.DatiDinamici;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.ConfigurazioneBackoffice
{
    public class ListaIdentificativiOnceOnly
    {
        public class RiferimentoCampo
        {
            public int IdCampo { get; }
            public string NomeCampo { get; }
            public bool IsUpload { get; }

            public RiferimentoCampo(int idCampo, string nomeCampo, bool isUpload)
            {
                this.IdCampo = idCampo;
                this.NomeCampo = nomeCampo;
                this.IsUpload = isUpload;
            }
        }

        public IEnumerable<IdentificativoDatoOnceOnly> Identificativi { get; }

        public static ListaIdentificativiOnceOnly Empty { get; } = new ListaIdentificativiOnceOnly();

        private ListaIdentificativiOnceOnly() : this(Array.Empty<IdentificativoDatoOnceOnly>()) { }

        public ListaIdentificativiOnceOnly(IEnumerable<IdentificativoDatoOnceOnly> identificativi)
        {
            this.Identificativi = identificativi ?? Enumerable.Empty<IdentificativoDatoOnceOnly>();
        }
        public bool Any() => this.Identificativi.Any();

        internal Dictionary<string, List<RiferimentoCampo>> CreateDictionary()
        {
            var rval = new Dictionary<string, List<RiferimentoCampo>>();

            foreach (var id in this.Identificativi)
            {
                if (!rval.TryGetValue(id.FonteInterna, out var listaIdCampi))
                {
                    listaIdCampi = new List<RiferimentoCampo>();
                    rval.Add(id.FonteInterna, listaIdCampi);
                }

                listaIdCampi.Add(new RiferimentoCampo(id.IdCampo, id.NomeCampo, id.IsUpload));
            }

            return rval;
        }

        internal DatiDinamiciOnceOnlyClient.PostDatoOnceOnlyRequest ToPostDatiOnceOnlyRequest(string codiceFiscaleUtenteLoggato)
        {
            return new DatiDinamiciOnceOnlyClient.PostDatoOnceOnlyRequest
            {
                CFUtente = codiceFiscaleUtenteLoggato,
                Fonti = this.Identificativi.Select(x => new DatiDinamiciOnceOnlyClient.PostDatoOnceOnlyRequest.PostDatoOnceOnlyFonti
                {
                    FonteEsterna = x.FonteEsterna,
                    FonteInterna = x.FonteInterna
                }).ToList()
            };
        }
    }
}
