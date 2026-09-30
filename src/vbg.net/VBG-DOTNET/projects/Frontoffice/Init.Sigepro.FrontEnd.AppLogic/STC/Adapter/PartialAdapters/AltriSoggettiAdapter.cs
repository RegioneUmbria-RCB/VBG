using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.LogicaRisoluzioneSoggetti;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
    internal class AltriSoggettiAdapter : IStcPartialAdapter
    {
        private readonly AnagraficheHelper _anagraficheHelper = new();
        private readonly IConfigurazione<ParametriStc> _parametriStc;
        private readonly ILogicaRisoluzioneTecnico _logicaRisoluzioneTecnico;

        public AltriSoggettiAdapter(IConfigurazione<ParametriStc> parametriStc, ILogicaRisoluzioneTecnico logicaRisoluzioneTecnico)
        {
            this._parametriStc = parametriStc;
            this._logicaRisoluzioneTecnico = logicaRisoluzioneTecnico;
        }

        public void Adapt(GestionePresentazioneDomanda.IDomandaOnlineReadInterface _readInterface, StcService.DettaglioPraticaType _dettaglioPratica)
        {
            _dettaglioPratica.altriSoggetti = _readInterface.Anagrafiche
                                                        .GetAltriSoggetti(this._logicaRisoluzioneTecnico)
                                                        .Select(x => new AltriSoggettiType
                                                        {
                                                            soggetto = this._anagraficheHelper.AdattaAnagrafica(x),
                                                            tipoRapporto = this._anagraficheHelper.AdattaRuolo(x),
                                                            anagraficaCollegata = this._anagraficheHelper.AdattaAnagrafica(x.AnagraficaCollegata)
                                                        }).ToArray();

            if (this._parametriStc.Parametri.IncludiTecnicoInSoggettiCollegati)
            {
                var list = _dettaglioPratica.altriSoggetti.ToList();
                var tecnico = _readInterface.Anagrafiche.GetTecnico(this._logicaRisoluzioneTecnico);

                if (tecnico is not null)
                {
                    list.Add(new AltriSoggettiType
                    {
                        soggetto = this._anagraficheHelper.AdattaAnagrafica(tecnico),
                        tipoRapporto = this._anagraficheHelper.AdattaRuolo(tecnico),
                        anagraficaCollegata = null
                    });
                }

                _dettaglioPratica.altriSoggetti = list.ToArray();
            }

        }
    }
}
