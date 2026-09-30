using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione
{
    public class TokenApplicazioneService : ITokenApplicazioneService
    {
        private readonly TokenApplicazioneRepository _tokenRepository;
        private readonly IAliasResolver _aliasResovler;

        public TokenApplicazioneService(TokenApplicazioneRepository tokenRepository, IAliasResolver aliasResovler)
        {
            if (tokenRepository == null)
                throw new System.ArgumentNullException(nameof(tokenRepository));

            this._tokenRepository = tokenRepository;
            this._aliasResovler = aliasResovler;
        }

        #region ITokenApplicazioneService Members

        public string GetToken(string aliasComune)
        {
            var token = this._tokenRepository.GetTokenByAliasComune(aliasComune);

            return token;
        }

        public string GetToken()
        {
            return this.GetToken(this._aliasResovler.AliasComune);
        }

        #endregion
    }
}
