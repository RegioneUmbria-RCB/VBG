namespace SIGePro.Manager.VerticalizzazioniBase
{
    public class VerticalizzazioniFactory : IVerticalizzazioniFactory
    {
        private readonly RegoleServiceClient _regoleServiceClient;

        public VerticalizzazioniFactory(RegoleServiceClient regoleServiceClient)
        {
            this._regoleServiceClient = regoleServiceClient;
        }

        public T Create<T>(string alias, string software = "TT", string codiceComune = "") where T : Verticalizzazione, new()
        {
            var instance = new T();

            instance.Alias = alias;
            instance.Software = software;
            instance.CodiceComune = codiceComune;
            instance.RegoleServiceClient = this._regoleServiceClient;

            instance.Initialize();

            return instance;
        }

        public VerticalizzazioneGenerica CreaVerticalizzazioneGenerica(string nomeVerticalizzazione, string alias, string software = "TT", string codiceComune = "")
        {
            var verticalizzazione = new VerticalizzazioneGenerica(alias, nomeVerticalizzazione)
            {
                Alias = alias,
                Software = software,
                CodiceComune = codiceComune,
                RegoleServiceClient = this._regoleServiceClient
            };
            verticalizzazione.Initialize();
            return verticalizzazione;
        }
    }
}
