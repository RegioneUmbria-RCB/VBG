using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Anagrafiche.Request;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Anagrafiche
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
