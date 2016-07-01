using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Folium.Protocollazione.MittentiDestinatari
{
    public interface IMittentiDestinatari
    {
        string Nome { get; }
        string Cognome { get; }
        string Denominazione { get; }
        string CodiceMezzoSpedizione { get; }
        string EMail { get; }
        string Citta { get; }
        string Tipo { get; }
        string Indirizzo { get; }

        bool IsPerConoscenza(string codicePerConoscenza);
    }
}
