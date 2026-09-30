// -----------------------------------------------------------------------
// <copyright file="AnagraficaUtente.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

	[Serializable]
	public partial class AnagraficaUtente
	{
		#region Membri privati

		private int? m_codiceanagrafe = null;

		private string m_nominativo = null;

		private string m_referente = null;

		private int? m_formagiuridica = null;

		private int? m_tipologia = null;

		private string m_indirizzo = null;

		private string m_citta = null;

		private string m_cap = null;

		private string m_provincia = null;

		private string m_telefono = null;

		private string m_telefonocellulare = null;

		private string m_fax = null;

		private string m_partitaiva = null;

		private string m_legalerappresentante = null;

		private string m_codicefiscale = null;

		private string m_note = null;

		private string m_email = null;

		private string m_regditte = null;

		private string m_regtrib = null;

		private string m_codcomregditte = null;

		private string m_codcomregtrib = null;

		private string m_codcomnascita = null;

		private DateTime? m_datanascita = null;

		private DateTime? m_dataregditte = null;

		private DateTime? m_dataregtrib = null;

		private int? m_invioemail = null;

		private string m_sesso = null;

		private string m_nome = null;

		private int? m_titolo = null;

		private string m_tipoanagrafe = null;

		private string m_indirizzolr = null;

		private string m_cittalr = null;

		private string m_caplr = null;

		private string m_provincialr = null;

		private string m_telefonolr = null;

		private string m_telefonocellularelr = null;

		private DateTime? m_datanominativo = null;

		private int? m_invioemailtec = null;

		private int? m_codicecittadinanza = null;

		private string m_comuneresidenza = null;

		private string m_password = null;

		private string m_indirizzocorrispondenza = null;

		private string m_cittacorrispondenza = null;

		private string m_capcorrispondenza = null;

		private string m_provinciacorrispondenza = null;

		private string m_comunecorrispondenza = null;

		private string m_provinciarea = null;

		private string m_numiscrrea = null;

		private DateTime? m_dataiscrrea = null;

		private string m_idcomune = null;

		private string m_codicefiscalelr = null;

		private int? m_flag_noprofit = null;

		private int? m_flag_disabilitato = null;

		private DateTime? m_data_disabilitato = null;

		private int? m_codiceelencopro = null;

		private string m_numeroelencopro = null;

		private string m_provinciaelencopro = null;


		#endregion

		#region properties

		#region Key Fields


		public int? Codiceanagrafe
		{
			get { return m_codiceanagrafe; }
			set { m_codiceanagrafe = value; }
		}

		public string Idcomune
		{
			get { return m_idcomune; }
			set { m_idcomune = value; }
		}


		#endregion

		#region Data fields

		public string Nominativo
		{
			get { return m_nominativo; }
			set { m_nominativo = value; }
		}

		public string Referente
		{
			get { return m_referente; }
			set { m_referente = value; }
		}

		public int? Formagiuridica
		{
			get { return m_formagiuridica; }
			set { m_formagiuridica = value; }
		}

		public int? Tipologia
		{
			get { return m_tipologia; }
			set { m_tipologia = value; }
		}

		public string Indirizzo
		{
			get { return m_indirizzo; }
			set { m_indirizzo = value; }
		}

		public string Citta
		{
			get { return m_citta; }
			set { m_citta = value; }
		}

		public string Cap
		{
			get { return m_cap; }
			set { m_cap = value; }
		}

		public string Provincia
		{
			get { return m_provincia; }
			set { m_provincia = value; }
		}

		public string Telefono
		{
			get { return m_telefono; }
			set { m_telefono = value; }
		}

		public string Telefonocellulare
		{
			get { return m_telefonocellulare; }
			set { m_telefonocellulare = value; }
		}

		public string Fax
		{
			get { return m_fax; }
			set { m_fax = value; }
		}

		public string Partitaiva
		{
			get { return m_partitaiva; }
			set { m_partitaiva = value; }
		}

		public string Legalerappresentante
		{
			get { return m_legalerappresentante; }
			set { m_legalerappresentante = value; }
		}

		public string Codicefiscale
		{
			get { return m_codicefiscale; }
			set { m_codicefiscale = value; }
		}

		public string Note
		{
			get { return m_note; }
			set { m_note = value; }
		}

		public string Email
		{
			get { return m_email; }
			set { m_email = value; }
		}

		public string Regditte
		{
			get { return m_regditte; }
			set { m_regditte = value; }
		}

		public string Regtrib
		{
			get { return m_regtrib; }
			set { m_regtrib = value; }
		}

		public string Codcomregditte
		{
			get { return m_codcomregditte; }
			set { m_codcomregditte = value; }
		}

		public string Codcomregtrib
		{
			get { return m_codcomregtrib; }
			set { m_codcomregtrib = value; }
		}

		public string Codcomnascita
		{
			get { return m_codcomnascita; }
			set { m_codcomnascita = value; }
		}

		public DateTime? Datanascita
		{
			get { return m_datanascita; }
			set { m_datanascita = value; }
		}

		public DateTime? Dataregditte
		{
			get { return m_dataregditte; }
			set { m_dataregditte = value; }
		}

		public DateTime? Dataregtrib
		{
			get { return m_dataregtrib; }
			set { m_dataregtrib = value; }
		}

		public int? Invioemail
		{
			get { return m_invioemail; }
			set { m_invioemail = value; }
		}

		public string Sesso
		{
			get { return m_sesso; }
			set { m_sesso = value; }
		}

		public string Nome
		{
			get { return m_nome; }
			set { m_nome = value; }
		}

		public int? Titolo
		{
			get { return m_titolo; }
			set { m_titolo = value; }
		}

		public string Tipoanagrafe
		{
			get { return m_tipoanagrafe; }
			set { m_tipoanagrafe = value; }
		}

		public string Indirizzolr
		{
			get { return m_indirizzolr; }
			set { m_indirizzolr = value; }
		}

		public string Cittalr
		{
			get { return m_cittalr; }
			set { m_cittalr = value; }
		}

		public string Caplr
		{
			get { return m_caplr; }
			set { m_caplr = value; }
		}

		public string Provincialr
		{
			get { return m_provincialr; }
			set { m_provincialr = value; }
		}

		public string Telefonolr
		{
			get { return m_telefonolr; }
			set { m_telefonolr = value; }
		}

		public string Telefonocellularelr
		{
			get { return m_telefonocellularelr; }
			set { m_telefonocellularelr = value; }
		}

		public DateTime? Datanominativo
		{
			get { return m_datanominativo; }
			set { m_datanominativo = value; }
		}

		public int? Invioemailtec
		{
			get { return m_invioemailtec; }
			set { m_invioemailtec = value; }
		}

		public int? Codicecittadinanza
		{
			get { return m_codicecittadinanza; }
			set { m_codicecittadinanza = value; }
		}

		public string Comuneresidenza
		{
			get { return m_comuneresidenza; }
			set { m_comuneresidenza = value; }
		}

		public string Password
		{
			get { return m_password; }
			set { m_password = value; }
		}

		public string Indirizzocorrispondenza
		{
			get { return m_indirizzocorrispondenza; }
			set { m_indirizzocorrispondenza = value; }
		}

		public string Cittacorrispondenza
		{
			get { return m_cittacorrispondenza; }
			set { m_cittacorrispondenza = value; }
		}

		public string Capcorrispondenza
		{
			get { return m_capcorrispondenza; }
			set { m_capcorrispondenza = value; }
		}

		public string Provinciacorrispondenza
		{
			get { return m_provinciacorrispondenza; }
			set { m_provinciacorrispondenza = value; }
		}

		public string Comunecorrispondenza
		{
			get { return m_comunecorrispondenza; }
			set { m_comunecorrispondenza = value; }
		}

		public string Provinciarea
		{
			get { return m_provinciarea; }
			set { m_provinciarea = value; }
		}

		public string Numiscrrea
		{
			get { return m_numiscrrea; }
			set { m_numiscrrea = value; }
		}

		public DateTime? Dataiscrrea
		{
			get { return m_dataiscrrea; }
			set { m_dataiscrrea = value; }
		}

		public string Codicefiscalelr
		{
			get { return m_codicefiscalelr; }
			set { m_codicefiscalelr = value; }
		}

		public int? FlagNoprofit
		{
			get { return m_flag_noprofit; }
			set { m_flag_noprofit = value; }
		}

		public int? FlagDisabilitato
		{
			get { return m_flag_disabilitato; }
			set { m_flag_disabilitato = value; }
		}

		public DateTime? DataDisabilitato
		{
			get { return m_data_disabilitato; }
			set { m_data_disabilitato = value; }
		}

		public int? Codiceelencopro
		{
			get { return m_codiceelencopro; }
			set { m_codiceelencopro = value; }
		}

		public string Numeroelencopro
		{
			get { return m_numeroelencopro; }
			set { m_numeroelencopro = value; }
		}

		public string Provinciaelencopro
		{
			get { return m_provinciaelencopro; }
			set { m_provinciaelencopro = value; }
		}

		public bool UtenteTester { get; set; }

		#endregion

		#endregion

		public Anagrafe ToWsAnagrafe()
		{
			var rVal = new Anagrafe();

			rVal.CODICEANAGRAFE = this.Codiceanagrafe.HasValue ? this.Codiceanagrafe.ToString() : String.Empty;
			rVal.IDCOMUNE = this.Idcomune;
			rVal.NOMINATIVO = this.Nominativo;
			rVal.FORMAGIURIDICA = this.Formagiuridica.HasValue ? this.Formagiuridica.ToString() : String.Empty;
			rVal.TIPOLOGIA = this.Tipologia.HasValue ? this.Tipologia.ToString() : String.Empty;
			rVal.INDIRIZZO = this.Indirizzo;
			rVal.CITTA = this.Citta;
			rVal.CAP = this.Cap;
			rVal.PROVINCIA = this.Provincia;
			rVal.TELEFONO = this.Telefono;
			rVal.TELEFONOCELLULARE = this.Telefonocellulare;
			rVal.FAX = this.Fax;
			rVal.PARTITAIVA = this.Partitaiva;
			rVal.CODICEFISCALE = this.Codicefiscale;
			rVal.NOTE = this.Note;
			rVal.EMAIL = this.Email;
			rVal.REGDITTE = this.Regditte;
			rVal.REGTRIB = this.Regtrib;
			rVal.CODCOMREGDITTE = this.Codcomregditte;
			rVal.CODCOMREGTRIB = this.Codcomregtrib;
			rVal.CODCOMNASCITA = this.Codcomnascita;
			rVal.DATANASCITA = this.Datanascita;
			rVal.DATAREGDITTE = this.Dataregditte;
			rVal.DATAREGTRIB = this.Dataregtrib;
			rVal.INVIOEMAIL = this.Invioemail.GetValueOrDefault(0).ToString();
			rVal.SESSO = this.Sesso;
			rVal.NOME = this.Nome;
			rVal.TITOLO = this.Titolo.HasValue ? this.Titolo.ToString() : String.Empty;
			rVal.TIPOANAGRAFE = this.Tipoanagrafe;
			rVal.DATANOMINATIVO = this.Datanominativo;
			rVal.INVIOEMAILTEC = this.Invioemailtec.GetValueOrDefault(0).ToString();
			rVal.CODICECITTADINANZA = this.Codicecittadinanza.HasValue ? this.Codicecittadinanza.ToString() : String.Empty;
			rVal.COMUNERESIDENZA = this.Comuneresidenza;
			rVal.PASSWORD = this.Password;
			rVal.INDIRIZZOCORRISPONDENZA = this.Indirizzocorrispondenza;
			rVal.CITTACORRISPONDENZA = this.Cittacorrispondenza;
			rVal.CAPCORRISPONDENZA = this.Capcorrispondenza;
			rVal.PROVINCIACORRISPONDENZA = this.Provinciacorrispondenza;
			rVal.COMUNECORRISPONDENZA = this.Comunecorrispondenza;
			rVal.PROVINCIAREA = this.Provinciarea;
			rVal.NUMISCRREA = this.Numiscrrea;
			rVal.DATAISCRREA = this.Dataiscrrea;
			rVal.FLAG_NOPROFIT = this.FlagNoprofit.GetValueOrDefault(0).ToString();
			rVal.FLAG_DISABILITATO = this.FlagDisabilitato.GetValueOrDefault(0).ToString();
			rVal.DATA_DISABILITATO = this.DataDisabilitato;
			rVal.CODICEELENCOPRO = this.Codiceelencopro.HasValue ? this.Codiceelencopro.ToString() : String.Empty;
			rVal.NUMEROELENCOPRO = this.Numeroelencopro;
			rVal.PROVINCIAELENCOPRO = this.Provinciaelencopro;
			rVal.FoUtenteTester = this.UtenteTester ? 1 : 0;

			return rVal;
		}

		public override string ToString()
		{
			string n = this.Nominativo;

			if (!string.IsNullOrEmpty(this.Nome))
				n += " " + this.Nome;

			return n;
		}
	}
}
