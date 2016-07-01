using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAllegati;
using System.Data;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti
{
	public class Endoprocedimento
	{
		public class NaturaEndoprocedimento
		{
			public int Codice { get; private set; }
			public string Descrizione { get; private set; }

			public NaturaEndoprocedimento(int codice, string descrizione)
			{
				this.Codice = codice;
				this.Descrizione = descrizione;
			}
		}

		public class TipoTitolo
		{ 
			public int Codice { get; private set; }
			public string Descrizione { get; private set; }

			public TipoTitolo(int codice, string descrizione)
			{
				this.Codice = codice;
				this.Descrizione = descrizione;
			}			
		}

		public class RiferimentiEndoprocedimentoAcquisito
		{
			public string NumeroAtto { get; private set; }
			public DateTime? DataAtto { get; private set; }
			public TipoTitolo TipoTitolo { get; private set; }
			public string RilasciatoDa { get; private set; }
			public string Note { get; private set; }
			public AllegatoDellaDomanda Allegato { get; private set; }

			public RiferimentiEndoprocedimentoAcquisito(string numeroAtto, DateTime? dataAtto, TipoTitolo tipotitolo, string rilasciatoDa, string note, AllegatoDellaDomanda allegato)
			{
				this.NumeroAtto = numeroAtto;
				this.DataAtto = dataAtto;
				this.TipoTitolo = tipotitolo;
				this.RilasciatoDa = rilasciatoDa;
				this.Note = note;
				this.Allegato = allegato;
			}

		}

		public int Codice { get; private set; }
		public string Descrizione { get; private set; }
		public bool Principale { get; private set; }
		public NaturaEndoprocedimento Natura { get; private set; }
		public int BinarioDipendenze { get; private set; }
		public bool PermetteVerificaAcquisizione { get; private set; }
		public bool Facoltativo { get; private set; }
		public bool Presente{ get; private set; }
		public RiferimentiEndoprocedimentoAcquisito Riferimenti { get; private set; }


		protected Endoprocedimento(int codice, string descrizione, bool principale , 
								NaturaEndoprocedimento natura, int binarioDipendenze, bool permetteVerificaAcquisizione, 
								bool facoltativo, bool presente,RiferimentiEndoprocedimentoAcquisito riferimenti )
		{
			this.Codice = codice;
			this.Descrizione = descrizione;
			this.Principale = principale;
			this.Natura = natura;
			this.BinarioDipendenze = binarioDipendenze;
			this.PermetteVerificaAcquisizione = permetteVerificaAcquisizione;
			this.Facoltativo = facoltativo;
			this.Presente = presente;
			this.Riferimenti = riferimenti;
		}

		public static Endoprocedimento FromIstanzeProcedimentiRow(PresentazioneIstanzaDbV2.ISTANZEPROCEDIMENTIRow endo)
		{
			var codice = endo.CODICEINVENTARIO;
			var descrizione = endo.DESCRIZIONE;
			var principale = endo.Principale;
			var binarioDipendenze = endo.IsBinarioDipendenzeNull() ? 0 : endo.BinarioDipendenze;
			var permetteVerificaAcquisizione = endo.IsPermetteVerificaAcquisizioneNull() ? false : endo.PermetteVerificaAcquisizione;
			var facoltativo = endo.IsEndoFacoltativoNull() ? true : endo.EndoFacoltativo;
			var presente = endo.IsPresenteNull() || !endo.Presente ? false : true;
			Endoprocedimento.RiferimentiEndoprocedimentoAcquisito riferimenti = null;
			Endoprocedimento.NaturaEndoprocedimento natura = null;


			if (!endo.IsCodiceNaturaNull())
				natura = new Endoprocedimento.NaturaEndoprocedimento(endo.CodiceNatura, endo.DescrizioneNatura);

			if (presente)
			{
				var tipoTitolo = new Endoprocedimento.TipoTitolo(endo.TipoTitolo, endo.DescrizioneTipoTitolo);
				var allegato = endo.IsIdAllegatoNull() ? (AllegatoDellaDomanda)null : new AllegatoDellaDomanda(((PresentazioneIstanzaDbV2.AllegatiDataTable)endo.Table.DataSet.Tables["Allegati"]).FindById(endo.IdAllegato));
				riferimenti = new Endoprocedimento.RiferimentiEndoprocedimentoAcquisito(endo.NumeroAtto, endo.IsDataAttoNull() ? (DateTime?)null : endo.DataAtto, tipoTitolo, endo.RilasciatoDa, endo.Note, allegato);
			}

			return new Endoprocedimento(codice, descrizione, principale, natura, binarioDipendenze, permetteVerificaAcquisizione, facoltativo, presente, riferimenti);
		}
	}
}
