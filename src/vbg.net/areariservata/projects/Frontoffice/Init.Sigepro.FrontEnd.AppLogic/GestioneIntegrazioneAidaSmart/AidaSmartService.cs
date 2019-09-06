using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.AidaSmart;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneAidaSmart
{
    public class AidaSmartService : IAidaSmartService
    {
        private readonly IConfigurazione<ParametriAidaSmart> _cfg;
        private readonly IASCrossLoginClient _crossLoginClient;

        public AidaSmartService(IConfigurazione<ParametriAidaSmart> cfg, IASCrossLoginClient crossLoginClient)
        {
            this._cfg = cfg;
            this._crossLoginClient = crossLoginClient;
        }

        private string GetCrossLoginToken(AnagraficaUtente utente)
        {
            return this._crossLoginClient.GetCrossLginToken(utente.Nome, utente.Nominativo, utente.Codicefiscale, utente.Sesso);
        }

        public string GetUrlNuovaDomanda(AnagraficaUtente utente)
        {
            var token = this.GetCrossLoginToken(utente);

            return $"{this._cfg.Parametri.UrlNuovaDomanda}&Token={token}&AUTH_TYPE=AUTH_TYPE_LOGIN";
        }

        public string GetUrlIstanzeInSospeso(AnagraficaUtente utente)
        {
            var token = this.GetCrossLoginToken(utente);

            return $"{this._cfg.Parametri.UrlIstanzeInSospeso}&Token={token}&AUTH_TYPE=AUTH_TYPE_LOGIN";
        }
    }
}
