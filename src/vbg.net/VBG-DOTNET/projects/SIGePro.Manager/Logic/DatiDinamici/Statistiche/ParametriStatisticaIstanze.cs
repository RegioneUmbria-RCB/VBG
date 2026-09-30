using System;
using System.Collections.Generic;
using System.Text;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.Statistiche
{
	public partial class ParametriStatisticaIstanze : ParametriStatisticaBase
	{


		ParametriStatisticaBase.Intervallo<DateTime?> m_data = new ParametriStatisticaBase.Intervallo<DateTime?>();

		public ParametriStatisticaBase.Intervallo<DateTime?> Data
		{
			get { return m_data; }
			set { m_data = value; }
		}

		int? m_operatore = null;

		public int? Operatore
		{
			get { return m_operatore; }
			set { m_operatore = value; }
		}


		int? m_richiedente = null;

		public int? Richiedente
		{
			get { return m_richiedente; }
			set { m_richiedente = value; }
		}


		int? m_tecnico = null;

		public int? Tecnico
		{
			get { return m_tecnico; }
			set { m_tecnico = value; }
		}


		int? m_tipologiaIntervento = null;

		public int? TipologiaIntervento
		{
			get { return m_tipologiaIntervento; }
			set { m_tipologiaIntervento = value; }
		}


		int? m_tipoProcedura = null;

		public int? TipoProcedura
		{
			get { return m_tipoProcedura; }
			set { m_tipoProcedura = value; }
		}


		int? m_zonizzazione = null;

		public int? Zonizzazione
		{
			get { return m_zonizzazione; }
			set { m_zonizzazione = value; }
		}


		string m_tipoInformazione = null;

		public string TipoInformazione
		{
			get { return m_tipoInformazione; }
			set { m_tipoInformazione = value; }
		}


		string m_dettaglioInformazione = null;

		public string DettaglioInformazione
		{
			get { return m_dettaglioInformazione; }
			set { m_dettaglioInformazione = value; }
		}


		int? m_registro = null;

		public int? Registro
		{
			get { return m_registro; }
			set { m_registro = value; }
		}

		/*
		ParametriStatisticaBase.Intervallo<int?> m_metriQuadri = new ParametriStatisticaBase.Intervallo<int?>();

		public ParametriStatisticaBase.Intervallo<int?> MetriQuadri
		{
			get { return m_metriQuadri; }
			set { m_metriQuadri = value; }
		}


		bool m_conteggioMq = false;

		public bool ConteggioMq
		{
			get { return m_conteggioMq; }
			set { m_conteggioMq = value; }
		}
		*/

		string m_codiceStato = String.Empty;

		public string CodiceStato
		{
			get { return m_codiceStato; }
			set { m_codiceStato = value; }
		}




	}
}
