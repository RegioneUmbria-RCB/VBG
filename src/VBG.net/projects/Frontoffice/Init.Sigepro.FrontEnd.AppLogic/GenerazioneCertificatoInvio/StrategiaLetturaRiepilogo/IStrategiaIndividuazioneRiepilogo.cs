using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneCertificatoInvio.StrategiaLetturaRiepilogo
{
	public interface IStrategiaIndividuazioneCertificatoInvio
	{
		bool IsCertificatoDefinito { get; }
		int CodiceOggetto{get;}
	}
}
