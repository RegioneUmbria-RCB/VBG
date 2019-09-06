using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Microsis.Protocollazione.TipoPersona
{
    public class TipoPersonaValidationAttribute : Attribute
    {
        public int MaxLength { get; set; }
        public bool IsNullable { get; set; }
        public string NomeMetadato { get; set; }

        public string Valida(string value)
        {
            if (value.Length > MaxLength)
                return String.Format("IL METADATO {0} HA SUPERATO IL LIMITE DI {1} CARATTERI DEFINITI DAL PROTOCOLLO", NomeMetadato, MaxLength);
            else if (String.IsNullOrEmpty(value) && !IsNullable)
                return String.Format("IL METADATO {0} E' OBBLIGATORIO", NomeMetadato);
            else
                return "";
        }
    }
}
