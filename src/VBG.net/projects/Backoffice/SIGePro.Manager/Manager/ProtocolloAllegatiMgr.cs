using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using PersonalLib2.Sql.Collections;

namespace Init.SIGePro.Manager
{
	/// <summary>
	/// Descrizione di riepilogo per ProtocolloAllegatiMgr.
	/// </summary>
	public class ProtocolloAllegatiMgr : OggettiMgr
	{
		public ProtocolloAllegatiMgr(DataBase dataBase) : base( dataBase )
		{}

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
