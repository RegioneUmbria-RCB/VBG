using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces;

namespace Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService
{
	// Questa classe è utilizzata solamente per definire IClasseContestoModelloDinamico come
	// classe base di Istanze, altrimenti darebbe errore quando si va ad assegnare la classe 
	// nel Dyn2IstanzeManager
	public partial class Istanze : IClasseContestoModelloDinamico
	{
	}
}
