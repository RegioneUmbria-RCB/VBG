using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.DTO.StradarioComune;
using PersonalLib2.Data;
using Init.SIGePro.Data;

namespace Init.SIGePro.Manager.Logic.GestioneStradario
{


	public interface IQueryRicercaStradario
	{
		IEnumerable<Stradario> FilterRecords(RicercaStradarioWrapper mgr);
	}

	public class CompositeQueryRicercaStradario : IQueryRicercaStradario
	{
		IEnumerable<IQueryRicercaStradario> _queries;

		internal CompositeQueryRicercaStradario(IEnumerable<IQueryRicercaStradario> queries)
		{
			this._queries = queries;
		}

		#region IQueryRicercaStradario Members

		public IEnumerable<Stradario> FilterRecords(RicercaStradarioWrapper mgr)
		{
			foreach (var query in this._queries)
			{
				var result = query.FilterRecords(mgr);

				if (result.Count() != 0)
					return result;
			}

			return new List<Stradario>();
		}

		#endregion
	}

	public class QueryRicercaStradario : IQueryRicercaStradario
	{
		/// <summary>
		/// parole riservate che vanno escluse dalla ricerca per match parziale
		/// </summary>
		private static string[] PAROLE_RISERVATE = {
			"STR",
			"PIAZZA",
			"PZZA",
			"PONTE",
			"CORSO",
			"CALA",
			"STRADA",
			"PIAZZALE",
			"PARALLELA",
			"PIAZZETTA",
			"SALITA",
			"CHIASSO",
			"ARCO",
			"LARGO",
			"STRADETTA",
			"TRAVERSA",
			"RACCORDO",
			"TRATTO",
			"VIA",
			"CORTE",
			"STRADELLA",
			"LUNGOMARE",
			"PIAZZA",
			"PROLUNGAMENTO",
			"VIALE",
			"VICO",
			"CONTRADA",
			"SOTTOVIA",
			"MOLO"
		};

		string _indirizzoParziale;
		string _separatore;

		internal QueryRicercaStradario(string separatore, string indirizzoParziale)
		{
			this._indirizzoParziale = indirizzoParziale;
			this._separatore		= " " + separatore + " ";
		}

		private IEnumerable<string> SeparaStringaRicerca()
		{
			var partiIndirizzo = this._indirizzoParziale.Trim().Replace(".", ". ").Split(' ');

			return partiIndirizzo.Where(x => !PAROLE_RISERVATE.Contains(x.ToUpperInvariant()));
		}


		#region IQueryRicercaStradario Members

		public IEnumerable<Stradario> FilterRecords(RicercaStradarioWrapper mgr)
		{
			var condizioniRicerca = new StradarioMgr.CondizioniRicercaStradarioPerDescrizione( this._separatore, SeparaStringaRicerca() );

			return mgr.Trovaindirizzi( condizioniRicerca );
		}

		#endregion
	}

	public class RicercaStradarioWrapper 
	{
		string _idComune;
		string _codiceComune;
		StradarioMgr _mgr;
		string _comuneLocalizzazione;

		internal RicercaStradarioWrapper(string idComune, string codiceComune, string comuneLocalizzazione, StradarioMgr mgr)
		{
			this._idComune = idComune;
			this._codiceComune = codiceComune;
			this._comuneLocalizzazione = comuneLocalizzazione;
			this._mgr = mgr;
		}

		internal IEnumerable<Stradario> Trovaindirizzi(StradarioMgr.CondizioniRicercaStradarioPerDescrizione condizioniRicerca)
		{
			return this._mgr.GetByMatchParziale(this._idComune, this._codiceComune, this._comuneLocalizzazione, condizioniRicerca);
		}
	}

	public class RicercaStradarioStrategy
	{
		private RicercaStradarioWrapper _ricercaWrapper;

		public RicercaStradarioStrategy(DataBase db, string idComune, string codiceComune, string comuneLocalizzazione)
		{
			this._ricercaWrapper = new RicercaStradarioWrapper(idComune, codiceComune, comuneLocalizzazione, new StradarioMgr(db));
		}

		public IEnumerable<StradarioDto> FindByPartialMatch(IQueryRicercaStradario specification)
		{
			return specification.FilterRecords( this._ricercaWrapper)
								.Select( x => new StradarioDto(x));
		}
	}
}
