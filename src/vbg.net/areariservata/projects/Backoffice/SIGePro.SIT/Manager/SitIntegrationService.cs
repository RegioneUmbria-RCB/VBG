using System;
using System.Linq;
using System.Reflection;
using Init.SIGePro.Sit.Data;
using Init.SIGePro.Sit;
using Init.SIGePro.Verticalizzazioni;
using PersonalLib2.Data;
using log4net;
using Init.SIGePro.Sit.Manager;
using System.Collections.Generic;
using CuttingEdge.Conditions;
using Init.SIGePro.Sit.Data;

namespace Init.SIGePro.Sit.Manager
{
	/// <summary>
	/// Classe astratta che implementa l'interfaccia ISitMgr.
	/// </summary>
	public class SitIntegrationService
	{

		public static class NomiCampiSit
		{
			public const string Fabbricato = "Fabbricato";
			public const string UnitaImmobiliare = "UnitaImmob";
			public const string CodiceVia = "CodiceVia";
			public const string Civico = "Civico";
			public const string Colore = "Colore";
			public const string EsponenteInterno = "EsponenteInterno";
			public const string Esponente = "Esponente";
			public const string Foglio = "Foglio";
			public const string Interno = "Interno";
			public const string Km = "Km";
			public const string Particella = "Particella";
			public const string Scala = "Scala";
			public const string Sezione = "Sezione";
			public const string Sub = "Sub";
			public const string Cap = "Cap";
			public const string Frazione = "Frazione";
			public const string Circoscrizione = "Circoscrizione";
			public const string Vincoli = "Vincoli";
			public const string Zone = "Zone";
			public const string SottoZone = "SottoZone";
			public const string DatiUrbanistici = "DatiUrbanistici";
			public const string Piani = "Piani";
			public const string Quartieri = "Quartieri";
			public const string TipoCatasto = "TipoCatasto";
			public const string Piano = "Piano";
			public const string Quartiere = "Quartiere";
			public const string CodiceCivico = "CodCivico";
			public const string Coordinate = "Coordinate";
            public const string AccessoTipo = "AccessoTipo";
            public const string AccessoNumero = "AccessoNumero";
            public const string AccessoDescrizione = "AccessoDescrizione";

        }

        private ILog _log = LogManager.GetLogger(typeof(SitIntegrationService));
		private ConcreteSitFactory _sitFactory;

		public SitIntegrationService( DataBase db , string idComune , string idComuneAlias, string software)
		{
			Condition.Requires(db, "db").IsNotNull();
			Condition.Requires(idComune, "idComune").IsNotNullOrEmpty();
            Condition.Requires(idComuneAlias, "idComuneAlias").IsNotNullOrEmpty();
			Condition.Requires(software, "software").IsNotNullOrEmpty();

			this._sitFactory = new ConcreteSitFactory(idComune, idComuneAlias, software, db);
		}

		#region Metodi della classe

		public DetailSit GetDetail(string field, Init.SIGePro.Sit.Data.Sit sit)
		{
			var detailValue = GetDetailInternal(field, sit);

			return new DetailSit
			{
				ReturnValue = detailValue.ReturnValue,
				Message = detailValue.Message,
				MessageCode = detailValue.MessageCode,
				Field = detailValue.DataMap.Select( x => new DetailField{ Campo = x.Key, Valore = x.Value }).ToArray()
			};
		}

		public RetSit GetDetailInternal(string field, Init.SIGePro.Sit.Data.Sit sit)
		{
			_log.DebugFormat("Invocazione di GetDetail con il parametro: field= {0}", field);

			var sitConnector = this._sitFactory.GetSitAttivo();

			sitConnector.DataSit = sit;

			switch (field)
			{
				case NomiCampiSit.Fabbricato:
					return sitConnector.DettaglioFabbricato();
				case NomiCampiSit.UnitaImmobiliare:
					return sitConnector.DettaglioUI();
			}

			throw new ArgumentException("Metodo GetDetail invocato con un parametro field non valido: " + field);
		}

		public ListSit GetList(string field, Init.SIGePro.Sit.Data.Sit sit)
		{
			var listaValori = GetListInternal(field, sit);

			return new ListSit
			{
				ReturnValue = listaValori.ReturnValue,
				Message = listaValori.Message,
				MessageCode = listaValori.MessageCode,
				Field = listaValori.DataCollection.ToList()
			};
		}

