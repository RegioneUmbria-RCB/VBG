// -----------------------------------------------------------------------
// <copyright file="TipiSoggettoService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Common;

	public interface ITipiSoggettoService
	{
		TipoSoggetto GetById(int codiceTipoSoggetto);
		IEnumerable<TipoSoggetto> GetTipiSoggettoObbligatori(int? codiceIntervento);
		IEnumerable<TipoSoggetto> GetTipiSoggettoPersonaGiurudica(int? codiceIntervento);
		IEnumerable<TipoSoggetto> GetTipiSoggettoPersonaFisica(int? codiceIntervento);
	}

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class TipiSoggettoService : ITipiSoggettoService
	{
		ITipiSoggettoRepository _tipiSoggettoRepository;

		public TipiSoggettoService( ITipiSoggettoRepository tipiSoggettoRepository)
		{
			this._tipiSoggettoRepository = tipiSoggettoRepository;
		}

		#region ITipiSoggettoService Members

		public IEnumerable<TipoSoggetto> GetTipiSoggettoObbligatori(int? codiceIntervento)
		{
			return this._tipiSoggettoRepository
					   .GetObbligatori(codiceIntervento)
					   .Select( x => new TipoSoggetto( x ));
		}

		public TipoSoggetto GetById(int codiceTipoSoggetto)
		{
			var ts = this._tipiSoggettoRepository.GetById(codiceTipoSoggetto);

			if (ts == null)
				return null;

			return new TipoSoggetto(ts);
		}

		public IEnumerable<TipoSoggetto> GetTipiSoggettoPersonaGiurudica(int? codiceIntervento)
		{
			return this._tipiSoggettoRepository
					   .GetTipiSoggettoPersonaGiurudica(codiceIntervento)
					   .Select(x => new TipoSoggetto(x));
					
		}

		public IEnumerable<TipoSoggetto> GetTipiSoggettoPersonaFisica(int? codiceIntervento)
		{
			return this._tipiSoggettoRepository
		   .GetTipiSoggettoPersonaFisica(codiceIntervento)
		   .Select(x => new TipoSoggetto(x));
		}

		#endregion
	}
}
