// -----------------------------------------------------------------------
// <copyright file="CittadinanzeService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneComuni
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using CuttingEdge.Conditions;
	using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
	using Init.Sigepro.FrontEnd.AppLogic.Common;

	public interface ICittadinanzeService
	{
		bool IsCittadinanzaExtracomunitaria(int codiceCittadinanza);
		Cittadinanza GetCittadinanzaDaId(int codiceCittadinanza);
		IEnumerable<Cittadinanza> GetListaCittadinanze();
	}

	public class CittadinanzeService : ICittadinanzeService
	{
		IComuniRepository _comuniRepository;
		IAliasResolver _aliasResolver;

		public CittadinanzeService(IAliasResolver aliasResolver, IComuniRepository comuniRepository)
		{
			Condition.Requires(comuniRepository, "comuniRepository").IsNotNull();
			Condition.Requires(aliasResolver, "aliasResolver").IsNotNull();

			this._comuniRepository = comuniRepository;
			this._aliasResolver = aliasResolver;
		}

		#region ICittadinanzeService Members

		public bool IsCittadinanzaExtracomunitaria(int idCittadinanza)
		{
			return this._comuniRepository.IsCittadinanzaExtracomunitaria(this._aliasResolver.AliasComune, idCittadinanza);
		}


		public Cittadinanza GetCittadinanzaDaId(int codiceCittadinanza)
		{
			return this._comuniRepository.GetCittadinanzaDaId(this._aliasResolver.AliasComune, codiceCittadinanza);
		}

		#endregion

		#region ICittadinanzeService Members


		public IEnumerable<Cittadinanza> GetListaCittadinanze()
		{
			return this._comuniRepository.GetListaCittadinanze(this._aliasResolver.AliasComune);
		}

		#endregion
	}
}