		private RetSit GetListInternal(string field, Init.SIGePro.Sit.Data.Sit sit)
		{
			_log.DebugFormat("Invocazione di GetList con il parametro: field= {0}", field);

			var sitConnector = this._sitFactory.GetSitAttivo();

			sitConnector.DataSit = sit;

			switch (field)
			{
				case NomiCampiSit.CodiceVia:
					return sitConnector.ElencoCodVia();
				case NomiCampiSit.Civico:
					return sitConnector.ElencoCivici();
				case NomiCampiSit.Colore:
					return sitConnector.ElencoColori();
				case NomiCampiSit.EsponenteInterno:
					return sitConnector.ElencoEsponentiInterno();
				case NomiCampiSit.Esponente:
					return sitConnector.ElencoEsponenti();
				case NomiCampiSit.Fabbricato:
					return sitConnector.ElencoFabbricati();
				case NomiCampiSit.Foglio:
					return sitConnector.ElencoFogli();
				case NomiCampiSit.Interno:
					return sitConnector.ElencoInterni();
				case NomiCampiSit.Km:
					return sitConnector.ElencoKm();
				case NomiCampiSit.Particella:
					return sitConnector.ElencoParticelle();
				case NomiCampiSit.Scala:
					return sitConnector.ElencoScale();
				case NomiCampiSit.Sezione:
					return sitConnector.ElencoSezioni();
				case NomiCampiSit.Sub:
					return sitConnector.ElencoSub();
				case NomiCampiSit.UnitaImmobiliare:
					return sitConnector.ElencoUI();
				case NomiCampiSit.Cap:
					return sitConnector.ElencoCAP();
				case NomiCampiSit.Frazione:
					return sitConnector.ElencoFrazioni();
				case NomiCampiSit.Circoscrizione:
					return sitConnector.ElencoCircoscrizioni();
				case NomiCampiSit.Vincoli:
					return sitConnector.ElencoVincoli();
				case NomiCampiSit.Zone:
					return sitConnector.ElencoZone();
				case NomiCampiSit.SottoZone:
					return sitConnector.ElencoSottoZone();
				case NomiCampiSit.DatiUrbanistici:
					return sitConnector.ElencoDatiUrbanistici();
				case NomiCampiSit.Piani:
					return sitConnector.ElencoPiani();
				case NomiCampiSit.Quartieri:
					return sitConnector.ElencoQuartieri();
                case NomiCampiSit.AccessoTipo:
                    return sitConnector.ElencoAccessoTipo();
			}

			_log.ErrorFormat("Metodo GetList invocato con un parametro field non valido: {0}", field);

			return new RetSit(true);
		}

		public ValidateSit Validate(string field, Init.SIGePro.Sit.Data.Sit sit)
		{
			var esitoValidazione = this.ValidateInternal(field, sit);

			return new ValidateSit
			{
				ReturnValue = esitoValidazione.ReturnValue,
				Message = esitoValidazione.Message,
				MessageCode = esitoValidazione.MessageCode,
				DataSit = sit
			};
		}

		private RetSit ValidateInternal(string field, Init.SIGePro.Sit.Data.Sit sit)
		{
			_log.DebugFormat("Invocazione di Validate con il parametro: field= {0}", field);

			var sitConnector = this._sitFactory.GetSitAttivo();

			sitConnector.DataSit = sit;

			switch (field)
			{
				case NomiCampiSit.CodiceVia:
					return sitConnector.CodiceViaValidazione();
				case NomiCampiSit.Civico:
					return sitConnector.CivicoValidazione();
				case NomiCampiSit.Colore:
					return sitConnector.ColoreValidazione();
				case NomiCampiSit.EsponenteInterno:
					return sitConnector.EsponenteInternoValidazione();
				case NomiCampiSit.Esponente:
					return sitConnector.EsponenteValidazione();
				case NomiCampiSit.Fabbricato:
					return sitConnector.FabbricatoValidazione();
				case NomiCampiSit.Foglio:
					return sitConnector.FoglioValidazione();
				case NomiCampiSit.Interno:
					return sitConnector.InternoValidazione();
				case NomiCampiSit.Km:
					return sitConnector.KmValidazione();
				case NomiCampiSit.Particella:
					return sitConnector.ParticellaValidazione();
				case NomiCampiSit.Scala:
					return sitConnector.ScalaValidazione();
				case NomiCampiSit.Sezione:
					return sitConnector.SezioneValidazione();
				case NomiCampiSit.Sub:
					return sitConnector.SubValidazione();
				case NomiCampiSit.UnitaImmobiliare:
					return sitConnector.UIValidazione();
				case NomiCampiSit.Cap:
					return sitConnector.CAPValidazione();
				case NomiCampiSit.Frazione:
					return sitConnector.FrazioneValidazione();
				case NomiCampiSit.Circoscrizione:
					return sitConnector.CircoscrizioneValidazione();
				case NomiCampiSit.TipoCatasto:
					return sitConnector.TipoCatastoValidazione();
				case NomiCampiSit.Piano:
					return sitConnector.PianoValidazione();
				case NomiCampiSit.Quartiere:
					return sitConnector.QuartiereValidazione();
                case NomiCampiSit.AccessoTipo:
                    return sitConnector.AccessoTipoValidazione();
                case NomiCampiSit.AccessoNumero:
                    return sitConnector.AccessoNumeroValidazione();
                case NomiCampiSit.AccessoDescrizione:
                    return sitConnector.AccessoDescrizioneValidazione();


            }


            _log.ErrorFormat("Metodo Validate invocato con un parametro field non valido: {0}", field);

			return new RetSit(true);
		}

		public DettagliVia[] GetListaVie(FiltroRicercaListaVie filtro, string[] codiciComuni)
		{
			var sitConnector = this._sitFactory.GetSitAttivo();

			return sitConnector.GetListaVie(filtro, codiciComuni);
		}

		#endregion


		public bool EffettuaValidazioneFormale(Init.SIGePro.Sit.Data.Sit sitClass)
		{
			var sitConnector = _sitFactory.GetSitAttivo();

			return sitConnector.ValidaDatiSit(sitClass);
		}

		public IEnumerable<string> GetListaCampiGestiti()
		{
			return GetFeatures().CampiGestiti;
		}

		public SitFeatures GetFeatures()
		{
			var sitConnector = _sitFactory.GetSitAttivo();

			return new SitFeatures
			{
				CampiGestiti = sitConnector.GetListaCampiGestiti(),
				VisualizzazioniFrontoffice = sitConnector.GetVisualizzazioniFrontoffice(),
				VisualizzazioniBackoffice = sitConnector.GetVisualizzazioniBackoffice(),
			};
		}
	}

}