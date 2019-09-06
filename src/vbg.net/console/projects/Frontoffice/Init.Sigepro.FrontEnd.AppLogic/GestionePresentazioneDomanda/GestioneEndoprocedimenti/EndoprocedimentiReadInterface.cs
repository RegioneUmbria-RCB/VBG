namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti
{
	using System.Collections.Generic;
	using System.Linq;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita;
	using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;

	public class EndoprocedimentiReadInterface : IEndoprocedimentiReadInterface
	{
		PresentazioneIstanzaDbV2 _database;
		List<Endoprocedimento> _endoprocedimenti = new List<Endoprocedimento>();

		public EndoprocedimentiReadInterface(PresentazioneIstanzaDbV2 database)
		{
			this._database = database;

			this.PreparaEndo();
		}

		private void PreparaEndo()
		{
			this._endoprocedimenti = this._database.ISTANZEPROCEDIMENTI
													.Cast<PresentazioneIstanzaDbV2.ISTANZEPROCEDIMENTIRow>()
													.Select(x => Endoprocedimento.FromIstanzeProcedimentiRow(x))
													.ToList();
		}

		#region IEndoprocedimentiReadInterface Members

		public IEnumerable<Endoprocedimento> NonAcquisiti
		{
			get { return this._endoprocedimenti.Where(x => x.Riferimenti == null); }
		}

		public IEnumerable<Endoprocedimento> Acquisiti
		{
			get { return this._endoprocedimenti.Where(x => x.Riferimenti != null); }
		}

		public IEnumerable<Endoprocedimento> Endoprocedimenti
		{
			get { return this._endoprocedimenti; }
		}

		public IEnumerable<Endoprocedimento> SecondariNonPresenti
		{
			get { return this._endoprocedimenti.Where(x => !x.Principale && x.Riferimenti == null); }
		}

		public IEnumerable<Endoprocedimento> Secondari
		{
			get { return this._endoprocedimenti.Where(x => !x.Principale); }
		}

		public Endoprocedimento Principale
		{
			get { return this._endoprocedimenti.Where(x => x.Principale).FirstOrDefault(); }
		}

		public IEnumerable<EndoprocedimentoIncompatibile> GetEndoprocedimentiIncompatibili(IEndoprocedimentiIncompatibiliService endoprocedimentiIncompatibiliService)
		{
			var codiciEndo = this._endoprocedimenti.Select(x => x.Codice);

			var incompatibili = endoprocedimentiIncompatibiliService.GetEndoprocedimentiIncompatibili(codiciEndo);

			return incompatibili.Select(i =>
			{
				var nomeEndo = this.Endoprocedimenti.Where(e => e.Codice == i.Endo).Select(e => e.Descrizione).First();
				var nomiEndoIncompatibili = this.Endoprocedimenti.Where(e => i.EndoIncompatibili.Contains(e.Codice)).Select(e => e.Descrizione);

				return new EndoprocedimentoIncompatibile(nomeEndo, nomiEndoIncompatibili);
			});
		}

		#endregion
	}
}
