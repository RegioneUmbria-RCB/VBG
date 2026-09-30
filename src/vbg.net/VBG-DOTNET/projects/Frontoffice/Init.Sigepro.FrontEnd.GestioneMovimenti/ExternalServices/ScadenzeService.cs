// -----------------------------------------------------------------------
// <copyright file="IScadenzeService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices
{
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
    using Init.SIGePro.Manager.DTO.Scadenzario;
    using System;
    using System.Collections.Generic;
    using System.Linq;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public interface IScadenzeService
    {
        IEnumerable<ElementoListaScadenzeDto> GetListaScadenzeByCodiceFiscale(string software, string codiceFiscale);
        IEnumerable<ElementoListaScadenzeDto> GetListaScadenzeByCodiceIstanza(string software, int codiceIstanza, string codiceUtenteCheEffettuaMovimento);
        ScadenzaDto GetById(int idScadenza);
        IEnumerable<ElementoListaScadenzeDto> GetListaScadenzeEntiTerziByNumeroIstanza(string software, string numeroIstanza, string partitaIvaAmministrazione);
        IEnumerable<ElementoListaScadenzeDto> GetListaScadenzeEntiTerzi(string partitaIvaAmministrazione);
    }

    public class ScadenzeService : IScadenzeService
    {
        private readonly MovimentiBackofficeServiceCreator _serviceCreator;
        private readonly IAliasResolver _aliasResolver;
        private readonly IConfigurazione<ParametriScadenzario> _configurazione;

        public ScadenzeService(IConfigurazione<ParametriScadenzario> configurazione, IAliasResolver aliasResolver, MovimentiBackofficeServiceCreator serviceCreator)
        {
            if (configurazione == null)
                throw new ArgumentNullException(nameof(configurazione));

            if (aliasResolver == null)
                throw new ArgumentNullException(nameof(aliasResolver));

            if (serviceCreator == null)
                throw new ArgumentNullException(nameof(serviceCreator));
            //Condition.Requires(configurazione, "configurazione").IsNotNull();
            //Condition.Requires(aliasResolver, "aliasResolver").IsNotNull();
            //Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

            this._serviceCreator = serviceCreator;
            this._aliasResolver = aliasResolver;
            this._configurazione = configurazione;
        }


        #region IScadenzeService Members

        public IEnumerable<ElementoListaScadenzeDto> GetListaScadenzeByCodiceFiscale(string software, string codiceFiscale)
        {
            var cfg = this._configurazione.Parametri;

            var richiesta = new RichiestaListaScadenzeDto
            {
                CodEnte = this._aliasResolver.AliasComune,
                CodSportello = software,
                FiltroFoSoggettiEsterni = FoTipiSoggettiEsterniEnum.FrontofficeRichiedenti
            };

            richiesta.CodiceFiscaleSoggetto = codiceFiscale;

            return GetListaScadenze(richiesta);
        }

        public IEnumerable<ElementoListaScadenzeDto> GetListaScadenzeByCodiceIstanza(string software, int codiceIstanza, string codiceUtenteCheEffettuaMovimento)
        {
            if (String.IsNullOrEmpty(codiceUtenteCheEffettuaMovimento))
            {
                return Enumerable.Empty<ElementoListaScadenzeDto>();
            }

            var richiesta = new RichiestaListaScadenzeDto
            {
                CodEnte = this._aliasResolver.AliasComune,
                CodSportello = software,
                FiltroFoSoggettiEsterni = FoTipiSoggettiEsterniEnum.FrontofficeRichiedenti,
                IdPratica = codiceIstanza.ToString(),
                CodiceFiscaleSoggetto = codiceUtenteCheEffettuaMovimento
            };

            return GetListaScadenze(richiesta);
        }

        public ScadenzaDto GetById(int idScadenza)
        {
            using (var ws = _serviceCreator.CreateClient())
            {
                return ws.Service.GetScadenzaById(ws.Token, idScadenza);
            }
        }

        #endregion

        private IEnumerable<ElementoListaScadenzeDto> GetListaScadenze(RichiestaListaScadenzeDto richiesta)
        {
            using (var ws = _serviceCreator.CreateClient())
            {
                return ws.Service.GetListaScadenze(ws.Token, richiesta);
            }
        }

        public IEnumerable<ElementoListaScadenzeDto> GetListaScadenzeEntiTerziByNumeroIstanza(string software, string numeroIstanza, string partitaIvaAmministrazione)
        {
            var cfg = this._configurazione.Parametri;

            var richiesta = new RichiestaListaScadenzeDto
            {
                CodEnte = this._aliasResolver.AliasComune,
                CodSportello = software,
                FiltroFoSoggettiEsterni = FoTipiSoggettiEsterniEnum.FrontofficeAmministrazioni,
                NumeroPratica = numeroIstanza,
                DatiAmministrazione = new DatiAmministrazioneDto
                {
                    PartitaIva = partitaIvaAmministrazione
                }
            };

            return GetListaScadenze(richiesta);
        }

        public IEnumerable<ElementoListaScadenzeDto> GetListaScadenzeEntiTerzi(string partitaIvaAmministrazione)
        {
            var cfg = this._configurazione.Parametri;

            var richiesta = new RichiestaListaScadenzeDto
            {
                CodEnte = this._aliasResolver.AliasComune,
                FiltroFoSoggettiEsterni = FoTipiSoggettiEsterniEnum.FrontofficeAmministrazioni,
                DatiAmministrazione = new DatiAmministrazioneDto
                {
                    PartitaIva = partitaIvaAmministrazione
                }
            };

            return GetListaScadenze(richiesta);
        }
    }

}
