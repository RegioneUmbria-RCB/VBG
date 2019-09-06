namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti
{
	using System;
	using System.Collections.Generic;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti.Sincronizzazione;
	using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;

	public class EndoprocedimentiWriteInterface : IEndoprocedimentiWriteInterface
	{
		private PresentazioneIstanzaDbV2 _database;

		public EndoprocedimentiWriteInterface(PresentazioneIstanzaDbV2 database)
		{
			this._database = database;
		}

		#region IEndoprocedimentiWriteInterface Members

		public void Elimina(int idEndo)
		{
			var row = this._database.ISTANZEPROCEDIMENTI.FindByCODICEINVENTARIO(idEndo);

			// TODO: eliminare
			//	- modelli dinamici dell'endo
			//	- Documenti dell'endo
			//	- Oneri dell'endo
			row.Delete();
		}

		public void AggiungiOAggiorna(int codice, string descrizione, bool principale, int? codiceNatura, string descrizionenatura, bool facoltativo, int binariodipendenze, bool permetteVerificaAcquisizione, bool tipoTitoloObbligatorio)
		{
			var nuovo = false;
			var row = this._database.ISTANZEPROCEDIMENTI.FindByCODICEINVENTARIO(codice);

			if (row == null)
			{
				row = this._database.ISTANZEPROCEDIMENTI.NewISTANZEPROCEDIMENTIRow();

				nuovo = true;

				row.CODICEINVENTARIO = codice;
			}

			if (row.IsPresenteNull())
			{
				row.Presente = false;
			}

			row.DESCRIZIONE = descrizione;
			row.Principale = principale;
			row.DescrizioneNatura = descrizionenatura;
			row.EndoFacoltativo = facoltativo;
			row.BinarioDipendenze = binariodipendenze;
			row.PermetteVerificaAcquisizione = permetteVerificaAcquisizione;
            row.TipoTitoloObbligatorio = tipoTitoloObbligatorio;

			if (codiceNatura.HasValue)
			{
				row.CodiceNatura = codiceNatura.Value;
			}
			else
			{
				row.SetCodiceNaturaNull();
			}

			if (nuovo)
			{
				this._database.ISTANZEPROCEDIMENTI.AddISTANZEPROCEDIMENTIRow(row);
			}
		}

		public void AggiungiESincronizza(IEnumerable<int> idNuoviEndoprocedimenti, LogicaSincronizzazioneEndo logicaSincronizzazioneEndo)
		{
			logicaSincronizzazioneEndo.Sincronizza(idNuoviEndoprocedimenti);
		}

		public void ImpostaNonPresente(int codiceEndo)
		{
			var row = this._database.ISTANZEPROCEDIMENTI.FindByCODICEINVENTARIO(codiceEndo);

			if (row == null)
			{
				return;
			}

			row.Presente = false;
			row.SetTipoTitoloNull();
			row.SetDescrizioneTipoTitoloNull();
			row.SetNumeroAttoNull();
			row.SetDataAttoNull();
			row.SetNoteNull();
			row.SetIdAllegatoNull();
		}

		public void ImpostaPresente(int codiceEndo, int? idTipoTitolo, string descrizioneTipoTitolo, string numeroAtto, DateTime? dataAtto, string rilasciatoDa, string note)
		{
			var row = this._database.ISTANZEPROCEDIMENTI.FindByCODICEINVENTARIO(codiceEndo);

			if (row == null)
			{
				return;
			}

			row.Presente = true;

			if (idTipoTitolo.HasValue)
			{
				row.TipoTitolo = idTipoTitolo.Value;
			}
			else
			{
				row.SetTipoTitoloNull();
			}

			row.RilasciatoDa = rilasciatoDa;
			row.DescrizioneTipoTitolo = descrizioneTipoTitolo;
			row.NumeroAtto = numeroAtto;

			if (dataAtto.HasValue)
			{
				row.DataAtto = dataAtto.Value;
			}
			else
			{
				row.SetDataAttoNull();
			}

			row.Note = note;
		}

		#endregion

		public void AllegaAdEndoPresente(int codiceInventario, int codiceOggetto)
		{
			var row = this._database.ISTANZEPROCEDIMENTI.FindByCODICEINVENTARIO(codiceInventario);

			if (row == null)
			{
				return;
			}

			row.IdAllegato = codiceOggetto;
		}

		public void RimuoviAllegatoDaEndoPresente(int codiceInventario)
		{
			var row = this._database.ISTANZEPROCEDIMENTI.FindByCODICEINVENTARIO(codiceInventario);

			row.SetIdAllegatoNull();
		}
	}
}
