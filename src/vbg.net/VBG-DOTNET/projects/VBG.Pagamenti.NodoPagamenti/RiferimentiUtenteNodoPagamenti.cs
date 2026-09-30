namespace VBG.Pagamenti.NodoPagamenti
{
    public class RiferimentiUtenteNodoPagamenti
    {
        public string Nome => this._soggettoDebitore.nome;
        public string Cognome => this._soggettoDebitore.cognome;
        public string Cfpi => this._soggettoDebitore.cfpi;
        public string Comune => this._soggettoDebitore.localita;
        public string Indirizzo => this._soggettoDebitore.via;
        public string Email => this._soggettoDebitore.email;
        public string CAP => this._soggettoDebitore.cap;
        /*
        public readonly string Comune;
        public readonly string Via;
        public readonly string Provincia;
        public readonly string Cap;
        public readonly string Email;
        */

        private readonly SoggettoDebitoreType _soggettoDebitore;

        public RiferimentiUtenteNodoPagamenti(string nome, string cognome, string cfpi, string comune, string via, string provincia, string cap, string email)
        {
            this._soggettoDebitore = new SoggettoDebitoreType
            {
                nome = nome,
                cognome = cognome,
                cfpi = cfpi,
                cap = cap,
                via = via,
                localita = comune,
                provincia = provincia,
                email = email
            };
        }

        public SoggettoDebitoreType ToSoggettoDebitoreType()
        {
            return this._soggettoDebitore;
        }
    }
}
