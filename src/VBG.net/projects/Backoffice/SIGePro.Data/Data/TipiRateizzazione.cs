using System;
using System.Collections.Generic;
using System.Text;

namespace Init.SIGePro.Data
{
    public partial class TipiRateizzazione
    {
        /// <summary>
        /// The collection of ADO.NET data providers that are supported by <see cref="DataProviderFactory"/>.
        /// </summary>
        public enum TipiScadenze
        {
            /// <summary>
            /// Dopo aver calcolato la scadenza della rata, la imposta alla fine del mese.
            /// </summary>
            FineMese = 0,
            /// <summary>
            /// Dopo aver calcolato la scadenza della rata, la imposta al prossimo 15 del mese ( corrente o successivo in base al giorno di scadenza ).
            /// </summary>
            MetaMeseSuccessivo,
            /// <summary>
            /// Dopo aver calcolato la scadenza della rata, la imposta alla fine del mese. Non modifica la scadenza della prima rata.
            /// </summary>
            FineMeseEsclusaPrimaRata,
            /// <summary>
            /// Dopo aver calcolato la scadenza della rata, la imposta al prossimo 15 del mese ( corrente o successivo in base al giorno di scadenza ). Non modifica la scadenza della prima rata.
            /// </summary>
            MetaMeseSuccessivoEsclusaPrimaRata,
            /// <summary>
            /// Dopo aver calcolato la scadenza della rata, la lascia inalterata
            /// </summary>
            LasciaInalterataLaScadenza
        } ;
    }
}
