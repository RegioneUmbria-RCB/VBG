//using System;
//using System.Collections.Generic;
//using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

//namespace Init.Sigepro.FrontEnd.AppLogic.Entities.Visura
//{
//	public class DatiSoggetto
//	{
//		public string Nominativo { get; internal set; }

//		public string Indirizzo { get; internal set; }

//		public string Localita { get; internal set; }

//		public string Citta { get; internal set; }

//		public string Provincia { get; internal set; }

//		public string Cap { get; internal set; }

//		public string CodiceFiscale { get; internal set; }

//		public string PartitaIva { get; internal set; }

//		public string TipoRapporto { get; internal set; }

//		public DatiSoggetto(Soggetto soggetto)
//		{
//			Nominativo = soggetto.Nominativo;
//			Indirizzo = soggetto.Indirizzo;
//			Localita = soggetto.Localita;
//			Citta = soggetto.Citta;
//			Provincia = soggetto.Provincia;
//			Cap = soggetto.Provincia;
//			CodiceFiscale = soggetto.CodFiscale;
//			PartitaIva = soggetto.PartitaIva;
//			TipoRapporto = soggetto.TipoRapporto;
//		}
//	}


//	public class DatiProcedimento
//	{
//		public string Procedimento { get; internal set; }

//		public string Amministrazione { get; internal set; }

//		internal DatiProcedimento(Procedimento proc)
//		{
//			Procedimento = proc.DesProcedimento;
//			Amministrazione = proc.Amministrazione;
//		}
//	}


//	public class DatiMovimento
//	{
//		public string Movimento { get; internal set; }

//		public string Data { get; internal set; }

//		public string Parere { get; internal set; }

//		public string Esito { get; internal set; }

//		public string Amministrazione { get; internal set; }

//		public string Note { get; internal set; }

//		internal DatiMovimento(Movimento mov)
//		{
//			Movimento = mov.Movimento1;
//			Data = String.IsNullOrEmpty(mov.DataMov) ? "" : mov.DataMov.Substring(0, 10);
//			Parere = mov.Parere;
//			Esito = mov.Esito;
//			Amministrazione = mov.Amministrazione;
//			Note = mov.Note;
//		}
//	}


//	public class DatiOnere
//	{
//		public string Onere { get; internal set; }

//		public string Importo { get; internal set; }

//		public string DataScadenza { get; internal set; }

//		public string DataPagamento { get; internal set; }

//		internal DatiOnere(Onere on)
//		{
//			Onere = on.DesOnere;
//			Importo = on.Importo;
//			DataScadenza = String.IsNullOrEmpty(on.DataScadenza) ? "" : on.DataScadenza.Substring(0, 10);
//			DataPagamento = String.IsNullOrEmpty(on.DataPagamento) ? "" : on.DataPagamento.Substring(0, 10);
//		}
//	}


//	public class DatiAltreInfo
//	{
//		public string Descrizione { get; internal set; }

//		public string Valore { get; internal set; }

//		internal DatiAltreInfo(AltreInfo inf)
//		{
//			Descrizione = inf.Dato;
//			Valore = inf.Valore;
//		}

//	}


//	public class DatiLocalizzazioni
//	{
//		public string Civico { get; internal set; }

//		public string Indirizzo { get; internal set; }

//		internal DatiLocalizzazioni(Localizzazione loc)
//		{
//			Indirizzo = loc.Indirizzo;
//			Civico = loc.Civico;
//		}
//	}


//	public class DatiCatastali
//	{
//		public string TipoCatasto { get; internal set; }

//		public string Foglio { get; internal set; }

//		public string Particella { get; internal set; }

//		public string Sub { get; internal set; }

//		internal DatiCatastali(RifCatastale dc)
//		{
//			TipoCatasto = dc.TipoCatasto;
//			Foglio = dc.Foglio;
//			Particella = dc.Particella;
//			Sub = dc.Subalterno;
//		}
//	}

//	public class DatiAutorizzazioni
//	{
//		public string DataRilascio { get; internal set; }

//		public string Tipologia { get; internal set; }

//		public string Note { get; internal set; }

//		public string Numero { get; internal set; }

//		internal DatiAutorizzazioni(Autorizzazione aut)
//		{
//			DataRilascio = aut.DataRilascio;
//			Tipologia = aut.Tipologia;
//			Note = aut.Note;
//			Numero = aut.Numero;
//		}
//	}


