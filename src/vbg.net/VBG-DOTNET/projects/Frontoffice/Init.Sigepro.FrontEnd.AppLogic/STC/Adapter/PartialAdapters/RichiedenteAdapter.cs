using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
    internal class RichiedenteAdapter : IStcPartialAdapter
    {
        private readonly AnagraficheHelper _anagraficheHelper = new AnagraficheHelper();

        public void Adapt(GestionePresentazioneDomanda.IDomandaOnlineReadInterface _readInterface, StcService.DettaglioPraticaType _dettaglioPratica)
        {
            var richiedente = _readInterface.Anagrafiche.GetRichiedente() ?? throw new Exception("Impossibile ricavare il richiedente della domanda");

            if (richiedente.TipoPersona == TipoPersonaEnum.Fisica)
            {
                var datiRichiedente = new RichiedenteType
                {
                    ruolo = this._anagraficheHelper.AdattaRuolo(richiedente),
                    anagrafica = this._anagraficheHelper.AdattaPersonaFisica(richiedente)
                };

                _dettaglioPratica.richiedente = datiRichiedente;
            }
            else
            {
                _dettaglioPratica.aziendaRichiedente = this._anagraficheHelper.AdattaPersonaGiuridica(richiedente);
            }
        }
    }
}
