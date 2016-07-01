using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.ProtocolloServices
{
    public class AmministrazioneService : IAnagraficaAmministrazione
    {
        Amministrazioni _amm;

        public AmministrazioneService(Amministrazioni amm)
        {
            _amm = amm;
        }

        public string Codice
        {
            get { return _amm.CODICEAMMINISTRAZIONE ?? ""; }
        }

        public string Nome
        {
            get { return ""; }
        }

        public string Cognome
        {
            get { return ""; }
        }

        public string CodiceFiscale
        {
            get { return ""; }
        }

        public string PartitaIva
        {
            get { return _amm.PARTITAIVA ?? ""; }
        }

        public string Denominazione
        {
            get { return _amm.AMMINISTRAZIONE ?? ""; }
        }

        public string Indirizzo
        {
            get { return _amm.INDIRIZZO ?? ""; }
        }

        public string Email
        {
            get { return _amm.EMAIL ?? ""; }
        }

        public string Pec
        {
            get { return _amm.PEC ?? ""; }
        }


        public string Tipo
        {
            get { return "G"; }
        }

        public string Sesso
        {
            get { return ""; }
        }

        public string NomeCognome
        {
            get { return _amm.AMMINISTRAZIONE ?? ""; }
        }
    }
}
