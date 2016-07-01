using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneFilesExcel
{
    public interface IDatiDinamiciExcelService
    {
        /// <summary>
        /// Analizza tutti gli allegati della domanda e cerca di estrarre dai files excel i valori con cui popolare le schede dinamiche
        /// </summary>
        /// <param name="idDomanda">Id della domanda di cui analizzare i files excel</param>
        void EstraiDatiDinamiciDaFilesExcel(int idDomanda);
    }
}
