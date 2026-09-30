namespace Init.SIGePro.Manager.Logic.GestioneIstanze.Documenti
{
    public class DocumentiIstanzaRestUrl
    {
        private readonly string _baseUrl;

        public DocumentiIstanzaRestUrl(string baseUrl)
        {
            _baseUrl = baseUrl;
        }

        public string GetUploadUrl(int codiceIstanza)
        {
            var baseUrl = this._baseUrl;

            if (!baseUrl.EndsWith("/"))
            {
                baseUrl += "/";
            }

            return $"{baseUrl}services/rest-auth-token/pratiche/documenti/{codiceIstanza}";
        }
    }
}
