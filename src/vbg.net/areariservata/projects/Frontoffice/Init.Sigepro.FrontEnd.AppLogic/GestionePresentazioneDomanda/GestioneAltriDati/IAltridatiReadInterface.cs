using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAltriDati
{
	public interface IAltriDatiReadInterface
	{
		DatiIntervento	Intervento { get; }
		string			AliasComune { get; }
		string			DescrizioneLavori { get; }
		string			DenominazioneAttivita { get; }
		string			Note { get; }
		string			DomicilioElettronico { get; }
		int				IdPresentazione { get; }
		string			IdentificativoDomanda { get; }
		string			CodiceComune { get; }
		string			Software { get; }
		IEnumerable<Evento> Eventi { get; }
		bool			FlagPrivacy { get;  }
		int?			AttivitaAtecoPrimaria { get; }
        string          NaturaBase { get; }
        int?            IdDomandaCollegata { get; }

    }
}
