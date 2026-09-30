using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Insiel2.Protocollazione;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Insiel2.Services;
using Init.SIGePro.Protocollo.ProtocolloInsielService2;

namespace Init.SIGePro.Protocollo.Insiel2.LeggiProtocollo
{
    public class LeggiProtocolloInputAdapter
    {
        string _utente;
        string _password;
        string _separatore;

        public LeggiProtocolloInputAdapter(string utente, string password, string separatore)
        {
            _utente = utente;
            _password = password;
            _separatore = separatore;
        }

        public DettagliProtocolloRequest Adatta(string id)
        {
            var idProtocollo = GetIdProtocollo(id);

            return new DettagliProtocolloRequest
            {
                Utente = new Utente { codice = _utente, password = _password },
                Registrazione = new ProtocolloRequest
                {
                    Item = new IdProtocollo
                    {
                        ProgDoc = long.Parse(idProtocollo[0]),
                        ProgMovi = idProtocollo[1]
                    }
                }
            };
        }

        public DettagliProtocolloRequest Adatta(string numero, string anno)
        {
            var request = new DettagliProtocolloRequest
            {
                Utente = new Utente { codice = _utente, password = _password },
                Registrazione = new ProtocolloRequest
                {
                    Item = new ProtocolloRequestIdentificatoreProt
                    {
                        Anno = anno,
                        Numero = numero,
                        versoSpecified = false
                    }
                }
            };

            return request;
        }

        private string[] GetIdProtocollo(string id)
        {
            if (String.IsNullOrEmpty(id))
                throw new Exception("L'ID DEL PROTOCOLLO NON E' VALORIZZATO, NON E' POSSIBILE LEGGERE IL PROTOCOLLO");

            var arrIdProtocollo = id.Split(_separatore.ToCharArray());
            if (arrIdProtocollo.Length == 1)
                throw new Exception(String.Format("L'ID DEL PROTOCOLLO DEVE CONTENERE IL PROGDOC E IL PROGMOVI SEPARATI DA UN PUNTO E VIRGOLA"));

            return arrIdProtocollo;
        }

    }
}
