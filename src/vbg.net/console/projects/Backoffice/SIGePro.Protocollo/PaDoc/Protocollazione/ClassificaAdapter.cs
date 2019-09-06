using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.PaDoc.Protocollazione
{
    public class ClassificaAdapter
    {
        public class Classifica
        {
            public string Titolo { get; private set; }
            public string Classe { get; private set; }

            public Classifica(string titolo, string classe)
            {
                Titolo = titolo;
                Classe = classe;
            }
        }

        private string _classifica;

        public ClassificaAdapter(string classifica)
        {
            _classifica = classifica;
        }

        public Classifica Adatta()
        {
            string titolo = "";
            string classe = "";

            if (String.IsNullOrEmpty(_classifica))
                return null;

            var arrClassifica = _classifica.Split('.');
            titolo = arrClassifica[0];

            if (arrClassifica.Length > 1)
                classe = arrClassifica[1];

            return new Classifica(titolo, classe);
        }
    }
}
