using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Microsis.Protocollazione.TipoPersona
{
    public class PersonaFisica : ITipoPersona
    {
        IAnagraficaAmministrazione _anagrafica;

        public PersonaFisica(IAnagraficaAmministrazione anagrafica)
        {
            _anagrafica = anagrafica;
        }

        [TipoPersonaValidationAttribute(IsNullable=false, MaxLength=50, NomeMetadato="NOME")]
        public string Nome
        {
            get { return _anagrafica.Nome; }
        }

        [TipoPersonaValidationAttribute(IsNullable = false, MaxLength = 50, NomeMetadato = "COGNOME")]
        public string Cognome
        {
            get { return _anagrafica.Cognome; }
        }

        [TipoPersonaValidationAttribute(IsNullable = false, MaxLength = 16, NomeMetadato = "CODICE FISCALE")]
        public string CodiceFiscale
        {
            get { return _anagrafica.CodiceFiscale; }
        }

        [TipoPersonaValidationAttribute(IsNullable = true, MaxLength = 150, NomeMetadato = "RAGIONE SOCIALE")]
        public string RagioneSociale
        {
            get { return ""; }
        }

        [TipoPersonaValidationAttribute(IsNullable = true, MaxLength = 11, NomeMetadato = "PARTITA IVA")]
        public string PartitaIva
        {
            get { return ""; }
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
