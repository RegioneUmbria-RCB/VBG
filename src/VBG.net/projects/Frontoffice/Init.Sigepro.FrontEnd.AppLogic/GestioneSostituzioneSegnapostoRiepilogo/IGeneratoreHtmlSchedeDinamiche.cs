using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.SIGePro.DatiDinamici.WebControls.MaschereCampiNonVisibili;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo
{
	public enum GenerazioneHtmlSchedeOptions
	{
		SoloSchedeCheNonNecessitanoFirma,
		TutteLeSchede
	}

	public interface IGeneratoreHtmlSchedeDinamiche
	{
		string GeneraHtml(DomandaOnline domanda, int idScheda);
		string GeneraHtml(DomandaOnline domanda, int idScheda, int indiceMolteplicita);
		string GeneraHtmlDelleSchedeDellaDomanda(DomandaOnline domanda, GenerazioneHtmlSchedeOptions options);
		string GeneraHtmlScheda(ModelloDinamicoBase scheda, ICampiNonVisibili campinonVisibili = null);
	}
}
