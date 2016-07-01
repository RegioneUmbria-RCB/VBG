using System;
using System.Data;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;
using System.Collections.Generic;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
	[DataTable("MOVIMENTI")]
	[Serializable]
	public class Movimenti : BaseDataClass
	{

		#region Key Fields

		string codicemovimento = null;

		string idcomune = null;
		/// <summary>
		/// Chiave primaria insieme a CODICEMOVIMENTO
		/// </summary>
		[XmlElement(Order = 0)]
		[KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		/// <summary>
		/// Chiave primaria insieme a IDCOMUNE
		/// </summary>
		[useSequence]
		[KeyField("CODICEMOVIMENTO", Type = DbType.Decimal)]
		[XmlElement(Order = 1)]
		public string CODICEMOVIMENTO
		{
			get { return codicemovimento; }
			set { codicemovimento = value; }
		}
		#endregion

		string codiceistanza = null;
		/// <summary>
		/// Id dell'istanza collegata (fk su ISTANZE.CODICEISTANZA insieme a IDCOMUNE)
		/// </summary>
		[isRequired]
		[DataField("CODICEISTANZA", Type = DbType.Decimal)]
		[XmlElement(Order = 2)]
		public string CODICEISTANZA
		{
			get { return codiceistanza; }
			set { codiceistanza = value; }
		}

		string tipomovimento = null;
		/// <summary>
		/// Id tipo movimento (fk su TIPIMOVIMENTO.TIPOMOVIMENTO inseime a IDCOMUNE)
		/// </summary>
		[isRequired]
		[DataField("TIPOMOVIMENTO", Size = 6, Type = DbType.String, CaseSensitive = false)]
		[XmlElement(Order = 3)]
		public string TIPOMOVIMENTO
		{
			get { return tipomovimento; }
			set { tipomovimento = value; }
		}

		string movimento = null;
		/// <summary>
		/// Nome del movimento
		/// </summary>
		[isRequired]
		[DataField("MOVIMENTO", Size = 128, Type = DbType.String, CaseSensitive = false)]
		[XmlElement(Order = 4)]
		public string MOVIMENTO
		{
			get { return movimento; }
			set { movimento = value; }
		}

		string codiceinventario = null;
		/// <summary>
		/// fk su INVENTARIOPROCEDIMENTI.CODICEINVENTARIO insieme a IDCOMUNE
		/// </summary>
		[DataField("CODICEINVENTARIO", Type = DbType.Decimal)]
		[XmlElement(Order = 5)]
		public string CODICEINVENTARIO
		{
			get { return codiceinventario; }
			set { codiceinventario = value; }
		}

		string codiceamministrazione = null;
		/// <summary>
		/// Fk su AMMINISTRAZIONI.CODICEAMMINISTRAZIONE insieme a IDCOMUNE
		/// </summary>
		[DataField("CODICEAMMINISTRAZIONE", Type = DbType.Decimal)]
		[XmlElement(Order = 6)]
		public string CODICEAMMINISTRAZIONE
		{
			get { return codiceamministrazione; }
			set { codiceamministrazione = value; }
		}

		string codammrichiedente = null;
		[DataField("CODAMMRICHIEDENTE", Type = DbType.Decimal)]
		[XmlElement(Order = 7)]
		public string CODAMMRICHIEDENTE
		{
			get { return codammrichiedente; }
			set { codammrichiedente = value; }
		}

		DateTime? data = null;
		/// <summary>
		/// Data del movimento o null se ilmovimento deve essere ancora eseguire
		/// </summary>
		[isRequired]
		[DataField("DATA", Type = DbType.DateTime)]
		[XmlElement(Order = 8)]
		public DateTime? DATA
		{
			get { return data; }
			set { data = VerificaDataLocale(value); }
		}

		string parere = null;
		/// <summary>
		/// Descrizione estesa del parere
		/// </summary>
		[DataField("PARERE", Size = 2000, Type = DbType.String, CaseSensitive = false)]
		[XmlElement(Order = 9)]
		public string PARERE
		{
			get { return parere; }
			set { parere = value; }
		}

		string esito = null;
		/// <summary>
		/// Esito: 1 o -1 = esito positivo, 0 o null = esito negativo
		/// </summary>
		[isRequired]
		[DataField("ESITO", Type = DbType.Decimal)]
		[XmlElement(Order = 10)]
		public string ESITO
		{
			get { return esito; }
			set { esito = value; }
		}

		string note = null;
		/// <summary>
		/// Note del movimento
		/// </summary>
		[DataField("NOTE", Size = 255, Type = DbType.String, CaseSensitive = false)]
		[XmlElement(Order = 11)]
		public string NOTE
		{
			get { return note; }
			set { note = value; }
		}

		string pubblica = null;
		/// <summary>
		/// Flag che indica se il movimento deve essere pubblicato nel FO (1 o -1 = pubblica, 0 o null = non pubblicare)
		/// </summary>
		[isRequired]
		[DataField("PUBBLICA", Type = DbType.Decimal)]
		[XmlElement(Order = 12)]
		public string PUBBLICA
		{
			get { return pubblica; }
			set { pubblica = value; }
		}

		string numeroprotocollo = null;
		/// <summary>
		/// Numero protocollo
		/// </summary>
		[DataField("NUMEROPROTOCOLLO", Size = 15, Type = DbType.String, CaseSensitive = false)]
		[XmlElement(Order = 13)]
		public string NUMEROPROTOCOLLO
		{
			get { return numeroprotocollo; }
			set { numeroprotocollo = value; }
		}

		DateTime? dataprotocollo = null;
		/// <summary>
		/// Data protocollo
		/// </summary>
		[DataField("DATAPROTOCOLLO", Type = DbType.DateTime)]
		[XmlElement(Order = 14)]
		public DateTime? DATAPROTOCOLLO
		{
			get { return dataprotocollo; }
			set { dataprotocollo = VerificaDataLocale(value); }
		}

		string codiceufficio = null;
		[DataField("CODICEUFFICIO", Type = DbType.Decimal)]
		[XmlElement(Order = 15)]
		public string CODICEUFFICIO
		{
			get { return codiceufficio; }
			set { codiceufficio = value; }
		}

		string fkidprotocollo = null;
		[DataField("FKIDPROTOCOLLO", Type = DbType.Decimal)]
		[XmlElement(Order = 16)]
		public string FKIDPROTOCOLLO
		{
			get { return fkidprotocollo; }
			set { fkidprotocollo = value; }
		}

		string codiceresponsabile = null;
		[isRequired]
		[DataField("CODICERESPONSABILE", Type = DbType.Decimal)]
		[XmlElement(Order = 17)]
		public string CODICERESPONSABILE
		{
			get { return codiceresponsabile; }
			set { codiceresponsabile = value; }
		}

		DateTime? datainserimento = null;
		[DataField("DATAINSERIMENTO", Type = DbType.DateTime)]
		[XmlElement(Order = 18)]
		public DateTime? DATAINSERIMENTO
		{
			get { return datainserimento; }
			set { datainserimento = VerificaDataLocale(value); }
		}

		string fileatto = null;
		[DataField("FILEATTO", Size = 255, Type = DbType.String, Compare = "like", CaseSensitive = false)]
		[XmlElement(Order = 19)]
		public string FILEATTO
		{
			get { return fileatto; }
			set { fileatto = value; }
		}

		string codiceoggetto = null;
		[DataField("CODICEOGGETTO", Type = DbType.Decimal)]
		[XmlElement(Order = 20)]
		public string CODICEOGGETTO
		{
			get { return codiceoggetto; }
			set { codiceoggetto = value; }
		}

		string pubblicaparere = null;
		[DataField("PUBBLICAPARERE", Type = DbType.Decimal)]
		[XmlElement(Order = 21)]
		public string PUBBLICAPARERE
		{
			get { return pubblicaparere; }
			set { pubblicaparere = value; }
		}

		int? creato_da_stc = null;
		[DataField("CREATO_DA_STC", Type = DbType.Decimal)]
		[XmlElement(Order = 22)]
		public int? CREATO_DA_STC
		{
			get { return creato_da_stc; }
			set { creato_da_stc = value; }
		}

		int? inviato_con_stc = null;
		[DataField("INVIATO_CON_STC", Type = DbType.Decimal)]
		[XmlElement(Order = 23)]
		public int? INVIATO_CON_STC
		{
			get { return inviato_con_stc; }
			set { inviato_con_stc = value; }
		}

		int? inviato_a_camcom = null;
		[DataField("INVIATO_A_CAMCOM", Type = DbType.Decimal)]
		[XmlElement(Order = 24)]
		public int? INVIATO_A_CAMCOM
		{
			get { return inviato_a_camcom; }
			set { inviato_a_camcom = value; }
		}

		int? flag_da_leggere = null;
		[isRequired]
		[DataField("FLAG_DA_LEGGERE", Type = DbType.Decimal)]
		[XmlElement(Order = 25)]
		public int? FLAG_DA_LEGGERE
		{
			get { return flag_da_leggere; }
			set { flag_da_leggere = value; }
		}



		#region Arraylist per gli inserimenti nelle tabelle collegate e foreign keys

		List<MovimentiAllegati> m_movimentiAllegati = new List<MovimentiAllegati>();
		[ForeignKey("IDCOMUNE,CODICEMOVIMENTO", "IDCOMUNE,CODICEMOVIMENTO")]
		[XmlElement(Order = 26)]
		public List<MovimentiAllegati> MovimentiAllegati
		{
			get { return m_movimentiAllegati; }
			set { m_movimentiAllegati = value; }
		}

		Autorizzazioni m_autorizzazione;
		[ForeignKey("IDCOMUNE, CODICEMOVIMENTO,CODICEISTANZA", "IDCOMUNE, CODICEMOVIMENTO,FKIDISTANZA")]
		[XmlElement(Order = 27)]
		public Autorizzazioni Autorizzazione
		{
			get { return m_autorizzazione; }
			set { m_autorizzazione = value; }
		}

		List<MovimentiDyn2ModelliT> m_movimentidyn2modellit = new List<MovimentiDyn2ModelliT>();
		[ForeignKey("IDCOMUNE, CODICEMOVIMENTO", "Idcomune,Codicemovimento")]
		[XmlElement(Order = 28)]
		public List<MovimentiDyn2ModelliT> MovimentiDyn2ModelliT
		{
			get { return m_movimentidyn2modellit; }
			set { m_movimentidyn2modellit = value; }
		}

		/*
		Autorizzazioni _Autorizzazione = null;
		public Autorizzazioni Autorizzazione
		{
			get { return _Autorizzazione; }
			set { _Autorizzazione = value; }
		}
		*/
		#endregion
	}
}