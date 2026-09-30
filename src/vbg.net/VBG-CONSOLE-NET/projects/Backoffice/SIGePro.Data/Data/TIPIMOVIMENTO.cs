using System;
using System.Data;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	/*[DataTable("TIPIMOVIMENTO")]
	[Serializable]*/
	public partial class TipiMovimento : BaseDataClass
	{
		/*
		#region Key Fields

		string tipomovimento=null;
		[KeyField("TIPOMOVIMENTO",Size=6, Type=DbType.String, CaseSensitive=false)]
        public string Tipomovimento
		{
			get { return tipomovimento; }
			set { tipomovimento = value; }
		}

		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
        public string Idcomune
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		#endregion

		string movimento=null;
		[isRequired]
		[DataField("MOVIMENTO",Size=128, Type=DbType.String, CaseSensitive=false)]
		public string MOVIMENTO
		{
			get { return movimento; }
			set { movimento = value; }
		}

		string sistema=null;
		[isRequired]
		[DataField("SISTEMA", Type=DbType.Decimal)]
		public string SISTEMA
		{
			get { return sistema; }
			set { sistema = value; }
		}

		string codicelettera=null;
		[DataField("CODICELETTERA", Type=DbType.Decimal)]
		public string CODICELETTERA
		{
			get { return codicelettera; }
			set { codicelettera = value; }
		}

		string flag_richiestaintegrazione=null;
		[isRequired]
		[DataField("FLAG_RICHIESTAINTEGRAZIONE", Type=DbType.Decimal)]
		public string FLAG_RICHIESTAINTEGRAZIONE
		{
			get { return flag_richiestaintegrazione; }
			set { flag_richiestaintegrazione = value; }
		}

		string flag_interruzione=null;
		[isRequired]
		[DataField("FLAG_INTERRUZIONE", Type=DbType.Decimal)]
		public string FLAG_INTERRUZIONE
		{
			get { return flag_interruzione; }
			set { flag_interruzione = value; }
		}

		string tutteleamministrazioni=null;
		[isRequired]
		[DataField("TUTTELEAMMINISTRAZIONI", Type=DbType.Decimal)]
		public string TUTTELEAMMINISTRAZIONI
		{
			get { return tutteleamministrazioni; }
			set { tutteleamministrazioni = value; }
		}

		string tipologiaesito=null;
		[isRequired]
		[DataField("TIPOLOGIAESITO", Type=DbType.Decimal)]
		public string TIPOLOGIAESITO
		{
			get { return tipologiaesito; }
			set { tipologiaesito = value; }
		}

		string flag_proroga=null;
		[isRequired]
		[DataField("FLAG_PROROGA", Type=DbType.Decimal)]
		public string FLAG_PROROGA
		{
			get { return flag_proroga; }
			set { flag_proroga = value; }
		}

		string ggproroga=null;
		[isRequired]
		[DataField("GGPROROGA", Type=DbType.Decimal)]
		public string GGPROROGA
		{
			get { return ggproroga; }
			set { ggproroga = value; }
		}

		string flag_enmail=null;
		[isRequired]
		[DataField("FLAG_ENMAIL", Type=DbType.Decimal)]
		public string FLAG_ENMAIL
		{
			get { return flag_enmail; }
			set { flag_enmail = value; }
		}

		string flag_enmostra=null;
		[isRequired]
		[DataField("FLAG_ENMOSTRA", Type=DbType.Decimal)]
		public string FLAG_ENMOSTRA
		{
			get { return flag_enmostra; }
			set { flag_enmostra = value; }
		}

		string software=null;
		[isRequired]
		[DataField("SOFTWARE",Size=2, Type=DbType.String)]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

		string flag_operante=null;
		[isRequired]
		[DataField("FLAG_OPERANTE", Type=DbType.Decimal)]
		public string FLAG_OPERANTE
		{
			get { return flag_operante; }
			set { flag_operante = value; }
		}

		string flag_nonoperante=null;
		[isRequired]
		[DataField("FLAG_NONOPERANTE", Type=DbType.Decimal)]
		public string FLAG_NONOPERANTE
		{
			get { return flag_nonoperante; }
			set { flag_nonoperante = value; }
		}

		string flag_cds=null;
		[isRequired]
		[DataField("FLAG_CDS", Type=DbType.Decimal)]
		public string FLAG_CDS
		{
			get { return flag_cds; }
			set { flag_cds = value; }
		}

		string flag_registro=null;
		[isRequired]
		[DataField("FLAG_REGISTRO", Type=DbType.Decimal)]
		public string FLAG_REGISTRO
		{
			get { return flag_registro; }
			set { flag_registro = value; }
		}

		string fkidregistro=null;
		[DataField("FKIDREGISTRO", Type=DbType.Decimal)]
		public string FKIDREGISTRO
		{
			get { return fkidregistro; }
			set { fkidregistro = value; }
		}

		string flag_noamminterna=null;
		[isRequired]
		[DataField("FLAG_NOAMMINTERNA", Type=DbType.Decimal)]
		public string FLAG_NOAMMINTERNA
		{
			get { return flag_noamminterna; }
			set { flag_noamminterna = value; }
		}

		string flag_usadalprotocollo=null;
		[isRequired]
		[DataField("FLAG_USADALPROTOCOLLO", Type=DbType.Decimal)]
		public string FLAG_USADALPROTOCOLLO
		{
			get { return flag_usadalprotocollo; }
			set { flag_usadalprotocollo = value; }
		}

		string flag_pubblicamovimento=null;
		[isRequired]
		[DataField("FLAG_PUBBLICAMOVIMENTO", Type=DbType.Decimal)]
		public string FLAG_PUBBLICAMOVIMENTO
		{
			get { return flag_pubblicamovimento; }
			set { flag_pubblicamovimento = value; }
		}

		string flag_pubblicaparere=null;
		[isRequired]
		[DataField("FLAG_PUBBLICAPARERE", Type=DbType.Decimal)]
		public string FLAG_PUBBLICAPARERE
		{
			get { return flag_pubblicaparere; }
			set { flag_pubblicaparere = value; }
		}
		*/
	}
}