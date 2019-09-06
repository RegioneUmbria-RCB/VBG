// -----------------------------------------------------------------------
// <copyright file="BariStradarioService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Core
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.Core;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
	using Init.Sigepro.FrontEnd.AppLogic.Common;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class BariStradarioService : IResolveViaDaCodviario
    {
        IStradarioRepository _stradarioRepository;
        IAliasResolver _aliasResolver;

		public BariStradarioService(IStradarioRepository stradarioRepository, IAliasResolver aliasResolver)
        {
            this._stradarioRepository = stradarioRepository;
            this._aliasResolver = aliasResolver;
        }

        public string GetNomeByCodViario(string codViario)
        {
            var stradario = this._stradarioRepository.GetByCodViario(this._aliasResolver.AliasComune, codViario);

            return stradario == null ? String.Format("La via con codice {0} non è stata trovata negli stradari comunali", codViario) : stradario.NomeVia;
        }
    }
}
