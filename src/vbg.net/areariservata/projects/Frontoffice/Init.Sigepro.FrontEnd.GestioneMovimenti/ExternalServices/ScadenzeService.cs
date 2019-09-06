// -----------------------------------------------------------------------
// <copyright file="IScadenzeService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.Scadenzario;
	using Init.Sigepro.FrontEnd.AppLogic.Common;
	using CuttingEdge.Conditions;
	using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.MovimentiWebService;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface IScadenzeService
	{
		IEnumerable<Scadenza> GetListaScadenzeByCodiceFiscale(string software, string codiceFiscale);
		IEnumerable<Scadenza> GetListaScadenzeByNumeroIstanza(string software, string numeroIstanza);
		Scadenza GetById(int idScadenza);
        IEnumerable<Scadenza> GetListaScadenzeEntiTerziByNumeroIstanza(string software, string numeroIstanza, string partitaIvaAmministrazione);
        IEnumerable<Scadenza> GetListaScadenzeEntiTerzi(string partitaIvaAmministrazione);
    }

	public class ScadenzeService : IScadenzeService
	{
		MovimentiBackofficeServiceCreator _serviceCreator;
		IAliasResolver _aliasResolver;
		IConfigurazione<ParametriScadenzario> _configurazione;

		public ScadenzeService(IConfigurazione<ParametriScadenzario> configurazione,IAliasResolver aliasResolver, MovimentiBackofficeServiceCreator serviceCreator)
		{
			Condition.Requires(configurazione, "configurazione").IsNotNull();
			Condition.Requires(aliasResolver, "aliasResolver").IsNotNull();
			Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();

			this._serviceCreator = serviceCreator;
			this._aliasResolver = aliasResolver;
			this._configurazione = configurazione;
		}


		#region IScadenzeService Members

		public IEnumerable<Scadenza> GetListaScadenzeByCodiceFiscale(string software, string codiceFiscale)
		{
			var cfg = this._configurazione.Parametri;

			var richiesta = new RichiestaListaScadenze
			{
				CodEnte = this._aliasResolver.AliasComune,
				CodSportello = software,
				Filtro_Fo_SoggettiEsterni = "1"
			};

			var codFiscale = new CodFiscale
			{
				cercaAncheComePartitaIva = cfg.CercaPartitaIva,
				cercaComeAzienda = cfg.CercaComeAzienda,
				cercaComeRichiedente = cfg.CercaComeRichiedente,
				cercaComeTecnico = cfg.CercaComeTecnico,
				Value = codiceFiscale
			};

			if (!cfg.CercaPartitaIva && !cfg.CercaComeAzienda && !cfg.CercaComeRichiedente && !cfg.CercaComeTecnico)
			{
				// Probabilmente c'è un problema in configurazione oppure 
				// la configurazione della verticalizzazione non è stata fatta
				// Imposto la ricerca come richiedente per non causare errori
				codFiscale.cercaComeRichiedente = true;
			}

			richiesta.Item = codFiscale;

			return GetListaScadenze( richiesta);
		}

		public IEnumerable<Scadenza> GetListaScadenzeByNumeroIstanza(string software, string numeroistanza)
		{
			var cfg = this._configurazione.Parametri;

			var richiesta = new RichiestaListaScadenze
			{
				CodEnte = this._aliasResolver.AliasComune,
				CodSportello = software,
				Filtro_Fo_SoggettiEsterni = "1",
				NumeroPratica = numeroistanza
			};

			return GetListaScadenze(richiesta);
		}

		public Scadenza GetById(int idScadenza)
		{
			using (var ws = _serviceCreator.CreateClient())
			{
				return new Scadenza(ws.Service.GetScadenza(ws.Token, idScadenza));
			}
		}

		#endregion

		private IEnumerable<Scadenza> GetListaScadenze( RichiestaListaScadenze richiesta)
		{
			using (var ws = _serviceCreator.CreateClient())
			{
				return ws.Service.GetListaScadenze(ws.Token, richiesta).Select( x => new Scadenza( x ));
			}
		}

        public IEnumerable<Scadenza> GetListaScadenzeEntiTerziByNumeroIstanza(string software, string numeroIstanza, string partitaIvaAmministrazione)
        {
            var cfg = this._configurazione.Parametri;

            var richiesta = new RichiestaListaScadenze
            {
                CodEnte = this._aliasResolver.AliasComune,
                CodSportello = software,
                Filtro_Fo_SoggettiEsterni = "2",
                NumeroPratica = numeroIstanza,
                DatiAmministrazione = new DatiAmministrazione
                {
                    PartitaIva = partitaIvaAmministrazione
                }
            };

            return GetListaScadenze(richiesta);
        }

        public IEnumerable<Scadenza> GetListaScadenzeEntiTerzi(string partitaIvaAmministrazione)
        {
            var cfg = this._configurazione.Parametri;

            var richiesta = new RichiestaListaScadenze
            {
                CodEnte = this._aliasResolver.AliasComune,
                Filtro_Fo_SoggettiEsterni = "2",
                DatiAmministrazione = new DatiAmministrazione
                {
                    PartitaIva = partitaIvaAmministrazione
                }
            };

            return GetListaScadenze(richiesta);
        }
    }

}
