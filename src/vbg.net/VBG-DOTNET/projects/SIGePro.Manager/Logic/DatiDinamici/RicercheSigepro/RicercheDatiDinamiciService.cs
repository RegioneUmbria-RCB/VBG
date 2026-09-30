using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.StrutturaModello;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.RicercheSigepro
{
    public class RicercheDatiDinamiciService
    {
        public class RisultatoRicercaDatiDinamici
        {
            public string Value { get; set; }
            public string Label { get; set; }
        }

        private readonly AuthenticationInfo _authenticationInfo;

        public RicercheDatiDinamiciService(AuthenticationInfo authenticationInfo)
        {
            this._authenticationInfo = authenticationInfo;
        }


        public RisultatoRicercaDatiDinamici InitializeControl(int idCampo, string valore)
        {
            if (String.IsNullOrEmpty(valore))
                return new RisultatoRicercaDatiDinamici { Value = "", Label = "" };

            using (var db = this._authenticationInfo.CreateDatabase())
            {
                var proprietaReader = new ProprietaCampoRicercaReader(idCampo, new CampiProprietaManager(db, this._authenticationInfo.IdComune));
                var ricercheService = new RicercheSigeproHelper(this._authenticationInfo, proprietaReader);

                var results = ricercheService
                                .ExecuteQuery(valore, false, Enumerable.Empty<ValoreFiltroRicerca>(), true);

                var result = results.Risultati
                                    .Select(x => new RisultatoRicercaDatiDinamici { Value = x.Value, Label = x.Label })
                                    .FirstOrDefault();

                if (result != null)
                    return result;

                return new RisultatoRicercaDatiDinamici { Value = valore, Label = "ERRORE:Impossibile inizializzare il campo" };
            }
        }

        public AutocompleteSearchResultDto GetCompletionList(int idCampo, string partial, IEnumerable<ValoreFiltroRicerca> filtri)
        {
            if (String.IsNullOrEmpty(partial))
            {
                return new AutocompleteSearchResultDto
                {
                    Risultati = new[] { new RisultatoRicercaDatiDinamiciDto { Value = "", Label = "" } },
                    TotaleRisultatiTrovati = 0
                };
            }

            using (var db = this._authenticationInfo.CreateDatabase())
            {
                var proprietaReader = new ProprietaCampoRicercaReader(idCampo, new CampiProprietaManager(db, this._authenticationInfo.IdComune));
                var ricercheService = new RicercheSigeproHelper(this._authenticationInfo, proprietaReader);

                return ricercheService.ExecuteQuery(partial, true, filtri);
            }

        }
    }
}
