using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GenerazioneRiepiloghiSchedeDinamiche
{
	public interface IGenerazioneRiepilogoSchedeDinamicheService
	{
		BinaryFile GeneraRiepilogoScheda(DatiMovimentoDaEffettuare movimento, int idScheda);
	}
}
