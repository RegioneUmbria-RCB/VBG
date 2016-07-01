using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche
{
	public enum TipoPersonaEnum
	{
		Fisica,
		Giuridica
	}

	public enum SessoEnum
	{
		Maschio,
		Femmina
	}


	public partial class AnagraficaDomanda
	{
		public int? Id { get; set; }
		public TipoPersonaEnum TipoPersona { get; set; }
		public string Nominativo { get; set; }
		public string Nome { get; set; }
		public SessoEnum Sesso { get; set; }
		public DatiNascitaAnagrafica DatiNascita { get; set; }

		public int? IdFormagiuridica { get; set; }
		public int? IdTitolo { get; set; }
		public int? IdCittadinanza { get; set; }
		public TipoSoggettoDomanda TipoSoggetto { get; set; }

		public IndirizzoAnagraficaDomanda IndirizzoResidenza { get; set; }
		public IndirizzoAnagraficaDomanda IndirizzoCorrispondenza { get; set; }

		public DatiContattoAnagrafica Contatti { get; set; }

		public string Codicefiscale { get; set; }
		public string PartitaIva { get; set; }

		public string Note { get; set; }

		public DatiIscrizioneRegTrib DatiIscrizioneRegTrib { get; set; }
		public DatiIscrizioneCciaa DatiIscrizioneCciaa { get; set; }

		public DateTime? DataCostituzione { get; set; }
		public DatiIscrizioneReaAnagrafica DatiIscrizioneRea { get; set; }

		public DatiIscrizioneAlboProfessionale DatiIscrizioneAlboProfessionale { get; set; }
		public int? IdAnagraficaCollegata { get; set; }
		public bool IsCittadinoExtracomunitario { get; set; }
		public AnagraficaDomanda AnagraficaCollegata { get; private set; }
        public DatiIscrizioneInpsType DatiIscrizioneInps { get; private set; }
        public DatiIscrizioneInailType DatiIscrizioneInail { get; private set; }

		public static AnagraficaDomanda FromAnagrafeRow(PresentazioneIstanzaDbV2.ANAGRAFERow anagrafica)
		{
			return new AnagraficaDomanda(anagrafica);
		}

		public static AnagraficaDomanda New(int id)
		{
			return new AnagraficaDomanda { Id = id };
		}

		public static AnagraficaDomanda DaCodiceFiscaleTipoPersona(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva)
		{
			var anag = new AnagraficaDomanda();

			anag.TipoPersona = tipoPersona;
			/*
			if (tipoPersona == TipoPersonaEnum.Fisica || codiceFiscalePartitaIva.Length == 16)
			{
				anag.Codicefiscale = codiceFiscalePartitaIva;
				anag.PartitaIva = String.Empty;
			}
			else
			{
				anag.PartitaIva = codiceFiscalePartitaIva;
				anag.Codicefiscale = String.Empty;
			}
			*/

			// Da ora in po isi utilizza solo il codice fiscale impresa
			anag.Codicefiscale = codiceFiscalePartitaIva;
			anag.PartitaIva = String.Empty;
			
			return anag;
		}
		

		protected AnagraficaDomanda()
		{
            DatiIscrizioneInps = DatiIscrizioneInpsType.New();
            DatiIscrizioneInail = DatiIscrizioneInailType.New();
		}

		protected AnagraficaDomanda(PresentazioneIstanzaDbV2.ANAGRAFERow anagrafeRow)
		{
			this.Id = anagrafeRow.ANAGRAFE_PK;
			this.TipoPersona = anagrafeRow.TIPOANAGRAFE == "F" ? TipoPersonaEnum.Fisica : TipoPersonaEnum.Giuridica;
			this.Nominativo = anagrafeRow.NOMINATIVO;
			this.Nome = anagrafeRow.NOME;

			this.IdFormagiuridica = anagrafeRow.IsFORMAGIURIDICANull() ? (int?)null : (int)anagrafeRow.FORMAGIURIDICA;

			this.Codicefiscale = anagrafeRow.CODICEFISCALE;
			this.PartitaIva = anagrafeRow.PartitaIva;

			this.Sesso = anagrafeRow.SESSO == "F" ? SessoEnum.Femmina : SessoEnum.Maschio;

			this.DatiNascita = new DatiNascitaAnagrafica(anagrafeRow.CODCOMNASCITA,
														  anagrafeRow.PROVINCIANASCITA,
														  anagrafeRow.IsDATANASCITANull() ? (DateTime?)null : anagrafeRow.DATANASCITA);

			this.IdTitolo = string.IsNullOrEmpty(anagrafeRow.TITOLO) ? (int?)null : Convert.ToInt32(anagrafeRow.TITOLO);

			this.DataCostituzione = anagrafeRow.IsDATANOMINATIVONull() ? (DateTime?)null : anagrafeRow.DATANOMINATIVO;
			this.IdCittadinanza = anagrafeRow.IsCODICECITTADINANZANull() ? (int?)null : (int)anagrafeRow.CODICECITTADINANZA;

            this.IndirizzoResidenza = new IndirizzoAnagraficaDomanda(anagrafeRow.INDIRIZZO,
                                                                            anagrafeRow.CITTA,
                                                                            anagrafeRow.CAP,
                                                                            anagrafeRow.PROVINCIA,
                                                                            anagrafeRow.COMUNERESIDENZA);

			this.IndirizzoCorrispondenza = new IndirizzoAnagraficaDomanda(anagrafeRow.INDIRIZZOCORRISPONDENZA,
																			anagrafeRow.CITTACORRISPONDENZA,
																			anagrafeRow.CAPCORRISPONDENZA,
																			anagrafeRow.PROVINCIACORRISPONDENZA,
																			anagrafeRow.COMUNECORRISPONDENZA);

			this.Contatti = new DatiContattoAnagrafica(anagrafeRow.TELEFONO,
														anagrafeRow.TELEFONOCELLULARE,
														anagrafeRow.FAX,
														anagrafeRow.EMAIL,
														anagrafeRow.Pec);

			this.Note = anagrafeRow.NOTE;

			this.DatiIscrizioneRegTrib = this.TipoPersona == TipoPersonaEnum.Fisica ?
											null :
											new DatiIscrizioneRegTrib(anagrafeRow.REGTRIB,
                                                                        anagrafeRow.IsDATAREGTRIBNull() ? (DateTime?)null : anagrafeRow.DATAREGTRIB,
																		anagrafeRow.CODCOMREGTRIB);
			this.DatiIscrizioneCciaa = this.TipoPersona == TipoPersonaEnum.Fisica ?
											null :
											new DatiIscrizioneCciaa(anagrafeRow.REGDITTE,
																		anagrafeRow.IsDATAREGDITTENull() ? (DateTime?)null : anagrafeRow.DATAREGDITTE,
																		anagrafeRow.CODCOMREGDITTE);

			this.DatiIscrizioneRea = this.TipoPersona == TipoPersonaEnum.Fisica ?
										null :
										new DatiIscrizioneReaAnagrafica(anagrafeRow.PROVINCIAREA,
																		 anagrafeRow.NUMISCRREA,
																		 anagrafeRow.IsDATAISCRREANull() ? (DateTime?)null : anagrafeRow.DATAISCRREA);

			this.DatiIscrizioneAlboProfessionale = String.IsNullOrEmpty(anagrafeRow.IdAlbo) ?
														null :
														new DatiIscrizioneAlboProfessionale(Convert.ToInt32(anagrafeRow.IdAlbo),
																							 anagrafeRow.DescrizioneAlbo,
																							 anagrafeRow.NumeroAlbo,
																							 anagrafeRow.ProvinciaAlbo);

			this.IdAnagraficaCollegata = String.IsNullOrEmpty(anagrafeRow.IdAnagraficaCollegata) ?
											(int?)null :
											Convert.ToInt32(anagrafeRow.IdAnagraficaCollegata);

			if (anagrafeRow.IsTIPOSOGGETTONull())
			{
				this.TipoSoggetto = TipoSoggettoDomanda.NonDefinito;
			}
			else
			{
				this.TipoSoggetto = new TipoSoggettoDomanda
				{
					Id = anagrafeRow.TIPOSOGGETTO,
					Descrizione = anagrafeRow.DescrSoggetto,
					DescrizioneEstesa = anagrafeRow.DescrizioneTipoSoggetto,
					Ruolo = TipoSoggettoDomanda.RuoloDaCodiceBackoffice(anagrafeRow.FlagTipoSoggetto),
					RichiedeAnagraficaCollegata = anagrafeRow.IsFlagRichiedeAnagraficaCollegataNull() ? false : anagrafeRow.FlagRichiedeAnagraficaCollegata
				};
			}

			this.IsCittadinoExtracomunitario = anagrafeRow.IsIsCittadinoExtracomunitarioNull() ? false : anagrafeRow.IsCittadinoExtracomunitario;

            
            this.DatiIscrizioneInps = new DatiIscrizioneInpsType(anagrafeRow.MatricolaInps, anagrafeRow.CodSedeIscrizioneInps, anagrafeRow.DesSedeIscrizioneInps);
            this.DatiIscrizioneInail = new DatiIscrizioneInailType(anagrafeRow.MatricolaInail, anagrafeRow.CodSedeIscrizioneInail, anagrafeRow.DesSedeIscrizioneInail);

		}

		public PresentazioneIstanzaDbV2.ANAGRAFERow ToAnagrafeRow()
		{
			var table = new PresentazioneIstanzaDbV2.ANAGRAFEDataTable();

			var anagrafeRow = table.NewANAGRAFERow();

			anagrafeRow.ANAGRAFE_PK = this.Id.GetValueOrDefault(-1);
			anagrafeRow.TIPOANAGRAFE = this.TipoPersona == TipoPersonaEnum.Fisica ? "F" : "G";
			anagrafeRow.NOMINATIVO = this.Nominativo;
			anagrafeRow.NOME = this.Nome;


			anagrafeRow["FORMAGIURIDICA"] = this.IdFormagiuridica.HasValue ? (object)this.IdFormagiuridica.Value : DBNull.Value;
			anagrafeRow.CODICEFISCALE = this.Codicefiscale;
			anagrafeRow.PartitaIva = this.PartitaIva;

			anagrafeRow.SESSO = this.Sesso == SessoEnum.Maschio ? "M" : "F";

			if (this.DatiNascita != null)
			{
				anagrafeRow.CODCOMNASCITA = this.DatiNascita.CodiceComune;
				anagrafeRow.PROVINCIANASCITA = this.DatiNascita.SiglaProvincia;

				if (this.DatiNascita.Data.HasValue)
					anagrafeRow.DATANASCITA = this.DatiNascita.Data.Value;
			}

			if (this.IdTitolo.HasValue)
				anagrafeRow.TITOLO = this.IdTitolo.Value.ToString();

			if (this.DataCostituzione.HasValue)
				anagrafeRow.DATANOMINATIVO = this.DataCostituzione.Value;

			if (this.IdCittadinanza.HasValue)
				anagrafeRow.CODICECITTADINANZA = this.IdCittadinanza.Value;

			anagrafeRow.IsCittadinoExtracomunitario = this.IsCittadinoExtracomunitario;

			// Indirizzo
			if (this.IndirizzoResidenza != null)
			{
				anagrafeRow.INDIRIZZO = this.IndirizzoResidenza.Via;
				anagrafeRow.CITTA = this.IndirizzoResidenza.Citta;
				anagrafeRow.CAP = this.IndirizzoResidenza.Cap;
				anagrafeRow.PROVINCIA = this.IndirizzoResidenza.SiglaProvincia;
				anagrafeRow.COMUNERESIDENZA = this.IndirizzoResidenza.CodiceComune;
			}

			// Indirizzo corrispondenza
			if (this.IndirizzoCorrispondenza != null)
			{
				anagrafeRow.INDIRIZZOCORRISPONDENZA = this.IndirizzoCorrispondenza.Via;
				anagrafeRow.CITTACORRISPONDENZA = this.IndirizzoCorrispondenza.Citta;
				anagrafeRow.CAPCORRISPONDENZA = this.IndirizzoCorrispondenza.Cap;
				anagrafeRow.PROVINCIACORRISPONDENZA = this.IndirizzoCorrispondenza.SiglaProvincia;
				anagrafeRow.COMUNECORRISPONDENZA = this.IndirizzoCorrispondenza.CodiceComune;
			}

			// Recapiti per contatti
			if (this.Contatti != null)
			{
				anagrafeRow.TELEFONO = this.Contatti.Telefono;
				anagrafeRow.TELEFONOCELLULARE = this.Contatti.TelefonoCellulare;
				anagrafeRow.FAX = this.Contatti.Fax;
				anagrafeRow.EMAIL = this.Contatti.Email;
				anagrafeRow.Pec = this.Contatti.Pec;
			}

			anagrafeRow.NOTE = this.Note;

			// Reg trib
			if (this.DatiIscrizioneRegTrib == null)
			{
				anagrafeRow["REGTRIB"] = null;
				anagrafeRow["DATAREGTRIB"] = DBNull.Value;
				anagrafeRow["CODCOMREGTRIB"] = null;
			}
			else
			{
				anagrafeRow["REGTRIB"] = this.DatiIscrizioneRegTrib.Numero;
				anagrafeRow["DATAREGTRIB"] = this.DatiIscrizioneRegTrib.Data.HasValue ? (object)this.DatiIscrizioneRegTrib.Data.Value : (object)DBNull.Value;
				anagrafeRow["CODCOMREGTRIB"] = this.DatiIscrizioneRegTrib.CodiceComune;
			}


			// Reg ditte
			if (this.DatiIscrizioneCciaa == null)
			{
				anagrafeRow["REGDITTE"] = null;
				anagrafeRow["DATAREGDITTE"] = DBNull.Value;
				anagrafeRow["CODCOMREGDITTE"] = null;
			}
			else
			{
				anagrafeRow["REGDITTE"] = this.DatiIscrizioneCciaa.Numero;
				anagrafeRow["DATAREGDITTE"] = this.DatiIscrizioneCciaa.Data.HasValue ? (object)this.DatiIscrizioneCciaa.Data.Value : (object)DBNull.Value;
				anagrafeRow["CODCOMREGDITTE"] = this.DatiIscrizioneCciaa.CodiceComune;
			}

			// REA
			if (this.DatiIscrizioneRea == null)
			{
				anagrafeRow["PROVINCIAREA"] = null;
				anagrafeRow["NUMISCRREA"] = DBNull.Value;
				anagrafeRow["DATAISCRREA"] = DBNull.Value;
			}
			else
			{
				anagrafeRow["PROVINCIAREA"] = this.DatiIscrizioneRea.SiglaProvincia;
				anagrafeRow["DATAISCRREA"] = this.DatiIscrizioneRea.Data.HasValue ? (object)this.DatiIscrizioneRea.Data.Value : (object)DBNull.Value;
				anagrafeRow["NUMISCRREA"] = this.DatiIscrizioneRea.Numero;
			}

			// Albo professionale
			if (this.DatiIscrizioneAlboProfessionale == null)
			{
				anagrafeRow["IdAlbo"] = null;
				anagrafeRow["DescrizioneAlbo"] = null;
				anagrafeRow["NumeroAlbo"] = null;
				anagrafeRow["ProvinciaAlbo"] = null;
			}
			else
			{
				anagrafeRow["IdAlbo"] = this.DatiIscrizioneAlboProfessionale.IdAlbo.ToString();
				anagrafeRow["DescrizioneAlbo"] = this.DatiIscrizioneAlboProfessionale.Descrizione;
				anagrafeRow["NumeroAlbo"] = this.DatiIscrizioneAlboProfessionale.Numero;
				anagrafeRow["ProvinciaAlbo"] = this.DatiIscrizioneAlboProfessionale.SiglaProvincia;
			}

			anagrafeRow.IdAnagraficaCollegata = this.IdAnagraficaCollegata.ToString();

			if (this.TipoSoggetto == TipoSoggettoDomanda.NonDefinito || this.TipoSoggetto == null)
			{
				anagrafeRow.SetTIPOSOGGETTONull();
				anagrafeRow["DescrSoggetto"] = null;
				anagrafeRow["DescrizioneTipoSoggetto"] = null;
				anagrafeRow["FlagTipoSoggetto"] = null;
				anagrafeRow.SetFlagRichiedeAnagraficaCollegataNull();
			}
			else
			{
				anagrafeRow["TIPOSOGGETTO"] = this.TipoSoggetto.Id;
				anagrafeRow["DescrSoggetto"] = this.TipoSoggetto.Descrizione;
				anagrafeRow["DescrizioneTipoSoggetto"] = this.TipoSoggetto.DescrizioneEstesa;
				anagrafeRow["FlagTipoSoggetto"] = this.TipoSoggetto.RuoloAsCodiceBackoffice();
				anagrafeRow["FlagRichiedeAnagraficaCollegata"] = this.TipoSoggetto.RichiedeAnagraficaCollegata;
			}

            anagrafeRow.MatricolaInps = this.DatiIscrizioneInps.Matricola;
            anagrafeRow.CodSedeIscrizioneInps = this.DatiIscrizioneInps.CodiceSede;
            anagrafeRow.DesSedeIscrizioneInps= this.DatiIscrizioneInps.DescrizioneSede;

            anagrafeRow.MatricolaInail = this.DatiIscrizioneInail.Matricola;
            anagrafeRow.CodSedeIscrizioneInail = this.DatiIscrizioneInail.CodiceSede;
            anagrafeRow.DesSedeIscrizioneInail = this.DatiIscrizioneInail.DescrizioneSede;

			return anagrafeRow;
		}

		public override string ToString()
		{
			var sb = new StringBuilder();

			sb.Append(this.Nominativo);

			if (!String.IsNullOrEmpty(this.Nome))
				sb.Append(" ").Append(this.Nome);

			sb.Append(" [");

			if (!String.IsNullOrEmpty(this.Codicefiscale))
				sb.Append("cf: ").Append(this.Codicefiscale);

			if (!String.IsNullOrEmpty(this.PartitaIva))
			{
				if (!String.IsNullOrEmpty(this.Codicefiscale))
					sb.Append(", ");

				sb.Append("p.iva: ").Append(this.PartitaIva);
			}


			sb.Append("]");
			return sb.ToString();
		}

		public override bool Equals(object obj)
		{
			if (obj == null) return false;
			if (this.GetType() != obj.GetType()) return false;

			var typedObj = obj as AnagraficaDomanda;

			return this.Codicefiscale.ToUpperInvariant().Equals(typedObj.Codicefiscale.ToUpperInvariant()) &&
					this.PartitaIva.ToUpperInvariant().Equals(typedObj.PartitaIva.ToUpperInvariant()) &&
					this.TipoPersona == typedObj.TipoPersona &&
                    this.TipoSoggetto == typedObj.TipoSoggetto;
		}


		public override int GetHashCode()
		{
			return this.Codicefiscale.GetHashCode() ^ this.PartitaIva.GetHashCode() ^ this.TipoPersona.GetHashCode();
		}

		public static Boolean operator ==(AnagraficaDomanda v1, AnagraficaDomanda v2)
		{

			if ((object)v1 == null)
				if ((object)v2 == null)
					return true;
				else
					return false;

			return (v1.Equals(v2));
		}

		public static Boolean operator !=(AnagraficaDomanda v1, AnagraficaDomanda v2)
		{
			return !(v1 == v2);
		}



		internal void CollegaAnagrafica(AnagraficaDomanda anagraficaCollegata)
		{
			this.AnagraficaCollegata = anagraficaCollegata;
		}

		internal AnagraficaDomanda DuplicaRimuovendoIlTipoSoggetto()
		{
			var newAnagrafica = new AnagraficaDomanda();

			newAnagrafica.Id = -1;
			newAnagrafica.TipoPersona = this.TipoPersona;
			newAnagrafica.Nominativo = this.Nominativo;
			newAnagrafica.Nome = this.Nome;
			newAnagrafica.Sesso = this.Sesso;
			newAnagrafica.DatiNascita = this.DatiNascita;
			newAnagrafica.IdFormagiuridica = this.IdFormagiuridica;
			newAnagrafica.IdTitolo = this.IdTitolo;
			newAnagrafica.IdCittadinanza = this.IdCittadinanza;
			newAnagrafica.TipoSoggetto = null;
			newAnagrafica.IndirizzoResidenza = this.IndirizzoResidenza;
			newAnagrafica.IndirizzoCorrispondenza = this.IndirizzoCorrispondenza;
			newAnagrafica.Contatti = this.Contatti;
			newAnagrafica.Codicefiscale = this.Codicefiscale;
			newAnagrafica.PartitaIva = this.PartitaIva;
			newAnagrafica.Note = this.Note;
			newAnagrafica.DatiIscrizioneRegTrib = this.DatiIscrizioneRegTrib;
			newAnagrafica.DatiIscrizioneCciaa = this.DatiIscrizioneCciaa;
			newAnagrafica.DataCostituzione = this.DataCostituzione;
			newAnagrafica.DatiIscrizioneRea = this.DatiIscrizioneRea;
			newAnagrafica.DatiIscrizioneAlboProfessionale = this.DatiIscrizioneAlboProfessionale;
			newAnagrafica.IdAnagraficaCollegata = this.IdAnagraficaCollegata;
			newAnagrafica.IsCittadinoExtracomunitario = this.IsCittadinoExtracomunitario;
			newAnagrafica.AnagraficaCollegata = this.AnagraficaCollegata;

			return newAnagrafica;
		}
    }
}
