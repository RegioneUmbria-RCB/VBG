using Init.SIGePro.Protocollo.ProtocolloItCityService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ItCity
{
    public class LoginWsInfo
    {
        public string Username { get; private set; }
        public string Password { get; private set; }
        public IdentificativoUtente Identificativo { get; private set; }

        public LoginWsInfo(string username, string password, string operatore)
        {
            this.Username = username;
            this.Password = password;

            if (String.IsNullOrEmpty(operatore))
            {
                throw new Exception("PARAMETRO OPERATORE NON VALORIZZATO NELLA REGOLA PROTOCOLLO_ATTIVO");
            }

            var datiOperatore = operatore.Split(',');

            if (datiOperatore.Length < 2)
            {
                throw new Exception("I DATI RIGUARDANTI L'OPERATORE NON SONO COMPLETI, DOVREBBE ESSERE RAPPRESENTATO SIA L'ID UTENTE CHE L'UNITA' OPERATIVA");
            }

            int idUtente;
            bool isParsableIdUtente = Int32.TryParse(datiOperatore[0], out idUtente);

            if (!isParsableIdUtente)
            {
                throw new Exception("L'ID UTENTE DELL'OPERATORE NON E' UN NUMERO");
            }

            int idUnitaOperativa;
            bool isParsableIdUnitaOperativa = Int32.TryParse(datiOperatore[1], out idUnitaOperativa);

            if (!isParsableIdUtente)
            {
                throw new Exception("L'ID DELL'UNITA' OPERATIVA DELL'OPERATORE NON E' UN NUMERO");
            }


            this.Identificativo = new IdentificativoUtente
            {
                IdUtente =  idUtente,
                IdUnitaOperativa = idUnitaOperativa
            };
        }
    }
}
