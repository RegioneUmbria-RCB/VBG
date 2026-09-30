using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using PersonalLib2.Data;

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


        public Comuni ComuneResidenza
        {
            get { return _amm.ComuneResidenza; }
        }

        public string Localita
        {
            get { return _amm.CITTA; }
        }

        public string CodiceFiscalePartitaIva
        {
            get { return _amm.PARTITAIVA; }
        }

        public string ModalitaTrasmissione
        {
            get { return _amm.ModalitaTrasmissione; }
        }

        public string MezzoInvio
        {
            get { return _amm.Mezzo; }
        }


        public string Provincia
        {
            get { return _amm.PROVINCIA; }
        }


        public string Cap
        {
            get { return _amm.CAP; }
        }


        public string Comune
        {
            get { return _amm.CITTA; }
        }


        public string CodiceIstatResidenza
        {
            get { return _amm.ComuneResidenza != null ? _amm.ComuneResidenza.CODICEISTAT : ""; }
        }


        public string Telefono
        {
            get { return _amm.TELEFONO1; }
        }

        public string Fax
        {
            get { return _amm.FAX; }
        }

        public string Uo 
        { 
            get 
            { 
                return _amm.PROT_UO; 
            } 
        }

        public string Ruolo
        {
            get 
            {
                return _amm.PROT_RUOLO;
            }
        }

        public string TipologiaAnagrafica
        {
            get
            {
                return "AMMINISTRAZIONE";
            }
        }
    }
}
