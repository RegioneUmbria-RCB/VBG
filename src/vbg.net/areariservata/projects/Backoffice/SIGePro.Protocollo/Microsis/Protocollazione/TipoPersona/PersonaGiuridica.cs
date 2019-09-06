using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Microsis.Protocollazione.TipoPersona
{
    public class PersonaGiuridica : ITipoPersona
    {
        IAnagraficaAmministrazione _anagrafica;

        public PersonaGiuridica(IAnagraficaAmministrazione anagrafica)
        {
            _anagrafica = anagrafica;
        }

        [TipoPersonaValidationAttribute(IsNullable = true, MaxLength = 50, NomeMetadato = "NOME")]
        public string Nome
        {
            get { return ""; }
        }
        
        [TipoPersonaValidationAttribute(IsNullable = true, MaxLength = 50, NomeMetadato = "COGNOME")]
        public string Cognome
        {
            get { return ""; }
        }

        [TipoPersonaValidationAttribute(IsNullable = true, MaxLength = 16, NomeMetadato = "CODICE FISCALE")]
        public string CodiceFiscale
        {
            get { return _anagrafica.CodiceFiscale; }
        }

        [TipoPersonaValidationAttribute(IsNullable = false, MaxLength = 150, NomeMetadato = "RAGIONE SOCIALE")]
        public string RagioneSociale
        {
            get { return _anagrafica.NomeCognome; }
        }

        [TipoPersonaValidationAttribute(IsNullable = false, MaxLength = 11, NomeMetadato = "PARTITA IVA")]
        public string PartitaIva
        {
            get { return _anagrafica.PartitaIva; }
        }

        [TipoPersonaValidationAttribute(IsNullable = true, MaxLength = 30, NomeMetadato = "PROTOCOLLO")]
        public string Protocollo
        {
            get { return ""; }
        }

        [TipoPersonaValidationAttribute(IsNullable = true, MaxLength = 10, NomeMetadato = "DATA PROTOCOLLO")]
        public string DataProtocollo
        {
            get { return ""; }
        }
    }
}
