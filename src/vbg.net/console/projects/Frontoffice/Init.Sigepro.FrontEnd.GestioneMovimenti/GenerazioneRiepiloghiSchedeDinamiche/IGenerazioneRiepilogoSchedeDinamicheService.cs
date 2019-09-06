using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.GenerazioneRiepiloghiSchedeDinamiche
{
	public interface IGenerazioneRiepilogoSchedeDinamicheService
	{
		BinaryFile GeneraRiepilogoScheda(MovimentoDaEffettuare movimento, int idScheda);
	}
}
