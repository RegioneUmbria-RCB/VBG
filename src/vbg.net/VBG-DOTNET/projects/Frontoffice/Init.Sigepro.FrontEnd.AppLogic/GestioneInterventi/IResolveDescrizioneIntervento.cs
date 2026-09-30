using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi
{
    public interface IResolveDescrizioneIntervento
    {
        string GetDescrizioneDaCodiceintervento(int codiceIntervento);
    }
}
