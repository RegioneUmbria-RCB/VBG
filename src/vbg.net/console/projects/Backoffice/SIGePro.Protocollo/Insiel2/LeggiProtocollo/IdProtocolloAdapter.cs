using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Insiel2.LeggiProtocollo
{
    public class IdProtocolloAdapter
    {
        public class IdProtocollo
        {
            public long ProgDoc { get; private set; }
            public string ProgMovi { get; private set; }

            public IdProtocollo(string progDoc, string progMovi)
            {
                ProgDoc = long.Parse(progDoc);
                ProgMovi = progMovi;
            }
        }

        string _idProtocollo;
        string _separatore;

        public IdProtocolloAdapter(string idProtocollo, string separatore)
        {
            _idProtocollo = idProtocollo;
            _separatore = separatore;
        }

        public IdProtocollo Adatta()
        { 
            if (String.IsNullOrEmpty(_idProtocollo))
                throw new Exception("L'ID DEL PROTOCOLLO NON E' VALORIZZATO, NON E' POSSIBILE LEGGERE IL PROTOCOLLO");

            var arrIdProtocollo = _idProtocollo.Split(_separatore.ToCharArray());
            if (arrIdProtocollo.Length == 1)
                throw new Exception(String.Format("L'ID DEL PROTOCOLLO DEVE CONTENERE IL PROGDOC E IL PROGMOVI SEPARATI DA UN PUNTO E VIRGOLA"));

            return new IdProtocollo(arrIdProtocollo[0], arrIdProtocollo[1]);
        }
    }
}
