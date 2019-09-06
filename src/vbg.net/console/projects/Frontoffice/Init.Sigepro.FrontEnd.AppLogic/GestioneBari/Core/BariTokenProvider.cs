// -----------------------------------------------------------------------
// <copyright file="BariTokenProvider.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Core
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.Common;
	using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
	using Init.Sigepro.FrontEnd.Bari.Core;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class BariTokenProvider : IToken
	{
		IAliasResolver _aliasResolver;
		ITokenApplicazioneService _tokenApplicazioneService;

		public BariTokenProvider(IAliasResolver aliasResolver, ITokenApplicazioneService tokenApplicazioneService)
		{
			this._aliasResolver = aliasResolver;
			this._tokenApplicazioneService = tokenApplicazioneService;
		}

		public string Get()
		{
			return this._tokenApplicazioneService.GetToken(this._aliasResolver.AliasComune);
		}
	}
}
