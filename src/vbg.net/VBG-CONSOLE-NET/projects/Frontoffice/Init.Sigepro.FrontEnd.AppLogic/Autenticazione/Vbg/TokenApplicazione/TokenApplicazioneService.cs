using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione
{
	public class TokenApplicazioneService : ITokenApplicazioneService
	{
		TokenApplicazioneRepository _tokenRepository;
        IAliasResolver _aliasResovler;

		public TokenApplicazioneService(TokenApplicazioneRepository tokenRepository, IAliasResolver aliasResovler)
		{
			Condition.Requires(tokenRepository, "tokenRepository").IsNotNull();

			this._tokenRepository = tokenRepository;
            this._aliasResovler = aliasResovler;
		}

		#region ITokenApplicazioneService Members

		public string GetToken(string aliasComune)
		{
			var token = _tokenRepository.GetTokenByAliasComune(aliasComune);

			return token;
		}

        public string GetToken()
        {
            return GetToken(this._aliasResovler.AliasComune);
        }

        #endregion
    }
}
