// -----------------------------------------------------------------------
// <copyright file="IMovimentiStorage.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface.Persistence
{
	using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
	using System;

	/// <summary>
	/// Repository per accedere i dati relativi alla read interface della gestione movimenti
	/// </summary>
	public interface IMovimentiReadRepository
	{
		/// <summary>
		/// Ottiene i dati di un movimento da efettuare
		/// </summary>
		/// <param name="id">Id del movimento frontoffice</param>
		/// <returns>Dati del movimento corrispondente all'id specificato o null se l'id non è stato trovato</returns>
		DatiMovimentoDaEffettuare GetById(int id);

		/// <summary>
		/// Salva i dati di un movimento da effettuare nel frontoffice
		/// </summary>
		/// <param name="movimentoDaEffettuare">Dati del movimento da effettuare</param>
		void Save(DatiMovimentoDaEffettuare movimentoDaEffettuare);
	}



	public class MovimentiReadRepository : IMovimentiReadRepository
	{
		IGestioneMovimentiDataContext _dataContext;
		IMovimentiBackofficeService _movimentiBackofficeService;
		IScadenzeService _scadenzeService;

		public MovimentiReadRepository(IScadenzeService scadenzeService,IGestioneMovimentiDataContext dataContext, IMovimentiBackofficeService movimentiBackofficeService)
		{
			this._scadenzeService = scadenzeService;
			this._dataContext = dataContext;
			this._movimentiBackofficeService = movimentiBackofficeService;
		}

		#region IMovimentiReadRepository Members

		public DatiMovimentoDaEffettuare GetById(int id)
		{
			var mov = this._dataContext.GetDataStore().MovimentoDaEffettuare;

			if (mov == null || mov.Id != id)
				return null;

			var scadenza = this._scadenzeService.GetById(mov.Id);

			mov.ImpostaMovimentoDiOrigine( this._movimentiBackofficeService.GetById(Convert.ToInt32( scadenza.CodMovimento )) );

			return mov;
		}

		public void Save(DatiMovimentoDaEffettuare movimentoDaEffettuare)
		{
			this._dataContext.GetDataStore().MovimentoDaEffettuare = movimentoDaEffettuare;
		}

		#endregion

	}
}
