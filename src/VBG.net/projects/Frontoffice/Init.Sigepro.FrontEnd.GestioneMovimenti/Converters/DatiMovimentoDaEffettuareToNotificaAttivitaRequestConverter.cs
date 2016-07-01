// -----------------------------------------------------------------------
// <copyright file="DatiMovimentoDaEffettuareConverter.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Converters
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using AutoMapper;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface;
	using Init.Sigepro.FrontEnd.AppLogic.StcService;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiMovimentoDaEffettuareToNotificaAttivitaRequestConverter : ITypeConverter<DatiMovimentoDaEffettuare, NotificaAttivitaRequest>
	{
		private static class Constants
		{
			public const string NomeElementoSchedeDinamiche = "SCHEDE_MOVIMENTO";
			public const string PrefissoElementiDatiDinamici = "FO_DYN2DATO_";
		}

		#region ITypeConverter<DatiMovimentoDaEffettuare,NotificaAttivitaRequest> Members

		public NotificaAttivitaRequest Convert(ResolutionContext context)
		{
			var datiMovimento = (DatiMovimentoDaEffettuare)context.SourceValue;

			return new NotificaAttivitaRequest
			{
				rifPraticaDestinatario	= new RiferimentiPraticaType
				{
					idPratica = datiMovimento.MovimentoDiOrigine.DatiIstanza.CodiceIstanza.ToString()	// codiceistanza 
				},
				datiAttivita = new DettaglioAttivitaType
				{
					idPratica				= datiMovimento.MovimentoDiOrigine.DatiIstanza.CodiceIstanza.ToString(),	// codice istanza 
					idAttivita				= datiMovimento.Id.ToString(),		// id del movimento da effettuare
					dataAttivita			= DateTime.Now,						// Data in cui viene effettuatoil movimento
					dataAttivitaSpecified	= true,
					tipoAttivita = new TipoAttivitaType
					{
						codice		= datiMovimento.IdTipoAttivita.ToString(),			//	tipomovimento
						descrizione = datiMovimento.NomeAttivita			//	descrizione
					},
					esito = true,	// sempre true
					note = datiMovimento.Note,
					procedimenti = GetProcedimenti(datiMovimento),
					documenti = datiMovimento.Allegati.Select(x =>
						new DocumentiType
						{
							id = x.IdAllegato.ToString(),
							tipoDocumento = TipoDocumentoType.Altro,
							tipoDocumentoSpecified = true,
							documento = x.Descrizione,
							allegati = new AllegatiType
							{
								allegato = x.Note,
								file = null,
								id = x.IdAllegato.ToString()
							}
						})
						.Union( datiMovimento.GetRiepiloghiSchedeDinamiche()
												.Where( x => x.CodiceOggetto.HasValue )
												.Select( x => new DocumentiType
 												{	
													id = x.CodiceOggetto.Value.ToString(),
													tipoDocumento = TipoDocumentoType.Altro,
													tipoDocumentoSpecified = true,
													documento = "Riepilogo della scheda \"" + x.NomeScheda + "\"",
													allegati = new AllegatiType{
														allegato = x.NomeFile,
														file = null,
														id = x.CodiceOggetto.Value.ToString()
													}
												}))							
						.ToArray(),
					altriDati = GetSchedeDelMovimento( datiMovimento )
										.Union( GetValoriDatiDinamiciDelMovimento( datiMovimento ))
										.ToArray()
				}
			};
		}

		private ProcedimentoType[] GetProcedimenti(DatiMovimentoDaEffettuare datiMovimento)
		{
			if (!datiMovimento.MovimentoDiOrigine.HaProcedimento)
				return null;

			return new ProcedimentoType[]{
				new ProcedimentoType
				{
					codice = datiMovimento.MovimentoDiOrigine.CodiceProcedimento,
					descrizione = datiMovimento.MovimentoDiOrigine.Procedimento,
					principale = true,
					principaleSpecified = true
				}
			};
		}

		#endregion

		private ParametroType[] GetSchedeDelMovimento(DatiMovimentoDaEffettuare datiMovimento)
		{
			return new ParametroType[]{
				new ParametroType{
					nome = Constants.NomeElementoSchedeDinamiche,
					valore = datiMovimento.RiepiloghiSchedeDinamiche.Select(x => new ValoreParametroType 
					{ 
						codice = x.IdScheda.ToString(),
						descrizione = x.IdScheda.ToString()
					}).ToArray()
				}
			};
		}

		private ParametroType[] GetValoriDatiDinamiciDelMovimento(DatiMovimentoDaEffettuare datiMovimento)
		{
			return datiMovimento.ValoriSchedeDinamiche
								.GroupBy(x => x.Id)
								.Select(grp => new ParametroType
								{
									nome = String.Concat(Constants.PrefissoElementiDatiDinamici , grp.Key.ToString()),
									valore = grp.Select( x => new ValoreParametroType{
										codice = x.Valore,
										descrizione = x.ValoreDecodificato
									}).ToArray()
								})
								.ToArray();
		}
	}
}
