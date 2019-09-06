using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls.CreazioneControlli
{
	public interface IDatiDinamiciControlsFactory
	{
		IDatiDinamiciControl CreaControllo( CampoDinamicoBase campo );
	}
}
