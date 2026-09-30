using Init.SIGePro.Data;
using log4net;
using System;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class FoArConfigurazioneMgr
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(FoArConfigurazioneMgr));

        public FoArConfigurazione LeggiDati(string idComune, string software)
        {
            FoArConfigurazione cfgTt = this.GetById(idComune, "TT");
            FoArConfigurazione cfgSw = this.GetById(idComune, software);

            // TODO: migliorare la ligica di sincronizzazione dati con tt

            // Non ho trovato nei dati di configurazione del software ne quelli per TT
            // Devo andare in errore perchè non saprei come rileggere lo stato iniziale dell'istanza
            if (cfgSw == null && cfgTt == null)
            {
                string errMsg = "Dati di configurazione per l'area riservata non trovati per il software " + software + " ne per TT. Verificare che la tabella FO_ARCONFIGURAZIONE contenga dei records";

                this._log.Error($"{idComune} Dati di configurazione per l'area riservata non trovati per il software {software} ne per TT. Verificare che la tabella FO_ARCONFIGURAZIONE contenga dei records");

                throw new InvalidOperationException(errMsg);
            }

            if (cfgSw == null)
                return cfgTt;

            return cfgSw;
        }
    }
}
