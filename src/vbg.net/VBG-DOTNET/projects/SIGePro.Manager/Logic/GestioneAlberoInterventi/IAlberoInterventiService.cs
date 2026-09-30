using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneAlberoInterventi
{
    public interface IAlberoInterventiService
    {
        AlberoProc GetById(int idIntervento);
    }
}
