using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaPubblicazione;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi
{
	public interface IIntervento
	{
		int Id { get; }
		FlagPubblicazione Pubblica { get; }
		DateTime? DataInizioAttivazione { get;  }
		DateTime? DataFineAttivazione { get;  }
        int? LivelloAutenticazione { get; }
	}
}
