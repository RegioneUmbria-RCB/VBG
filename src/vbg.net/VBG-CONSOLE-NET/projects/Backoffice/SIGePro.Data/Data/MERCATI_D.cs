using System.Data;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;
using System.Collections.Generic;

namespace Init.SIGePro.Data
{
	[DataTable("MERCATI_D")]
	public class Mercati_D : BaseDataClass
	{
		string idcomune=null;
		[KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
		public string IDCOMUNE
		{
			get { return idcomune; }
			set { idcomune = value; }
		}

        int? idposteggio = null;
		[useSequence]
		[KeyField("IDPOSTEGGIO", Type=DbType.Decimal)]
		public int? IDPOSTEGGIO
		{
			get { return idposteggio; }
			set { idposteggio = value; }
		}

        int? fkcodicemercato = null;
		[DataField("FKCODICEMERCATO", Type=DbType.Decimal)]
		public int? FKCODICEMERCATO
		{
			get { return fkcodicemercato; }
			set { fkcodicemercato = value; }
		}

		string codiceposteggio=null;
		[DataField("CODICEPOSTEGGIO",Size=50, Type=DbType.String, CaseSensitive=false)]
		public string CODICEPOSTEGGIO
		{
			get { return codiceposteggio; }
			set { codiceposteggio = value; }
		}

        double? larghezza = null;
		[DataField("LARGHEZZA", Type=DbType.Decimal)]
		public double? LARGHEZZA
		{
			get { return larghezza; }
			set { larghezza = value; }
		}

        double? lunghezza = null;
		[DataField("LUNGHEZZA", Type=DbType.Decimal)]
		public double? LUNGHEZZA
		{
			get { return lunghezza; }
			set { lunghezza = value; }
		}

        double? superficie = null;
		[DataField("SUPERFICIE", Type=DbType.Decimal)]
		public double? SUPERFICIE
		{
			get { return superficie; }
			set { superficie = value; }
		}

		string fkcodicetipospazio=null;
		[DataField("FKCODICETIPOSPAZIO", Type=DbType.Decimal)]
		public string FKCODICETIPOSPAZIO
		{
			get { return fkcodicetipospazio; }
			set { fkcodicetipospazio = value; }
		}

		string disabilitato=null;
		[DataField("DISABILITATO", Type=DbType.Decimal)]
		public string DISABILITATO
		{
			get { return disabilitato; }
			set { disabilitato = value; }
		}

		string fkcodicestradario=null;
		[DataField("FKCODICESTRADARIO", Type=DbType.Decimal)]
		public string FKCODICESTRADARIO
		{
			get { return fkcodicestradario; }
			set { fkcodicestradario = value; }
		}

        string note = null;
        [DataField("NOTE", Size = 1000, Type = DbType.String, CaseSensitive = false)]
        public string Note
        {
            get { return note; }
            set { note = value; }
        }

		#region Arraylist per gli inserimenti nelle tabelle collegate
        List<Mercati_DAttivitaIstat> _AttivitaIstat = new List<Mercati_DAttivitaIstat>();
        public List<Mercati_DAttivitaIstat> AttivitaIstat
		{
			get { return _AttivitaIstat; }
			set { _AttivitaIstat = value; }
		}

        List<Mercati_DConti> _Conti = new List<Mercati_DConti>();
        public List<Mercati_DConti> Conti
        {
            get { return _Conti; }
            set { _Conti = value; }
        }

		#endregion
	}
}