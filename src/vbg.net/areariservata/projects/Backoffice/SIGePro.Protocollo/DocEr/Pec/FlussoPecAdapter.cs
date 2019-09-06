using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.DocEr.Pec
{
    public class FlussoPecAdapter
    {
        /// <summary>
        /// Da verificare se i valori di queste costanti siano da specificare su delle verticalizzazioni
        /// </summary>
        private static class Constants
        {
            public const string ModalitaInvioStandard = "S";
            public const string ModalitaInvioDocEr = "D";
            public const string ConInoltroAllegati = "C";
            public const string SenzaInoltroAllegati = "S";
            public const string ForzaInvioAllegatiNonFirmati = "1";
            public const string NonForzareInvioAllegatiNonFirmati = "0";
        }

        public static FlussoType Adatta()
        {
            return new FlussoType
            {
                ModalitaInvio = Constants.ModalitaInvioStandard,
                TipoRichiesta = Constants.ConInoltroAllegati,
                ForzaInvio = 1
            };
        }
    }
}
