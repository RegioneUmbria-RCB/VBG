// -----------------------------------------------------------------------
// <copyright file="GeneratoreRiepilogoModelloDinamico.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo;
	using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;

	public class GeneratoreRiepilogoModelloDinamico : IGeneratoreRiepilogoModelloDinamico
	{
		IAllegatiDomandaFoRepository _allegatiDomandaFoRepository;
		RiepilogoModelloInHtmlFactory _riepilogoModelloInHtmlFactory;
		DomandaOnline _domanda;
		FileConverterService _fileConverter;

		public GeneratoreRiepilogoModelloDinamico(DomandaOnline domanda, IAllegatiDomandaFoRepository allegatiDomandaFoRepository,  RiepilogoModelloInHtmlFactory riepilogoModelloInHtmlFactory, FileConverterService fileConverter)
		{
			this._domanda = domanda;
			this._allegatiDomandaFoRepository = allegatiDomandaFoRepository;
			this._riepilogoModelloInHtmlFactory = riepilogoModelloInHtmlFactory;
			this._fileConverter = fileConverter;
		}

		#region IGeneratoreRiepilogoSchedaDinamica Members

		public RisultatoGenerazioneRiepilogoDomanda GeneraRiepilogoEAllega( int idModello, string nomeRiepilogo, int indiceMolteplicita)
		{
			var idDomanda = this._domanda.DataKey.IdPresentazione;
			var nomeFile = new NomeFileRiepilogoModello(nomeRiepilogo.Trim(), indiceMolteplicita).ToString();
			var riepilogo = this._riepilogoModelloInHtmlFactory.FromDomanda(this._domanda, idModello, indiceMolteplicita);

			var binaryFile = riepilogo.ConvertiInPdf(this._fileConverter, nomeFile);

			var esitoSalvataggio = _allegatiDomandaFoRepository.SalvaAllegato(idDomanda, binaryFile, false);

			var md5 = new Hasher().ComputeHash(binaryFile.FileContent);

			return new RisultatoGenerazioneRiepilogoDomanda(esitoSalvataggio.CodiceOggetto, binaryFile, md5);
		}

		#endregion
	}
}
