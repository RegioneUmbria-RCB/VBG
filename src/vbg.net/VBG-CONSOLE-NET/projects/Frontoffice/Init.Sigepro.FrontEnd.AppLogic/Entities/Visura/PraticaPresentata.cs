//using System.Collections.Generic;
//using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
//using System;
//using System.Text;

//namespace Init.Sigepro.FrontEnd.AppLogic.Entities.Visura
//{
//	/// <summary>
//	/// Descrizione di riepilogo per PraticaPresentata.
//	/// </summary>
//	[Serializable]
//	public class PraticaPresentata
//	{
//		public string Civico { get;  set; }

//		public string CodiceArea { get; set; }

//		public string NumeroIstanza { get; set; }

//		public string DataPresentazione { get;  set; }

//		public string DataProtocollo { get;  set; }

//		public string Localizzazione { get;  set; }

//		public string NumeroProtocollo { get;  set; }

//		public string Oggetto { get;  set; }

//		public string Operatore { get; set; }

//		public string Foglio { get; set; }

//		public string Particella { get; set; }

//		public string Subalterno { get; set; }

//		public string Progressivo { get; set; }

//		public string Richiedente { get; set; }

//		public string Stato { get; set; }

//		public string TipoIntervento { get; set; }

//		public string TipoCatasto { get; set; }

//		public string TipoProcedura { get; set; }

//		public string IdComune { get; set; }

//		public string Software { get; set; }

//		public string IdPratica { get; set; }
        
//        public string Azienda { get; set; }
//        public string Uuid { get; set; }

//        public string LocalizzazioneConCivico
//        {
//            get
//            {
//                var sb = new StringBuilder(this.Localizzazione);

//                if (!String.IsNullOrEmpty(this.Civico))
//                {
//                    sb.AppendFormat(" {0}", this.Civico);
//                }

//                return sb.ToString();
//            }
//        }


//		public PraticaPresentata()
//		{
//		}

//		public static List<PraticaPresentata> CreaLista(ListaPratiche listaPratiche)
//		{
//			var rVal = new List<PraticaPresentata>();

//			if (listaPratiche.Pratiche == null) return rVal;

//			for (int i = 0; i < listaPratiche.Pratiche.Length; i++)
//			{
//				IdentificativoPratica idPratica = listaPratiche.Pratiche[i];

//				var pr = new PraticaPresentata
//				{
//					Civico = idPratica.Localizzazione.Civico,
//					CodiceArea = idPratica.DatiPratica.Zonizzazione,
//					NumeroIstanza = idPratica.DatiPratica.NumeroPratica,
//					DataPresentazione = idPratica.DatiPratica.DataPresentazione,
//					DataProtocollo = idPratica.DatiPratica.DataProtocollo,
//					IdComune = listaPratiche.EnteTitolare.Ente.CodEnte,
//					IdPratica = idPratica.DatiPratica.IdPratica,
//					Localizzazione = idPratica.Localizzazione.Indirizzo,
//					NumeroProtocollo = idPratica.DatiPratica.NumeroProtocollo,
//					Oggetto = idPratica.DatiPratica.Oggetto,
//					Operatore = idPratica.DatiPratica.ResponsabileProcedimento,
//					Progressivo = (i + 1).ToString(),
//					Richiedente = idPratica.Soggetto.Nominativo,
//					Software = listaPratiche.EnteTitolare.Sportello.CodSportello,
//					Stato = idPratica.DatiPratica.StatoPratica,
//					TipoCatasto = idPratica.RifCatastale.TipoCatasto,
//					Foglio = idPratica.RifCatastale.Foglio,
//					Particella = idPratica.RifCatastale.Particella,
//					Subalterno = idPratica.RifCatastale.Subalterno,
//					TipoIntervento = idPratica.DatiPratica.DescrizioneIntervento,
//					TipoProcedura = idPratica.DatiPratica.DescrizioneProcedura,
//                    Azienda = idPratica.Azienda.Nominativo
//				};

//				rVal.Add(pr);
//			}

//			return rVal;
//		}
//	}
//}
