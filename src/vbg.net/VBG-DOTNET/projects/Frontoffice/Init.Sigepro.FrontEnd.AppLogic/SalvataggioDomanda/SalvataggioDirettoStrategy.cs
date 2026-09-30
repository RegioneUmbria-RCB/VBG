// -----------------------------------------------------------------------
// <copyright file="SalvataggioDirettoStrategy.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda
{
    using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.DomandeInBozza;
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
    using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
    using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda.EliminazioneDomanda;
    using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda.Repositories;
    using Init.SIGePro.Manager.DTO.DatiDomandaOnline;
    using log4net;
    using System;
    using System.Threading.Tasks;

    /// <summary>
    /// Effettua il caricamento ed il salvataggio della domanda direttamente senza passare per nessun metodo di caching
    /// </summary>
    public class SalvataggioDirettoStrategy : ISalvataggioDomandaStrategy
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(SalvataggioDirettoStrategy));
        private readonly IDatiDomandaFoRepository _datiDomandaFoRepository;
        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;
        private readonly IAuthenticationDataResolver _authenticationDataResolver;
        private readonly IEventiDomandeInBozzaService _eventiDomandeInBozzaService;
        private readonly IEliminazioneBozzaDomandaService _eliminazioneBozzaDomandaService;

        public SalvataggioDirettoStrategy(IAliasSoftwareResolver aliasSoftwareResolver, IAuthenticationDataResolver authenticationDataResolver,
            IDatiDomandaFoRepository datiDomandaFoRepository,
            IEventiDomandeInBozzaService eventiDomandeInBozzaService, IEliminazioneBozzaDomandaService eliminazioneBozzaDomandaService)
        {
            this._aliasSoftwareResolver = aliasSoftwareResolver;
            this._datiDomandaFoRepository = datiDomandaFoRepository;
            this._authenticationDataResolver = authenticationDataResolver;
            this._eventiDomandeInBozzaService = eventiDomandeInBozzaService;
            this._eliminazioneBozzaDomandaService = eliminazioneBozzaDomandaService;
        }

        #region ILogicaSalvataggioDomanda Members

        public async Task<DomandaOnline> GetByIdAsync(int idPresentazione)
        {
            var alias = this._aliasSoftwareResolver.AliasComune;
            var software = this._aliasSoftwareResolver.Software;

            var datiDomanda = await this._datiDomandaFoRepository.LeggiDatiDomandaAsync(idPresentazione);

            if (datiDomanda != null)
            {
                return await this.GetDatiDomandaEsistenteAsync(alias, datiDomanda);
            }

            var codiceFiscale = this._authenticationDataResolver.DatiAutenticazione.DatiUtente.Codicefiscale;
            var dataKey = PresentazioneIstanzaDataKey.New(alias, software, codiceFiscale, idPresentazione);
            var dataSet = new PresentazioneIstanzaDbV2();

            return new DomandaOnline(dataKey, dataSet, false);
        }

        private async Task<DomandaOnline> GetDatiDomandaEsistenteAsync(string alias, DatiDomandaOnlineDto datiDomanda)
        {
            var dataKey = PresentazioneIstanzaDataKey.FromSerializationCode(datiDomanda.IdentificativoDomanda);
            var dataSet = await this._datiDomandaFoRepository.LeggiDataSetDomandaAsync(alias, datiDomanda);

            return new DomandaOnline(dataKey, dataSet, false);
        }

        public DomandaOnline GetById(int idPresentazione)
        {
            var alias = this._aliasSoftwareResolver.AliasComune;
            var software = this._aliasSoftwareResolver.Software;

            PresentazioneIstanzaDataKey dataKey;
            PresentazioneIstanzaDbV2 dataSet;

            var datiDomanda = this._datiDomandaFoRepository.LeggiDatiDomanda(alias, idPresentazione);

            if (datiDomanda != null)
            {
                dataSet = this._datiDomandaFoRepository.LeggiDataSetDomanda(alias, datiDomanda);
                dataKey = PresentazioneIstanzaDataKey.FromSerializationCode(datiDomanda.IdentificativoDomanda);
            }
            else
            {
                var codiceFiscale = this._authenticationDataResolver.DatiAutenticazione.DatiUtente.Codicefiscale;

                dataKey = PresentazioneIstanzaDataKey.New(alias, software, codiceFiscale, idPresentazione);
                dataSet = new PresentazioneIstanzaDbV2();
            }

            return new DomandaOnline(dataKey, dataSet, false);
        }

        public void Elimina(DomandaOnline domanda)
        {
            this._eliminazioneBozzaDomandaService.Elimina(domanda.ReadInterface);
            // this._datiDomandaFoRepository.Elimina(domanda.DataKey.IdComune, domanda.DataKey.IdPresentazione);
        }


        public void Salva(DomandaOnline domanda, bool aggiornaDataultimaModifica)
        {
            if (domanda.DataKey.IdPresentazione <= 0)
                throw new Exception("La domanda che si vuole salvare ha id " + domanda.DataKey.IdPresentazione + " non è possibile salvare una domanda con un id < 0");

            //_eventDispatcher.DispatchEvents(domanda);

            try
            {
                this._datiDomandaFoRepository.Salva(domanda, aggiornaDataultimaModifica);

                this.ProcessPostSave(domanda);
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nel salvataggio dei dati della domanda {domanda.DataKey.IdPresentazione}: {ex}");

                throw;
            }
        }

        private void ProcessPostSave(DomandaOnline domanda)
        {
            //domanda.RefreshInternalStatus();

            if (!domanda.Flags.Presentata)
            {
                this._eventiDomandeInBozzaService.DomandaInBozzaModificata(domanda.DataKey.CodiceUtente, domanda.ReadInterface);
            }
        }

        public async ValueTask SalvaAsync(DomandaOnline domanda, bool aggiornaDataultimaModifica = true)
        {
            if (domanda.DataKey.IdPresentazione <= 0)
                throw new Exception("La domanda che si vuole salvare ha id " + domanda.DataKey.IdPresentazione + " non è possibile salvare una domanda con un id < 0");

            //_eventDispatcher.DispatchEvents(domanda);

            try
            {
                await this._datiDomandaFoRepository.SalvaAsync(domanda, aggiornaDataultimaModifica = true);

                this.ProcessPostSave(domanda);
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nel salvataggio dei dati della domanda {domanda.DataKey.IdPresentazione}: {ex}");

                throw;
            }
        }

        public byte[] GetAsXml(int idDomanda)
        {
            var domanda = this.GetById(idDomanda);

            return this.GetAsXml(domanda);
        }

        public byte[] GetAsXml(DomandaOnline domanda)
        {
            return this._datiDomandaFoRepository.ConvertToXml(domanda);
        }

        public void ImpostaIdIstanzaOrigine(int idDomanda, int idDomandaOrigine)
        {
            this._datiDomandaFoRepository.ImpostaIdIstanzaOrigine(idDomanda, idDomandaOrigine);
        }

        #endregion

    }
}