//	public class DatiDettaglioPratica
//	{
//		private List<DatiSoggetto> m_soggettiCollegati = new List<DatiSoggetto>();
//		private List<DatiProcedimento> m_procedimenti = new List<DatiProcedimento>();
//		private List<DatiMovimento> m_movimenti = new List<DatiMovimento>();
//		private List<DatiOnere> m_oneri = new List<DatiOnere>();
//		private List<DatiAltreInfo> m_altreInfo = new List<DatiAltreInfo>();
//		private List<DatiLocalizzazioni> m_localizzazioni = new List<DatiLocalizzazioni>();
//		private List<DatiCatastali> m_datiCatastali = new List<DatiCatastali>();
//		private List<DatiAutorizzazioni> m_autorizzazioni = new List<DatiAutorizzazioni>();

//		public string Oggetto { get; internal set; }

//		public string DescrizioneIntervento { get; internal set; }

//		public string DataPresentazione { get; internal set; }

//		public string StatoPratica { get; internal set; }

//		public string NumeroProtocollo { get; internal set; }

//		public string DataProtocollo { get; internal set; }

//		public string NumeroPratica { get; internal set; }

//		public List<DatiSoggetto> SoggettiCollegati { get; internal set; }

//		public List<DatiProcedimento> Procedimenti { get; internal set; }

//		public List<DatiMovimento> Movimenti { get; internal set; }

//		public List<DatiOnere> Oneri { get; internal set; }

//		public List<DatiAltreInfo> AltreInfo { get; internal set; }

//		public List<DatiLocalizzazioni> Localizzazioni { get; internal set; }

//		public List<DatiCatastali> DatiCatastali { get; internal set; }

//		public List<DatiAutorizzazioni> Autorizzazioni { get; internal set; }

//		public string Software { get; internal set; }

//		public string Istruttore { get; internal set; }

//		public string Operatore { get; internal set; }

//		public string ResponsabileProc { get; internal set; }

//		public DatiDettaglioPratica(DettagliPratica dett)
//		{
//			SoggettiCollegati = new List<DatiSoggetto>();
//			Procedimenti = new List<DatiProcedimento>();
//			Movimenti = new List<DatiMovimento>();
//			Oneri = new List<DatiOnere>();
//			AltreInfo = new List<DatiAltreInfo>();
//			Localizzazioni = new List<DatiLocalizzazioni>();
//			DatiCatastali = new List<DatiCatastali>();
//			Autorizzazioni = new List<DatiAutorizzazioni>();


//			Software = dett.DatiPratica.CodSportelloBack;
//			Oggetto = dett.DatiPratica.Oggetto;
//			DescrizioneIntervento = dett.DatiPratica.DescrizioneIntervento;
//			DataPresentazione = dett.DatiPratica.DataPresentazione;
//			StatoPratica = dett.DatiPratica.StatoPratica;

//			NumeroProtocollo = dett.DatiPratica.NumeroProtocollo;
//			DataProtocollo = dett.DatiPratica.DataProtocollo;

//			NumeroPratica = dett.DatiPratica.NumeroPratica;
//			Istruttore = dett.DatiPratica.Istruttore;
//			Operatore = dett.DatiPratica.Operatore;
//			ResponsabileProc = dett.DatiPratica.ResponsabileProcedimento;

//			if (dett.Soggetto != null)
//			{
//				foreach (Soggetto sogg in dett.Soggetto)
//					SoggettiCollegati.Add(new DatiSoggetto(sogg));
//			}

//			if (dett.Procedimento != null)
//			{
//				foreach (Procedimento proc in dett.Procedimento)
//					Procedimenti.Add(new DatiProcedimento(proc));
//			}


//			if (dett.Movimento != null)
//			{
//				foreach (Movimento mov in dett.Movimento)
//					Movimenti.Add(new DatiMovimento(mov));
//			}

//			if (dett.Onere != null)
//			{
//				foreach (Onere on in dett.Onere)
//					Oneri.Add(new DatiOnere(on));
//			}


//			if (dett.AltreInfo != null)
//			{
//				foreach (AltreInfo inf in dett.AltreInfo)
//					AltreInfo.Add(new DatiAltreInfo(inf));
//			}

//			if (dett.Localizzazione != null)
//			{
//				foreach (Localizzazione loc in dett.Localizzazione)
//					Localizzazioni.Add(new DatiLocalizzazioni(loc));
//			}

//			if (dett.RifCatastale != null)
//			{
//				foreach (RifCatastale rc in dett.RifCatastale)
//					DatiCatastali.Add(new DatiCatastali(rc));
//			}


//			if (dett.Autorizzazioni != null)
//			{
//				foreach (Autorizzazione aut in dett.Autorizzazioni)
//					Autorizzazioni.Add(new DatiAutorizzazioni(aut));
//			}


//		}
//	}
//}
