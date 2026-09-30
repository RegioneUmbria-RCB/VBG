using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Managers
{
    /// <summary>
	/// Descrizione di riepilogo per ProtocolloAllegatiMgr.
	/// </summary>
	public class ProtocolloAllegatiMgr : OggettiMgr
    {
        public ProtocolloAllegatiMgr(DataBase dataBase) : base(dataBase)
        { }

        public void SetProtocolloAllegati(Oggetti pOggetto, ProtocolloAllegati pProtAll)
        {
            pProtAll.CODICEOGGETTO = pOggetto.CODICEOGGETTO;
            pProtAll.IDCOMUNE = pOggetto.IDCOMUNE;
            pProtAll.NOMEFILE = pOggetto.NOMEFILE;
            pProtAll.OGGETTO = pOggetto.OGGETTO;
            //pProtAll.PATH = pOggetto.PATH;
        }
    }
}
