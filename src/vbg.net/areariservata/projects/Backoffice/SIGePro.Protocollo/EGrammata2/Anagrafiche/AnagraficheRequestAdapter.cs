using Init.SIGePro.Protocollo.EGrammata2.Anagrafiche.Request;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.EGrammata2.Anagrafiche
{
    public class AnagraficheRequestAdapter
    {
        public static RicercaAnagrafica Adatta(IAnagraficaAmministrazione anagrafica)
        {
            var retVal = new RicercaAnagrafica { Item = new DatiAnag { Codice = new Codice { ItemElementName = ItemChoiceType.CodiceFiscale, Item = anagrafica.CodiceFiscale } } };

            if (!String.IsNullOrEmpty(anagrafica.PartitaIva))
                retVal = new RicercaAnagrafica { Item = new DatiAnag { Codice = new Codice { ItemElementName = ItemChoiceType.PartitaIva, Item = anagrafica.PartitaIva } } };

            return retVal;
        }
    }
}
