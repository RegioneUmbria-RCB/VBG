using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess
{
    public interface IFoDyn2DataAccessProvider : IIstanzeDyn2DatiManager, IDyn2ModelliManager, IDyn2ScriptModelloManager,
                                                    IDyn2DettagliModelloManager, IDyn2CampiManager, IDyn2TestoModelloManager, IDyn2ScriptCampiManager,
                                                    IDyn2ProprietaCampiManager
    {
    }
}
