// -----------------------------------------------------------------------
// <copyright file="AllegatoDomandaBandi.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using System.Xml.Serialization;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class AllegatoDomandaBandi
	{
		static Dictionary<CategoriaAllegatoBandoEnum, string> _nomiCategorieAllegati = new Dictionary<CategoriaAllegatoBandoEnum, string>{
			{CategoriaAllegatoBandoEnum.DocumentoLegaleRappresentante, "Documento legale rappresentante"},
			{CategoriaAllegatoBandoEnum.Procura, "Procura"},
			{CategoriaAllegatoBandoEnum.ProvvedimentoAbilitativo, "Provvedimento abilitativo"},
			{CategoriaAllegatoBandoEnum.TitoloDiProprieta, "Titolo di proprieta"},
			{CategoriaAllegatoBandoEnum.ComputoMetrico, "Computo metrico"},
			{CategoriaAllegatoBandoEnum.Allegato7, "Allegato 7"},
			{CategoriaAllegatoBandoEnum.Allegato1, "Allegato 1"},
			{CategoriaAllegatoBandoEnum.Allegato2, "Allegato 2"},
			{CategoriaAllegatoBandoEnum.Allegato3, "Allegato 3"},
			{CategoriaAllegatoBandoEnum.Allegato4, "Allegato 4"},
			{CategoriaAllegatoBandoEnum.Allegato10, "Allegato 10"},
			{CategoriaAllegatoBandoEnum.AllegatoAltreSediOperative, "Ulteriori sedi operative interessate dall&#39;intervento (non obbligatorio)"},
            {CategoriaAllegatoBandoEnum.IncomingAllegato1, "Allegato 1 - Domanda"},
            {CategoriaAllegatoBandoEnum.IncomingAllegato2, "Allegato 2 - Scheda tecnica"},
            {CategoriaAllegatoBandoEnum.IncomingAllegato3, "Allegato 3 - Dichiarazioni"}
		};

		static Dictionary<CategoriaAllegatoBandoEnum, string> _nomiTagAllegati = new Dictionary<CategoriaAllegatoBandoEnum, string>{
			{CategoriaAllegatoBandoEnum.Allegato7, "allegato7"},
			{CategoriaAllegatoBandoEnum.Allegato1, "allegato1"},
			{CategoriaAllegatoBandoEnum.Allegato2, "allegato2"},
			{CategoriaAllegatoBandoEnum.Allegato3, "allegato3"},
			{CategoriaAllegatoBandoEnum.Allegato4, "allegato4"},
			{CategoriaAllegatoBandoEnum.Allegato10,"allegato10"},
			{CategoriaAllegatoBandoEnum.AllegatoAltreSediOperative, ""},
            {CategoriaAllegatoBandoEnum.IncomingAllegato1, "allegato1"},
			{CategoriaAllegatoBandoEnum.IncomingAllegato2, "allegato2"},
			{CategoriaAllegatoBandoEnum.IncomingAllegato3, "allegato7"}
		};

		public string Id { get; set; }
		public CategoriaAllegatoBandoEnum Categoria { get; set; }
		public string Descrizione { get; set; }
		public int? IdModello { get; set; }
		public int? IdAllegato { get; set; }
		public int? IdAllegatoFirmatoDigitalmente { get; set; }
		public string NomeFile { get; set; }
		public string NomeFileFirmatoDigitalemnte { get; set; }

		public AllegatoDomandaBandi()
		{
		}

		public AllegatoDomandaBandi(CategoriaAllegatoBandoEnum categoria, string nomeAzienda, int? idModello = null)
		{
			this.Id = Guid.NewGuid().ToString();
			this.Categoria = categoria;

			if (String.IsNullOrEmpty(nomeAzienda))
			{
				this.Descrizione = _nomiCategorieAllegati[categoria];
			}
			else
			{
                this.Descrizione = String.Format("{1}: {0}", _nomiCategorieAllegati[categoria], nomeAzienda);
			}

			this.IdAllegatoFirmatoDigitalmente = (int?)null;

			if (idModello != null)
				this.IdModello = idModello;
		}

		public AllegatoDomandaBandi(CategoriaAllegatoBandoEnum categoria, int idModello)
			: this(categoria, String.Empty, idModello)
		{
		}

		public void SetRiferimentiFile(int codiceOggetto, string nomeFile)
		{
			this.IdAllegato = codiceOggetto;
			this.NomeFile = nomeFile;
		}

		internal void SetRiferimentiFileFirmatoDigitalemente(int codiceOggetto, string nomeFile)
		{
			this.IdAllegatoFirmatoDigitalmente = codiceOggetto;
			this.NomeFileFirmatoDigitalemnte = nomeFile;
		}

		internal void EliminaRiferimentiFile()
		{
			this.IdAllegato = (int?)null;
			this.NomeFile = String.Empty;
		}

		[XmlIgnore]
		public string TagModello
		{
			get
			{
				if (_nomiTagAllegati.ContainsKey(this.Categoria))
				{
					return _nomiTagAllegati[this.Categoria];
				}

				return String.Empty;
			}
		}



		internal void EliminaRiferimentiFileFirmatoDigitalemnte()
		{
			this.IdAllegatoFirmatoDigitalmente = (int?)null;
			this.NomeFileFirmatoDigitalemnte = String.Empty;
		}
	}
}
