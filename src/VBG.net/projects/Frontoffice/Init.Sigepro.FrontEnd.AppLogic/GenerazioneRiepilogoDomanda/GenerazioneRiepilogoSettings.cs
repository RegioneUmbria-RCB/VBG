using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneRiepilogoDomanda
{
	public class GenerazioneRiepilogoSettings
	{
		public bool AggiungiPdfSchedeAListaAllegati { get; set; }
		public bool DumpXml { get; set; }
		public string IdComune { get; set; }
		public string FormatoConversione { get; set; }
	}
}
