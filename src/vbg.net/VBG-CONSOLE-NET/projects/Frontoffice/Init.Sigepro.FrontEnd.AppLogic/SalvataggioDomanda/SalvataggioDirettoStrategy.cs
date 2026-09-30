// -----------------------------------------------------------------------
// <copyright file="SalvataggioDirettoStrategy.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda
{
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
    using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
    using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
    using log4net;
    using System;

    /// <summary>
    /// Effettua il caricamento ed il salvataggio della domanda direttamente senza passare per nessun metodo di caching
    /// </summary>
    internal class SalvataggioDirettoStrategy : ISalvataggioDomandaStrategy
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(SalvataggioDirettoStrategy));
        private readonly IDatiDomandaFoRepository _datiDomandaFoRepository;
        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;
        private readonly IAuthenticationDataResolver _authenticationDataResolver;

        public SalvataggioDirettoStrategy(IAliasSoftwareResolver aliasSoftwareResolver, IAuthenticationDataResolver authenticationDataResolver, IDatiDomandaFoRepository datiDomandaFoRepository)
        {
            this._aliasSoftwareResolver = aliasSoftwareResolver;
            this._datiDomandaFoRepository = datiDomandaFoRepository;
            this._authenticationDataResolver = authenticationDataResolver;
        }

        #region ILogicaSalvataggioDomanda Members

        public DomandaOnline GetById(int idPresentazione)
        {
            var alias = this._aliasSoftwareResolver.AliasComune;
            var software = this._aliasSoftwareResolver.Software;

            var datiDomanda = this._datiDomandaFoRepository.LeggiDatiDomanda(alias, idPresentazione);

            PresentazioneIstanzaDataKey dataKey = null;
            PresentazioneIstanzaDbV2 dataSet = null;
            bool domandaPresentata = false;


            if (datiDomanda != null)
            {
                domandaPresentata = datiDomanda.FlgPresentata.GetValueOrDefault(0) == 1;
                dataKey = PresentazioneIstanzaDataKey.New(alias, software, datiDomanda.Richiedente.CODICEFISCALE, idPresentazione);
                dataSet = this._datiDomandaFoRepository.LeggiDataSetDomanda(alias, idPresentazione);
            }
            else
            {
                var codiceFiscale = this._authenticationDataResolver.DatiAutenticazione.DatiUtente.Codicefiscale;

                domandaPresentata = false;
                dataKey = PresentazioneIstanzaDataKey.New(alias, software, codiceFiscale, idPresentazione);
                dataSet = new PresentazioneIstanzaDbV2();
            }

            return new DomandaOnline(dataKey, dataSet, false);
        }

        public void Elimina(DomandaOnline domanda)
        {
            this._datiDomandaFoRepository.Elimina(domanda.DataKey.IdComune, domanda.DataKey.IdPresentazione);
        }


        public void Salva(DomandaOnline domanda)
        {
            if (domanda.DataKey.IdPresentazione <= 0)
                throw new Exception("La domanda che si vuole salvare ha id " + domanda.DataKey.IdPresentazione + " non è possibile salvare una domanda con un id < 0");

            //_eventDispatcher.DispatchEvents(domanda);

            this._datiDomandaFoRepository.Salva(domanda);
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
