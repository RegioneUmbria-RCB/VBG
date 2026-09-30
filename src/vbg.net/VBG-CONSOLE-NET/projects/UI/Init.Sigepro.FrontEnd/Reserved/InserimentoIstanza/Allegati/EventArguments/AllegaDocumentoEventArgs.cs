namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Allegati.EventArguments
{
	using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

	public class AllegaDocumentoEventArgs : BaseGrigliaDocumentiEventArgs
	{
		public BinaryFile File { get; private set; }

		public AllegaDocumentoEventArgs(int idAllegato, BinaryFile file)
			: base(idAllegato)
		{
			this.File = file;
		}
	}
}