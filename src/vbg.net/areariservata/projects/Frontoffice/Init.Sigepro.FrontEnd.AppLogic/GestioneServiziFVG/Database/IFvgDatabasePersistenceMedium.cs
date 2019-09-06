using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.Database
{
    public interface IFvgDatabasePersistenceMedium
    {
        long CodiceIstanza { get; }

        FvgDatabase.Flyweight Load();
        void Save(FvgDatabase.Flyweight statoInterno);
    }
}
