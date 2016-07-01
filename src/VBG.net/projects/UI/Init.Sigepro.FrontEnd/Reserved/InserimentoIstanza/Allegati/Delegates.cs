namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Allegati
{
	using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Allegati.EventArguments;

	public delegate void OnAllegaDocumentoDelegate(object sender, AllegaDocumentoEventArgs args);

	public delegate void OnRimuoviDocumentoDelegate(object sender, RimuoviDocumentoEventArgs args);

	public delegate void OnCompilaDocumentoDelegate(object sender, CompilaDocumentoEventArgs args);

	public delegate void OnFirmaDocumentoDelegate(object sender, FirmaDocumentoEventArgs args);
}