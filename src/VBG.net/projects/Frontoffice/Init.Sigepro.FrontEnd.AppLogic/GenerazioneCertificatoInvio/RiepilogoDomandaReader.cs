using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneCertificatoInvio.StrategiaLetturaRiepilogo;
using Init.Sigepro.FrontEnd.Infrastructure.FileEncoding;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneCertificatoInvio
{
	public class RiepilogoDomandaReader
	{
		IAliasSoftwareResolver _aliasSoftwareResolver;
		IOggettiService _oggettiService;

		public RiepilogoDomandaReader(IAliasSoftwareResolver aliasSoftwareResolver, IOggettiService oggettiService)
		{
			Condition.Requires(aliasSoftwareResolver, "aliasSoftwareResolver").IsNotNull();
			Condition.Requires(oggettiService, "oggettiService").IsNotNull();

			this._aliasSoftwareResolver = aliasSoftwareResolver;
			this._oggettiService = oggettiService;
		}

		public string Read( IStrategiaIndividuazioneCertificatoInvio strategiaLetturaRiepilogo )
		{
			var codiceOggetto = strategiaLetturaRiepilogo.CodiceOggetto;

			var oggetto = _oggettiService.GetById(codiceOggetto);

			if (oggetto == null)
				throw new InvalidOperationException("Non è stato possibile leggere l'oggetto con id " + codiceOggetto);

			return UnknownEncodingToString.Convert(oggetto.FileContent);
		}
	}
}
