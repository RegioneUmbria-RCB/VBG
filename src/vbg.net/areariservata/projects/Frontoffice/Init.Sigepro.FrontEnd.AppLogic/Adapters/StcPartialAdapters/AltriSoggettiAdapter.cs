using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
    internal class AltriSoggettiAdapter : IStcPartialAdapter
    {
        AnagraficheHelper _anagraficheHelper = new AnagraficheHelper();
        IConfigurazione<ParametriStc> _parametriStc;

        public AltriSoggettiAdapter(IConfigurazione<ParametriStc> parametriStc)
        {
            this._parametriStc = parametriStc;
        }

        public void Adapt(GestionePresentazioneDomanda.IDomandaOnlineReadInterface _readInterface, StcService.DettaglioPraticaType _dettaglioPratica)
        {
            _dettaglioPratica.altriSoggetti = _readInterface.Anagrafiche
                                                        .GetAltriSoggetti()
                                                        .Select(x => new AltriSoggettiType
                                                        {
                                                            soggetto = _anagraficheHelper.AdattaAnagrafica(x),
                                                            tipoRapporto = _anagraficheHelper.AdattaRuolo(x),
                                                            anagraficaCollegata = _anagraficheHelper.AdattaAnagrafica(x.AnagraficaCollegata)
                                                        }).ToArray();

            if (this._parametriStc.Parametri.IncludiTecnicoInSoggettiCollegati)
            {
                var list = _dettaglioPratica.altriSoggetti.ToList();
                var tecnico = _readInterface.Anagrafiche.GetTecnico();

                if (tecnico != null)
                {
                    list.Add(new AltriSoggettiType
                    {
                        soggetto = _anagraficheHelper.AdattaAnagrafica(tecnico),
                        tipoRapporto = _anagraficheHelper.AdattaRuolo(tecnico),
                        anagraficaCollegata = null
                    });
                }

                _dettaglioPratica.altriSoggetti = list.ToArray();
            }

        }
    }
}
