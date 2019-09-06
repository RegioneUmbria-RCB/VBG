using Init.SIGePro.Protocollo.Insiel3.Services;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Insiel3.Protocollazione.MittentiDestinatari.GestioneAnagrafiche
{
    public interface IGestioneAnagrafiche
    {
        void Gestisci(IAnagraficaAmministrazione anagrafica, ProtocolloService srv);
        string Nominativo { get; }
    }
}
