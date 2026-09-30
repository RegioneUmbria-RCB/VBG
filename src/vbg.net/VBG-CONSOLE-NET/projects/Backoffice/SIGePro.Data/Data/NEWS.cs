using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	[DataTable("NEWS")]
	public class News : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

		string codicenews=null;
		[KeyField("CODICENEWS", Type=DbType.Decimal)]
		public string CODICENEWS
		{
			get { return codicenews; }
			set { codicenews = value; }
		}

        DateTime? data = null;
		[DataField("DATA", Type=DbType.DateTime)]
		public DateTime? DATA
		{
			get { return data; }
            set { data = VerificaDataLocale(value); }
		}

		string titolo=null;
		[DataField("TITOLO",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string TITOLO
		{
			get { return titolo; }
			set { titolo = value; }
		}

		string _news=null;
		[DataField("NEWS",Size=4000, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string news
		{
			get { return _news; }
			set { _news = value; }
		}

		string imgtesta1=null;
		[DataField("IMGTESTA1",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string IMGTESTA1
		{
			get { return imgtesta1; }
			set { imgtesta1 = value; }
		}

		string imgfondo1=null;
		[DataField("IMGFONDO1",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string IMGFONDO1
		{
			get { return imgfondo1; }
			set { imgfondo1 = value; }
		}

		string imgfondo2=null;
		[DataField("IMGFONDO2",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string IMGFONDO2
		{
			get { return imgfondo2; }
			set { imgfondo2 = value; }
		}

		string imgfondo3=null;
		[DataField("IMGFONDO3",Size=50, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string IMGFONDO3
		{
			get { return imgfondo3; }
			set { imgfondo3 = value; }
		}

		string codiceoggetto1=null;
		[DataField("CODICEOGGETTO1", Type=DbType.Decimal)]
		public string CODICEOGGETTO1
		{
			get { return codiceoggetto1; }
			set { codiceoggetto1 = value; }
		}

		string codiceoggetto2=null;
		[DataField("CODICEOGGETTO2", Type=DbType.Decimal)]
		public string CODICEOGGETTO2
		{
			get { return codiceoggetto2; }
			set { codiceoggetto2 = value; }
		}

		string codiceoggetto3=null;
		[DataField("CODICEOGGETTO3", Type=DbType.Decimal)]
		public string CODICEOGGETTO3
		{
			get { return codiceoggetto3; }
			set { codiceoggetto3 = value; }
		}

		string codiceoggetto4=null;
		[DataField("CODICEOGGETTO4", Type=DbType.Decimal)]
		public string CODICEOGGETTO4
		{
			get { return codiceoggetto4; }
			set { codiceoggetto4 = value; }
		}

		string software=null;
		[DataField("SOFTWARE",Size=2, Type=DbType.String )]
		public string SOFTWARE
		{
			get { return software; }
			set { software = value; }
		}

	}
}