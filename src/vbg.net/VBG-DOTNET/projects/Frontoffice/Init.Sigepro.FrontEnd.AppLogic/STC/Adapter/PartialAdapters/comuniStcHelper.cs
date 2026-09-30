using Init.Sigepro.FrontEnd.AppLogic.StcService;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
    internal class ComuniStcHelper
    {
        /// <summary>
        /// Crea un oggetto ComuneType a partire dal codice belfiore del comune
        /// </summary>
        /// <param name="codiceBelfiore"></param>
        /// <returns></returns>
        internal ComuneType? AdattaComuneDaCodiceBelfiore(string codiceBelfiore)
        {
            if (String.IsNullOrEmpty(codiceBelfiore))
                return null;

            return new ComuneType
            {
                Item = codiceBelfiore,
                ItemElementName = ItemChoiceType.codiceCatastale
            };
        }
    }
}
