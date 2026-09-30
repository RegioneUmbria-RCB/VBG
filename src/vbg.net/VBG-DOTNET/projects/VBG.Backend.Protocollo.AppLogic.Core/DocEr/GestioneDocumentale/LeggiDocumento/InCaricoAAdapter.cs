using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Core.DocEr.ProtocollazioneRegistrazione;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale.LeggiDocumento
{
    public class InCaricoAAdapter
    {
        public class InCaricoADati
        {
            public string Codice { get; private set; }
            public string Descrizione { get; private set; }

            public InCaricoADati(string codice, string descrizione)
            {
                Codice = codice;
                Descrizione = descrizione;
            }
        }

        MittDestType _mittenteDestinatario;

        public InCaricoAAdapter(MittDestType mittenteDestinatario)
        {
            _mittenteDestinatario = mittenteDestinatario;
        }

        public InCaricoADati Adatta()
        {
            if (_mittenteDestinatario == null ||_mittenteDestinatario.Items == null || _mittenteDestinatario.Items.Length == 0)
                return new InCaricoADati("", "");

            var amministrazione = (AmministrazioneType)_mittenteDestinatario.Items[0];

            if (amministrazione.Items == null || amministrazione.Items.Length == 0)
                return new InCaricoADati("", "");

            var uo = (UnitaOrganizzativaType)amministrazione.Items[0];

            string codice = uo.Identificativo.Text[0];
            string denominazione = "";

            if (uo.Denominazione != null)
                denominazione = uo.Denominazione.Text[0];

            return new InCaricoADati(codice, denominazione);
        }
    }
}
