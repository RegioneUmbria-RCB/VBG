using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.ProtocolloServices
{
    public class AnagraficaService : IAnagraficaAmministrazione
    {
        ProtocolloAnagrafe _anag;

        public AnagraficaService(ProtocolloAnagrafe anag)
        {
            _anag = anag;
        }

        public string Codice
        {
            get { return _anag.CODICEANAGRAFE ?? ""; }
        }

        public string Nome
        {
            get { return _anag.TIPOANAGRAFE == "F" ? _anag.NOME : ""; }
        }

        public string Cognome
        {
            get { return _anag.TIPOANAGRAFE == "F" ? _anag.NOMINATIVO : ""; }
        }

        public string CodiceFiscale
        {
            get { return _anag.CODICEFISCALE ?? ""; }
        }

        public string PartitaIva
        {
            get { return _anag.PARTITAIVA ?? ""; }
        }

        public string Denominazione
        {
            get { return _anag.GetNomeCompleto() ?? ""; }
        }

        public string Indirizzo
        {
            get { return _anag.INDIRIZZO ?? ""; }
        }

        public string Email
        {
            get { return _anag.EMAIL ?? ""; }
        }

        public string Pec
        {
            get { return _anag.Pec ?? ""; }
        }


        public string Tipo
        {
            get { return _anag.TIPOANAGRAFE ?? ""; }
        }

        public string Sesso
        {
            get { return _anag.SESSO ?? ""; }
        }

        public string NomeCognome
        {
            get { return String.Format("{0} {1}", _anag.NOME ?? "", _anag.NOMINATIVO ?? "").TrimStart(); }
        }
    }
}
