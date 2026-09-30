// -----------------------------------------------------------------------
// <copyright file="GeneratoreRiepilogoModelloDinamico.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli
{
    public class GeneratoreRiepilogoModelloDinamico : IGeneratoreRiepilogoModelloDinamico
    {
        //IAllegatiDomandaFoRepository _allegatiDomandaFoRepository;
        private readonly RiepilogoModelloInHtmlFactory _riepilogoModelloInHtmlFactory;
        private readonly IDatiDinamiciRiepilogoReader _reader;
        private readonly IHtmlToPdfFileConverter _fileConverter;

        public GeneratoreRiepilogoModelloDinamico(IDatiDinamiciRiepilogoReader reader/*, IAllegatiDomandaFoRepository allegatiDomandaFoRepository*/,
                                                    RiepilogoModelloInHtmlFactory riepilogoModelloInHtmlFactory, IHtmlToPdfFileConverter fileConverter)
        {
            this._reader = reader;
            //this._allegatiDomandaFoRepository = allegatiDomandaFoRepository;
            this._riepilogoModelloInHtmlFactory = riepilogoModelloInHtmlFactory;
            this._fileConverter = fileConverter;
        }

        public BinaryFile GeneraRiepilogo(int idModello, string nomeRiepilogo, int indiceMolteplicita)
        {
            var nomeFile = new NomeFileRiepilogoModello(nomeRiepilogo.Trim(), indiceMolteplicita).ToString();
            var riepilogo = this._riepilogoModelloInHtmlFactory.FromDomanda(this._reader, idModello, indiceMolteplicita);

            return riepilogo.ConvertiInPdf(this._fileConverter, nomeFile);
        }


        #region IGeneratoreRiepilogoSchedaDinamica Members
        /*
		public RisultatoGenerazioneRiepilogoDomanda GeneraRiepilogoEAllega( int idModello, string nomeRiepilogo, int indiceMolteplicita)
		{
			var idDomanda = this._reader.DataKey.IdPresentazione;
			var nomeFile = new NomeFileRiepilogoModello(nomeRiepilogo.Trim(), indiceMolteplicita).ToString();
			var riepilogo = this._riepilogoModelloInHtmlFactory.FromDomanda(this._reader, idModello, indiceMolteplicita);

			var binaryFile = riepilogo.ConvertiInPdf(this._fileConverter, nomeFile);

			var esitoSalvataggio = _allegatiDomandaFoRepository.SalvaAllegato(idDomanda, binaryFile, false);

			var md5 = new Hasher().ComputeHash(binaryFile.FileContent);

			return new RisultatoGenerazioneRiepilogoDomanda(esitoSalvataggio.CodiceOggetto, binaryFile, md5);
		}
        */
        #endregion
    }
}
