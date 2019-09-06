// -----------------------------------------------------------------------
// <copyright file="EstremiPagamento.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOneri
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

    public class EstremiPagamento
    {
        public readonly TipoPagamento TipoPagamento;
        public readonly DateTime? Data;
        public readonly string Riferimento;
        public readonly float ImportoPagato;

        public EstremiPagamento(TipoPagamento tipoPagamento, DateTime? data, string riferimento, float importoPagato)
        {
            this.TipoPagamento = tipoPagamento;
            this.Data = data;
            this.Riferimento = riferimento;
            this.ImportoPagato = importoPagato;
        }
    }
	/*
    public class EstremiPagamento
	{
        
		public int IdOnere { get; private set; }
		public ProvenienzaOnere Provenienza { get; private set; }
		public int CodiceInterventoOEndo { get; private set; }
		public TipoPagamento TipoPagamento { get; private set; }
		public DateTime? Data { get; private set; }
		public string Riferimento { get; private set; }
		public float ImportoPagato { get; private set; }

		public static EstremiPagamento CreaPerIntervento(int codiceIntervento, int idOnere, string codiceTipoPagamento, string tipoPagamento, DateTime? data, string riferimento, float importo)
		{
			return new EstremiPagamento(ProvenienzaOnere.Intervento, codiceIntervento, idOnere, codiceTipoPagamento, tipoPagamento, data, riferimento, importo);
		}

		public static EstremiPagamento CreaPerEndo(int codiceEndo, int idOnere, string codiceTipoPagamento, string tipoPagamento, DateTime? data, string riferimento, float importo)
		{
			return new EstremiPagamento(ProvenienzaOnere.Endo, codiceEndo, idOnere, codiceTipoPagamento, tipoPagamento, data, riferimento, importo);
		}

		protected EstremiPagamento(ProvenienzaOnere provenienza, int codInterventoOEndo, int idOnere, string codiceTipoPagamento, string tipoPagamento, DateTime? data, string riferimento, float importo)
		{
			this.Provenienza = provenienza;
			this.IdOnere = idOnere;
			this.TipoPagamento = new TipoPagamento(codiceTipoPagamento, tipoPagamento);
			this.CodiceInterventoOEndo = codInterventoOEndo;
			this.Data = data;
			this.Riferimento = riferimento;
			this.ImportoPagato = importo;
		}
         
	}*/
}
