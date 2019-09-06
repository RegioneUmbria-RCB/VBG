using System.Data;
using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;
using Init.SIGePro.Attributes;
using System;

namespace Init.SIGePro.Data
{
	[DataTable("MERCATI_USO")]
    [Serializable]
	public class Mercati_Uso : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IdComune
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

        int? id = null;
		[useSequence]
		[KeyField("ID", Type=DbType.Decimal)]
        public int? Id
		{
			get { return id; }
			set { id = value; }
		}

        int? fkcodicemercato = null;
		[DataField("FKCODICEMERCATO", Type=DbType.Decimal)]
        public int? FkCodiceMercato
		{
			get { return fkcodicemercato; }
			set { fkcodicemercato = value; }
		}

		string fkgsid=null;
		[DataField("FKGSID", Type=DbType.Decimal)]
		public string FkGsId
		{
			get { return fkgsid; }
			set { fkgsid = value; }
		}

		string fkcodiceuso=null;
		[DataField("FKCODICEUSO", Type=DbType.Decimal)]
		public string FkCodiceUso
		{
			get { return fkcodiceuso; }
			set { fkcodiceuso = value; }
		}

		string descrizione=null;
		[DataField("DESCRIZIONE",Size=30, Type=DbType.String, Compare="like", CaseSensitive=false)]
		public string Descrizione
		{
			get { return descrizione; }
			set { descrizione = value; }
		}

		string peso=null;
		[DataField("PESO", Type=DbType.Decimal)]
		public string Peso
		{
			get { return peso; }
			set { peso = value; }
		}

        public override string ToString()
        {
            return Descrizione.ToString();
        }
	}
}