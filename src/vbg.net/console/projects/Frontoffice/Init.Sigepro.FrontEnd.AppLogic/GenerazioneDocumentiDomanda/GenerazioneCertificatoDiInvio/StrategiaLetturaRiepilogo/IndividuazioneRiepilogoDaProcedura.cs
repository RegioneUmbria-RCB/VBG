using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.ReadInterface;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.StrategiaLetturaRiepilogo
{
	public class IndividuazioneCertificatoInvioDaProcedura : IStrategiaIndividuazioneCertificatoInvio
	{
		private static class Constants
		{
			public const int NoRiepilogo = -1;
		}

		IReadFacade _readFacade;
		IInterventiRepository _repository;
		int? _codiceOggettoRiepilogo;
		int _codiceIntervento = -1;


		public IndividuazioneCertificatoInvioDaProcedura(IReadFacade readFacade, IInterventiRepository repository)
		{
			Condition.Requires(readFacade, "readFacade").IsNotNull();
			Condition.Requires(repository, "repository").IsNotNull();

			this._readFacade = readFacade;
			this._repository = repository;
		}


		public int CodiceOggetto
		{
			get
			{
				EnsureCodiceOggettoRiepilogo();

				if (this._codiceOggettoRiepilogo.Value == Constants.NoRiepilogo)
					throw new InvalidOperationException("Si sta cercando di leggere il codice oggetto del riepilogo della domanda ma non esiste un riepilogo domanda collegato alla procedura dell'intervento " + this._codiceIntervento);

				return this._codiceOggettoRiepilogo.Value;
			}
		}

		public bool IsCertificatoDefinito
		{
			get 
			{
				EnsureCodiceOggettoRiepilogo();

				return this._codiceOggettoRiepilogo.Value != Constants.NoRiepilogo;
			}
		}

		private void EnsureCodiceOggettoRiepilogo()
		{
			if (_codiceOggettoRiepilogo.HasValue)
				return;

			this._codiceIntervento = _readFacade.Domanda.AltriDati.Intervento.Codice;

			this._codiceOggettoRiepilogo = this._repository.GetCodiceOggettoCertificatoDiInvioDaIdIntervento(this._codiceIntervento);

			if (!this._codiceOggettoRiepilogo.HasValue)
				this._codiceOggettoRiepilogo = Constants.NoRiepilogo;
		}
		
	}
}
