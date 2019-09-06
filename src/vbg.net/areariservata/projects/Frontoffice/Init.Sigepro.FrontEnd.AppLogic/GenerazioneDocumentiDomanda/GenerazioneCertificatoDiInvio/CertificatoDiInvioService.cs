// -----------------------------------------------------------------------
// <copyright file="CertificatoDiInvioService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio
{
    using CuttingEdge.Conditions;
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneCertificatoDiInvio.StrategiaLetturaRiepilogo;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
    using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
    using log4net;
    using System;

	public class CertificatoDiInvioService
	{
		ILog _log = LogManager.GetLogger(typeof(CertificatoDiInvioService));

		IStrategiaIndividuazioneCertificatoInvio _strategiaIndividuazioneRiepilogo;
		GeneratoreCertificatoDiInvio _generatoreCertificatoDiInvio;
		IAliasSoftwareResolver _aliasSoftwareResolver;
		ISalvataggioDomandaStrategy _salvataggioStrategy;
		CertificatoDiInvioAllegato _certificatoDiInvioAllegato;

		public CertificatoDiInvioService(IAliasSoftwareResolver aliasSoftwareResolver, ISalvataggioDomandaStrategy salvataggioStrategy, GeneratoreCertificatoDiInvio generatoreCertificatoDiInvio, IStrategiaIndividuazioneCertificatoInvio strategiaIndividuazioneRiepilogo, CertificatoDiInvioAllegato certificatoDiInvioAllegato)
		{
			Condition.Requires(aliasSoftwareResolver, "aliasSoftwareResolver").IsNotNull();
			Condition.Requires(salvataggioStrategy, "salvataggioStrategy").IsNotNull();
			Condition.Requires(strategiaIndividuazioneRiepilogo, "strategiaIndividuazioneRiepilogo").IsNotNull();
			Condition.Requires(generatoreCertificatoDiInvio, "generatoreCertificatoDiInvio").IsNotNull();
			Condition.Requires(certificatoDiInvioAllegato, "certificatoDiInvioAllegato").IsNotNull();


			this._aliasSoftwareResolver = aliasSoftwareResolver;
			this._salvataggioStrategy = salvataggioStrategy;
			this._strategiaIndividuazioneRiepilogo = strategiaIndividuazioneRiepilogo;
			this._generatoreCertificatoDiInvio = generatoreCertificatoDiInvio;
			this._certificatoDiInvioAllegato = certificatoDiInvioAllegato;
		}


		public BinaryFile GeneraCertificatoDiInvio(DomandaOnline domanda, string idDomandaBackoffice)
		{
			try
			{
				var fileCertificato = this._generatoreCertificatoDiInvio.GeneraCertificatoDiInvio(domanda, idDomandaBackoffice, this._strategiaIndividuazioneRiepilogo);

                this._certificatoDiInvioAllegato.AllegaSeNonEsiste(idDomandaBackoffice, fileCertificato);

                return fileCertificato;
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Generazione del certificato di invio fallita per la domanda {0} (id domanda backoffice {1}): {2}", domanda.DataKey.ToSerializationCode(), idDomandaBackoffice, ex.ToString());

				throw;
			}
			finally
			{
				_log.Debug("Fine generazione del certificato di invio");
			}
		}


		public BinaryFile GetCertificatoDaIdDomandaBackoffice(string idDomandaBackoffice)
		{
			try
			{
				_log.DebugFormat("Inizio lettura del certificato di invio allegato alla domanda backoffice con id {0}", idDomandaBackoffice);

				return this._certificatoDiInvioAllegato.GetByIdDomanda(idDomandaBackoffice);
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante la lettura del certificato di invio allegato alla domanda backoffice con id {0}: {1}", idDomandaBackoffice, ex.ToString());

				throw;
			}
			finally
			{
				_log.Debug("Fine della lettura del certificato di invio");
			}
		}

		public int? GetCodiceOggettoCertificatoDiInvioDaIdDomandaBackoffice(string idDomandaBackoffice)
		{
			try
			{
				_log.DebugFormat("Inizio lettura del certificato di invio allegato alla domanda backoffice con id {0}", idDomandaBackoffice);

				return this._certificatoDiInvioAllegato.GetIdFileCertificato(idDomandaBackoffice);
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore durante la lettura del certificato di invio allegato alla domanda backoffice con id {0}: {1}", idDomandaBackoffice, ex.ToString());

				throw;
			}
			finally
			{
				_log.Debug("Fine della lettura del certificato di invio");
			}
		}
	}
}
