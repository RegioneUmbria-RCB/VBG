using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("OT_OGGCATASTALI")]
	public class Ot_OggCatastali : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string numogg=null;
		[KeyField("NUMOGG",Size=9, Type=DbType.String, CaseSensitive=false)]
		public string NUMOGG
		{
			get { return numogg; }
			set { numogg = value; }
		}

		string ft=null;
		[DataField("FT",Size=1, Type=DbType.String, CaseSensitive=false)]
		public string FT
		{
			get { return ft; }
			set { ft = value; }
		}

		string sezione=null;
		[DataField("SEZIONE",Size=3, Type=DbType.String, CaseSensitive=false)]
		public string SEZIONE
		{
			get { return sezione; }
			set { sezione = value; }
		}

		string foglio=null;
		[DataField("FOGLIO",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string FOGLIO
		{
			get { return foglio; }
			set { foglio = value; }
		}

		string numero=null;
		[DataField("NUMERO",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string NUMERO
		{
			get { return numero; }
			set { numero = value; }
		}

		string subalterno=null;
		[DataField("SUBALTERNO",Size=4, Type=DbType.String, CaseSensitive=false)]
		public string SUBALTERNO
		{
			get { return subalterno; }
			set { subalterno = value; }
		}

		string consistenza=null;
		[DataField("CONSISTENZA",Size=10, Type=DbType.String, CaseSensitive=false)]
		public string CONSISTENZA
		{
			get { return consistenza; }
			set { consistenza = value; }
		}

		string catcast=null;
		[DataField("CATCAST",Size=3, Type=DbType.String, CaseSensitive=false)]
		public string CATCAST
		{
			get { return catcast; }
			set { catcast = value; }
		}

		string classe=null;
		[DataField("CLASSE",Size=2, Type=DbType.String, CaseSensitive=false)]
		public string CLASSE
		{
			get { return classe; }
			set { classe = value; }
		}

		string rendita=null;
		[DataField("RENDITA",Size=19, Type=DbType.String, CaseSensitive=false)]
		public string RENDITA
		{
			get { return rendita; }
			set { rendita = value; }
		}

		string codvia=null;
		[DataField("CODVIA", Type=DbType.Decimal)]
		public string CODVIA
		{
			get { return codvia; }
			set { codvia = value; }
		}

		string indirizzo=null;
		[DataField("INDIRIZZO",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string INDIRIZZO
		{
			get { return indirizzo; }
			set { indirizzo = value; }
		}

		string tiponota_gen=null;
		[DataField("TIPONOTA_GEN",Size=3, Type=DbType.String, CaseSensitive=false)]
		public string TIPONOTA_GEN
		{
			get { return tiponota_gen; }
			set { tiponota_gen = value; }
		}

		string numnota_gen=null;
		[DataField("NUMNOTA_GEN",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string NUMNOTA_GEN
		{
			get { return numnota_gen; }
			set { numnota_gen = value; }
		}

		string annonota_gen=null;
		[DataField("ANNONOTA_GEN",Size=4, Type=DbType.String, CaseSensitive=false)]
		public string ANNONOTA_GEN
		{
			get { return annonota_gen; }
			set { annonota_gen = value; }
		}

		string prognota_gen=null;
		[DataField("PROGNOTA_GEN",Size=3, Type=DbType.String, CaseSensitive=false)]
		public string PROGNOTA_GEN
		{
			get { return prognota_gen; }
			set { prognota_gen = value; }
		}

		string annonota_fin=null;
		[DataField("ANNONOTA_FIN",Size=4, Type=DbType.String, CaseSensitive=false)]
		public string ANNONOTA_FIN
		{
			get { return annonota_fin; }
			set { annonota_fin = value; }
		}

		string prognota_fin=null;
		[DataField("PROGNOTA_FIN",Size=3, Type=DbType.String, CaseSensitive=false)]
		public string PROGNOTA_FIN
		{
			get { return prognota_fin; }
			set { prognota_fin = value; }
		}

		string piano=null;
		[DataField("PIANO",Size=4, Type=DbType.String, CaseSensitive=false)]
		public string PIANO
		{
			get { return piano; }
			set { piano = value; }
		}

		string ident_mut_ini=null;
		[DataField("IDENT_MUT_INI",Size=9, Type=DbType.String, CaseSensitive=false)]
		public string IDENT_MUT_INI
		{
			get { return ident_mut_ini; }
			set { ident_mut_ini = value; }
		}

		string ident_mut_fin=null;
		[DataField("IDENT_MUT_FIN",Size=9, Type=DbType.String, CaseSensitive=false)]
		public string IDENT_MUT_FIN
		{
			get { return ident_mut_fin; }
			set { ident_mut_fin = value; }
		}

		string bis=null;
		[DataField("BIS",Size=3, Type=DbType.String, CaseSensitive=false)]
		public string BIS
		{
			get { return bis; }
			set { bis = value; }
		}

		string scala=null;
		[DataField("SCALA",Size=2, Type=DbType.String, CaseSensitive=false)]
		public string SCALA
		{
			get { return scala; }
			set { scala = value; }
		}

		string progr=null;
		[DataField("PROGR",Size=3, Type=DbType.String, CaseSensitive=false)]
		public string PROGR
		{
			get { return progr; }
			set { progr = value; }
		}

		string zona=null;
		[DataField("ZONA",Size=3, Type=DbType.String, CaseSensitive=false)]
		public string ZONA
		{
			get { return zona; }
			set { zona = value; }
		}

		string superficie=null;
		[DataField("SUPERFICIE",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string SUPERFICIE
		{
			get { return superficie; }
			set { superficie = value; }
		}

		string rendita_euro=null;
		[DataField("RENDITA_EURO",Size=22, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string RENDITA_EURO
		{
			get { return rendita_euro; }
			set { rendita_euro = value; }
		}

		string lotto=null;
		[DataField("LOTTO",Size=2, Type=DbType.String, CaseSensitive=false)]
		public string LOTTO
		{
			get { return lotto; }
			set { lotto = value; }
		}

		string edificio=null;
		[DataField("EDIFICIO",Size=2, Type=DbType.String, CaseSensitive=false)]
		public string EDIFICIO
		{
			get { return edificio; }
			set { edificio = value; }
		}

        DateTime? d_efficacia_gen = null;
		[DataField("D_EFFICACIA_GEN", Type=DbType.DateTime)]
		public DateTime? D_EFFICACIA_GEN
		{
			get { return d_efficacia_gen; }
            set { d_efficacia_gen = VerificaDataLocale(value); }
		}

        DateTime? d_efficacia_fin = null;
		[DataField("D_EFFICACIA_FIN", Type=DbType.DateTime)]
		public DateTime? D_EFFICACIA_FIN
		{
			get { return d_efficacia_fin; }
            set { d_efficacia_fin = VerificaDataLocale(value); }
		}

        DateTime? d_iscrizione_gen = null;
		[DataField("D_ISCRIZIONE_GEN", Type=DbType.DateTime)]
		public DateTime? D_ISCRIZIONE_GEN
		{
			get { return d_iscrizione_gen; }
            set { d_iscrizione_gen = VerificaDataLocale(value); }
		}

        DateTime? d_iscrizione_fin = null;
		[DataField("D_ISCRIZIONE_FIN", Type=DbType.DateTime)]
		public DateTime? D_ISCRIZIONE_FIN
		{
			get { return d_iscrizione_fin; }
            set { d_iscrizione_fin = VerificaDataLocale(value); }
		}

		string numnota_fin=null;
		[DataField("NUMNOTA_FIN",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string NUMNOTA_FIN
		{
			get { return numnota_fin; }
			set { numnota_fin = value; }
		}

		string partita=null;
		[DataField("PARTITA",Size=7, Type=DbType.String, CaseSensitive=false)]
		public string PARTITA
		{
			get { return partita; }
			set { partita = value; }
		}

		string tipnota_fin=null;
		[DataField("TIPNOTA_FIN",Size=3, Type=DbType.String, CaseSensitive=false)]
		public string TIPNOTA_FIN
		{
			get { return tipnota_fin; }
			set { tipnota_fin = value; }
		}

		string annotazione=null;
		[DataField("ANNOTAZIONE",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string ANNOTAZIONE
		{
			get { return annotazione; }
			set { annotazione = value; }
		}

		string interno=null;
		[DataField("INTERNO",Size=3, Type=DbType.String, CaseSensitive=false)]
		public string INTERNO
		{
			get { return interno; }
			set { interno = value; }
		}

		string toponimo=null;
		[DataField("TOPONIMO",Size=3, Type=DbType.String, CaseSensitive=false)]
		public string TOPONIMO
		{
			get { return toponimo; }
			set { toponimo = value; }
		}

		string civico=null;
		[DataField("CIVICO",Size=6, Type=DbType.String, CaseSensitive=false)]
		public string CIVICO
		{
			get { return civico; }
			set { civico = value; }
		}

		string foglio_ult=null;
		[DataField("FOGLIO_ULT",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string FOGLIO_ULT
		{
			get { return foglio_ult; }
			set { foglio_ult = value; }
		}

		string numero_ult=null;
		[DataField("NUMERO_ULT",Size=5, Type=DbType.String, CaseSensitive=false)]
		public string NUMERO_ULT
		{
			get { return numero_ult; }
			set { numero_ult = value; }
		}

		string subalterno_ult=null;
		[DataField("SUBALTERNO_ULT",Size=4, Type=DbType.String, CaseSensitive=false)]
		public string SUBALTERNO_ULT
		{
			get { return subalterno_ult; }
			set { subalterno_ult = value; }
		}

		string anno=null;
		[DataField("ANNO",Size=4, Type=DbType.String, CaseSensitive=false)]
		public string ANNO
		{
			get { return anno; }
			set { anno = value; }
		}

		string variazione=null;
		[DataField("VARIAZIONE",Size=8, Type=DbType.String, CaseSensitive=false)]
		public string VARIAZIONE
		{
			get { return variazione; }
			set { variazione = value; }
		}

	}
}