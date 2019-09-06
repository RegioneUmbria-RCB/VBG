using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Allegati.EventArguments
{
    public class AllegaDocumentiMultipliEventArgs : BaseGrigliaDocumentiEventArgs
	{
        public AllegaDocumentiMultipliEventArgs(int idAllegato)
			: base(idAllegato)
		{
		}
    }
}