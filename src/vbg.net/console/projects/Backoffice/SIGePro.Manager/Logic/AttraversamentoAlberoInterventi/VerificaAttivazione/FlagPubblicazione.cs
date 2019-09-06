using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaPubblicazione
{
	public enum FlagPubblicazione
	{
		EreditaDalPadre = -1,
		NonPubblicare = 0,
		AreaRiservataEFrontoffice = 1,
		AreaRiservata = 2,
		Frontoffice = 3		
	}
}
