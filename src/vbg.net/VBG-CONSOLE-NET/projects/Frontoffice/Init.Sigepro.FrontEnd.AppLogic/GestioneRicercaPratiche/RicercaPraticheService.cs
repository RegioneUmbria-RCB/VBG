using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.RicercaPraticheWs;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using log4net;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneRicercaPratiche
{


    public class RicercaPraticheService : IRicercaPraticheService
    {
        private static class Constants
        {
            public const string DatiExtraKey = "RicercaPraticheService.IstanzaCercata";
        }

        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly RicercaPraticheServiceCreator _ricercaPraticheProxy;
        private readonly IConfigurazione<ParametriStcConsole> _parametriStc;
        private readonly CopiaDatiDomandaDaRisultatoRicercaService _copiaDatiDomandaService;
        private readonly ILog _log = LogManager.GetLogger(typeof(RicercaPraticheService));

        public RicercaPraticheService(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, RicercaPraticheServiceCreator ricercaPraticheProxy,
            IConfigurazione<ParametriStcConsole> parametriStc, CopiaDatiDomandaDaRisultatoRicercaService copiaDatiDomandaService)
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._ricercaPraticheProxy = ricercaPraticheProxy;
            this._parametriStc = parametriStc;
            this._copiaDatiDomandaService = copiaDatiDomandaService;
        }

        public RisultatoRicercaPratiche TrovaPraticaDaNumeroIstanza(int idDomanda, string numeroIstanza)
        {

            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var cfRichiedente = domanda.DataKey.CodiceUtente.ToUpper();
            var codiceComune = domanda.ReadInterface.AltriDati.CodiceComune;
            var software = this._parametriStc.Parametri.SportelloDestinatario.IdSportello;

            try
            {
                this._log.Info($"Ricerca pratiche per numero istanza. Parametri: software={software}, codiceComune={codiceComune}, numeroIstanza={numeroIstanza}, cfRichiedente={cfRichiedente} ");

                var pratica = this._ricercaPraticheProxy.Call(ws => ws.Service.RicercaPraticaDaNumeroIstanza(ws.Token, software, codiceComune, numeroIstanza, cfRichiedente));

                this._log.Info($"Pratica trovata: {pratica != null}");

                return pratica;
            }
            catch (Exception ex)
            {
                this._log.Error($"Ricerca pratiche per numero istanza FALLITA. Parametri: software={software}, codiceComune={codiceComune}, numeroIstanza={numeroIstanza}, cfRichiedente={cfRichiedente}, ex={ex}");

                throw;
            }
        }

        public RisultatoRicercaPratiche TrovaPraticaDaEstremiProtocollo(int idDomanda, string numeroProtocollo, DateTime dataProtocollo)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var cfRichiedente = domanda.DataKey.CodiceUtente.ToUpper();
            var codiceComune = domanda.ReadInterface.AltriDati.CodiceComune;
            var software = this._parametriStc.Parametri.SportelloDestinatario.IdSportello;

            try
            {
                this._log.Info($"Ricerca pratiche per estremi protocollo. Parametri: software={software}, codiceComune={codiceComune}, numeroProtocollo={numeroProtocollo}, dataProtocollo={dataProtocollo}, cfRichiedente={cfRichiedente}");

                var pratica = this._ricercaPraticheProxy.Call(ws => ws.Service.RicercaPraticaDaEstremiProtocollo(ws.Token, software, codiceComune, numeroProtocollo, dataProtocollo, cfRichiedente));

                this._log.Info($"Pratica trovata: {pratica != null}");

                return pratica;
            }
            catch (Exception ex)
            {
                this._log.Error($"Ricerca pratiche per estremi protocollo FALLITA. Parametri: software={software}, codiceComune={codiceComune}, numeroProtocollo={numeroProtocollo}, dataProtocollo={dataProtocollo}, cfRichiedente={cfRichiedente}, ex={ex}");

                throw;
            }
        }

        public void IgnoraRicercaPratiche(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            domanda.WriteInterface.DatiExtra.Delete(Constants.DatiExtraKey);
            domanda.WriteInterface.AltriDati.RimuoviIdDomandaCollegata();

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public void ImpostaDatiPraticaDaRisultatoRicerca(int idDomanda, RisultatoRicercaPratiche risultatoRicerca, CopiaDatiDomandaFlags flags)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            this._copiaDatiDomandaService.ImpostaDatiDomandaDaRicerca(domanda, risultatoRicerca, flags);

            domanda.WriteInterface.DatiExtra.Set(Constants.DatiExtraKey, risultatoRicerca);

            domanda.WriteInterface.AltriDati.ImpostaIdDomandaCollegata(risultatoRicerca.EstremiPratica.CodiceIstanza);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public RisultatoRicercaPratiche GetRisultatoRicerca(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            return domanda.ReadInterface.DatiExtra.Get<RisultatoRicercaPratiche>(Constants.DatiExtraKey);
        }

        public bool RisultatoRicercaPresente(int idDomanda)
        {
            return this.GetRisultatoRicerca(idDomanda) != null;
        }
    }
}
