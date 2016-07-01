using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ProtocolloInterfaces
{
    public interface IAnagraficaAmministrazione
    {
        string Codice { get; }
        string Nome { get; }
        string Cognome { get; }
        string CodiceFiscale { get; }
        string PartitaIva { get; }
        string Denominazione { get; }
        string Indirizzo { get; }
        string Email { get; }
        string Pec { get; }
        string Tipo { get; }
        string Sesso { get; }
        string NomeCognome { get; }
    }
}
