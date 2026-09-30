using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI
{
    public class RiferimentiUtenteNodoPagamenti
    {
        public string Nome => this._soggettoDebitore.nome;
        public string Cognome => this._soggettoDebitore.cognome;
        public string Cfpi => this._soggettoDebitore.cfpi;
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

        internal SoggettoDebitoreType ToSoggettoDebitoreType()
        {
            return this._soggettoDebitore;
        }
    }
}
