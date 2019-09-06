using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.JIride.Fascicolazione
{
    public class FascicolazioneInfo
    {
        public string Anno { get; private set; }
        public string Data { get; private set; }
        public string Numero { get; private set; }
        public string Oggetto { get; private set; }
        public string Classifica { get; private set; }
        public string Utente { get; private set; }
        public string Ruolo { get; private set; }
        public ParametriRegoleInfo ParametriRegola { get; private set; }

        public FascicolazioneInfo(string anno, string data, string numero, string oggetto, string classifica, string utente, string ruolo, ParametriRegoleInfo parametriRegole)
        {
            this.Anno = anno;

            DateTime dtFasc;
            var isValidDate = DateTime.TryParse(data, out dtFasc);

            if (!isValidDate && String.IsNullOrEmpty(parametriRegole.FormatoDataFasc))
            {
                this.Data = data;
            }

            this.Data = dtFasc.ToString(parametriRegole.FormatoDataFasc);
            this.Numero = numero;
            this.Oggetto = oggetto;
            this.Classifica = classifica;
            this.Utente = utente;
            this.Ruolo = ruolo;
        }
    }
}
