using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI.Configurazione
{
    public interface IVerificaConfigurazioneNodoPagamentiService
    {
        bool ConfigurazioneValida(int idDomanda);
    }
}
