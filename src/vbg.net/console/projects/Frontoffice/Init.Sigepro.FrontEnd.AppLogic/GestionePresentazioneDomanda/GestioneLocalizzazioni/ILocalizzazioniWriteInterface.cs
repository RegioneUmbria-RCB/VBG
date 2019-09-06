using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni
{
	public class NuovaLocalizzazione
	{
		public int CodiceStradario { get; private set; }
		public string Indirizzo { get; private set; }
		public string Civico { get; private set; }
		public string Esponente { get; set; }
		public string Colore { get; set; }
		public string Scala { get; set; }
		public string Interno { get; set; }
		public string EsponenteInterno { get; set; }
		public string Piano { get; set; }
		public string Note { get; set; }
		public string Fabbricato { get; set; }
		public string Km { get; set; }
		public string Circoscrizione { get; set; }
		public string Cap { get; set; }
		public string Longitudine { get; set; }
		public string Latitudine { get; set; }
		public string TipoLocalizzazione { get; set; }
		public string CodiceCivico { get; set; }
		public string CodiceViario { get; set; }
		

		public NuovaLocalizzazione(int codiceStradario, string indirizzo, string civico, string esponente = "")
		{
			this.CodiceStradario = codiceStradario;
			this.Indirizzo = indirizzo;
			this.Civico = civico;

            this.Esponente = esponente;
			this.Colore = String.Empty;
			this.Scala = String.Empty;
			this.Interno = String.Empty;
			this.EsponenteInterno = String.Empty;
			this.Piano = String.Empty;
			this.Note = String.Empty;
			this.Fabbricato = String.Empty;
			this.Km = String.Empty;
			this.Circoscrizione = String.Empty;
			this.Cap = String.Empty;
			this.CodiceCivico = String.Empty;
			this.CodiceViario = String.Empty;
		}

        public NuovaLocalizzazione(StradarioDto stradarioDto, string civico, string esponente = "")
            :this(stradarioDto.CodiceStradario, stradarioDto.NomeVia, civico, esponente)
        {   
        }
	}

	public class NuovoRiferimentoCatastale
	{
		public string CodiceTipoCatasto { get; set; }
		public string TipoCatasto { get; set; }
        public string Sezione { get; set; }
		public string Foglio { get; set; }
		public string Particella { get; set; }
		public string Sub { get; set; }

        public NuovoRiferimentoCatastale(string codTipoCatasto, string tipoCatasto, string foglio, string particella, string sub = "", string sezione = "")
		{
            this.Sezione = sezione;
			this.CodiceTipoCatasto = codTipoCatasto;
			this.TipoCatasto = tipoCatasto;
			this.Foglio = foglio;
			this.Particella = particella;
			this.Sub = sub;
		}
	}
	public interface ILocalizzazioniWriteInterface
	{
		void EliminaRiferimentoCatastale(int idRiferimentoCatastale);
		void AssegnaRiferimentiCatastaliALocalizzazione(int idLocalizzazione, NuovoRiferimentoCatastale riferimentoCatastale);
		void EliminaLocalizzazione(int idLocalizzazione);
		void AggiungiLocalizzazione(NuovaLocalizzazione localizzazione);
		void AggiungiLocalizzazioneConRiferimentiCatastali(NuovaLocalizzazione localizzazione, NuovoRiferimentoCatastale riferimentoCatastale);
	}
}
