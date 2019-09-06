using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
    internal class AltriSoggettiAdapter : IStcPartialAdapter
    {
        AnagraficheHelper _anagraficheHelper = new AnagraficheHelper();
        IConfigurazione<ParametriStc> _parametriStc;
        private readonly IFormeGiuridicheRepository _formeGiuridicheRepository;

        public AltriSoggettiAdapter(IConfigurazione<ParametriStc> parametriStc, IFormeGiuridicheRepository formeGiuridicheRepository)
        {
            this._parametriStc = parametriStc;
            this._formeGiuridicheRepository = formeGiuridicheRepository;
        }

        public void Adapt(GestionePresentazioneDomanda.IDomandaOnlineReadInterface _readInterface, StcService.DettaglioPraticaType _dettaglioPratica)
        {
            _dettaglioPratica.altriSoggetti = _readInterface.Anagrafiche
                                                        .GetAltriSoggetti()
                                                        .Select(x => new AltriSoggettiType
                                                        {
                                                            soggetto = _anagraficheHelper.AdattaAnagrafica(x, this._formeGiuridicheRepository),
                                                            tipoRapporto = _anagraficheHelper.AdattaRuolo(x),
                                                            anagraficaCollegata = _anagraficheHelper.AdattaAnagrafica(x.AnagraficaCollegata, this._formeGiuridicheRepository)
                                                        }).ToArray();

            if (this._parametriStc.Parametri.IncludiTecnicoInSoggettiCollegati)
            {
                var list = _dettaglioPratica.altriSoggetti.ToList();
                var tecnico = _readInterface.Anagrafiche.GetTecnico();

                if (tecnico != null)
                {
                    list.Add(new AltriSoggettiType
                    {
                        soggetto = _anagraficheHelper.AdattaAnagrafica(tecnico, this._formeGiuridicheRepository),
                        tipoRapporto = _anagraficheHelper.AdattaRuolo(tecnico),
                        anagraficaCollegata = null
                    });
                }

                _dettaglioPratica.altriSoggetti = list.ToArray();
            }

        }
    }
}
