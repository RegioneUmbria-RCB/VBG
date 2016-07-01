using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CuttingEdge.Conditions;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione
{
	public class TokenApplicazioneService : ITokenApplicazioneService
	{
		TokenApplicazioneRepository _tokenRepository;

		public TokenApplicazioneService(TokenApplicazioneRepository tokenRepository)
		{
			Condition.Requires(tokenRepository, "tokenRepository").IsNotNull();

			this._tokenRepository = tokenRepository;
		}

		#region ITokenApplicazioneService Members

		public string GetToken(string aliasComune)
		{
			var token = _tokenRepository.GetTokenByAliasComune(aliasComune);

			return token;
		}

		#endregion
	}
}
