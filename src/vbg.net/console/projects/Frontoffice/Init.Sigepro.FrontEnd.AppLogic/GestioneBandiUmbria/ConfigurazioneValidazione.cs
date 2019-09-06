// -----------------------------------------------------------------------
// <copyright file="ConfigurazioneValidazione.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
	using System.IO;
	using System.Web;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class ConfigurazioneValidazione
	{
		public class ImpostazioniModello1
		{
			public string NomeCampoNominativoDittaCapofila { get; set; }
			public int NumeroMinimoImpreseCheRichiedonoContributo { get; set; }
			public string NomeTipoSoggettoAziendaRichiedenteContributo { get; set; }
			public string NomeTipoSoggettoAziendaCapofila { get; set; }
			public int PercentualePromozioneSuSpeseAmmissibili { get; set; }
			public string NomeCampoTotaleSpesePromozione { get; set; }
			public string NomeCampoTotaleSpeseAmmissibili { get; set; }
			public int ValoreMinSpeseAmmissibili { get; set; }
			public int ValoreMaxSpeseAmmissibili { get; set; }
			public int ValoreMinSpesaAmmissibileSingolaImpresa { get; set; }
			public int ValoreMaxSpesaAmmissibileSingolaImpresa { get; set; }
			public string NomeCampoNominativoDittaPartecipante { get; set; }
			public string NomeCampoSpesePromozione { get; set; }
			public string NomeCampoSpeseInvestimenti { get; set; }
		}

		public class ImpostazioniModello2
		{
			public string NomeCampoNominativoDittaCapofila { get; set; }
			public string NomeCampoNominativoDittaPartecipante { get; set; }
			public string NomeCampoSpesePerizieTecniche { get; set; }
			public string NomeCampoSpesePerInvestimenti { get; set; }
			public string NomeCampoSpeseNotarili { get; set; }
			public string NomeCampoSpeseFideiussioni { get; set; }
			public string NomeCampoSpeseDiPromozione { get; set; }
			public int PercentualeSpeseFidejussioni { get; set; }
			public int PercentualeValoreAnticipo { get; set; }
			public int PercentualeValoreContributo { get; set; }

			public int ImportoMassimoSpeseNotarili { get; set; }

			public int PercentualeSpeseTecnicheSuInvestimenti { get; set; }

			public string TitoloProgetto { get; set; }
			public string Acronimo { get; set; }
			public string TipologiaAggregazione { get; set; }
			public string DenominazioneCapofila { get; set; }
			public string DurataIniziativa { get; set; }
		}

		public class ImpostazioniModello3
		{
			public int ValoreMinSpeseAmmissibili { get; set; }
			public int ValoreMaxSpeseAmmissibili { get; set; }
			public string NomeCampoTotaleSpeseAmmissibili { get; set; }
			public string NomeCampoCategoriaPosseduta { get; set; }
			public string NomeCampoDenominazioneStruttura { get; set; }
		}

		public class ImpostazioniModello4
		{
			public string NomeCampoImportoSpeseTecniche { get; set; }
			public string NomeCampoImportoSpeseInvestimenti { get; set; }
			public string NomeCampoImportoSpeseArredamenti { get; set; }
			public string NomeCampoImportoSpeseFideiussioni { get; set; }
			public string NomeCampoImportoLavoriStrutturali { get; set; }
			public string NomeCampoObiettiviPropostaAggregazione { get; set; }
			public string NomeCampoSottocriterio1 { get; set; }
			public string NomeCampoSottocriterio2a { get; set; }
			public string NomeCampoSottocriterio2b { get; set; }
			public string NomeCampoSottocriterio2c { get; set; }
			public string NomeCampoSottocriterio2d { get; set; }

			public int PercentualeSpeseTecnicheSuInvestimenti { get; set; }
			public int PercentualeSpeseFidejussioni { get; set; }
			public int PercentualeValoreAnticipo { get; set; }
			public int PercentualeValoreContributo { get; set; }
		}

		public ImpostazioniModello1 Modello1 { get; set; }
		public ImpostazioniModello2 Modello2 { get; set; }
		public ImpostazioniModello3 Modello3 { get; set; }
		public ImpostazioniModello4 Modello4 { get; set; }

		public ConfigurazioneValidazione()
		{
			Modello1 = new ImpostazioniModello1();
			Modello2 = new ImpostazioniModello2();
			Modello3 = new ImpostazioniModello3();
			Modello4 = new ImpostazioniModello4();
		}

		public static ConfigurazioneValidazione Load(string path)
		{
			if (HttpContext.Current != null)
			{
				path = HttpContext.Current.Server.MapPath(path);
			}

			var config = File.ReadAllText(path);

			return config.DeserializeXML<ConfigurazioneValidazione>();
		}

		public void Save(string path)
		{
			if (HttpContext.Current != null)
			{
				path = HttpContext.Current.Server.MapPath(path);
			}

			File.WriteAllBytes(path, this.ToXmlByteArray());
		}

		public static void TestDefault()
		{
			var cfg = new ConfigurazioneValidazione();

			cfg.Modello1 = new ImpostazioniModello1
			{
				NomeTipoSoggettoAziendaRichiedenteContributo = "Azienda richiedente contributo",
				NomeCampoNominativoDittaPartecipante = "NOME_DITTA_[n]",
				NomeCampoSpeseInvestimenti = "Spesa_prevista_investimenti[n]",
				NomeCampoSpesePromozione = "Spesa_prevista_promozione[n]",
				NomeCampoTotaleSpeseAmmissibili = "Importo_totale_spesa_ammissibile_contributo",
				NomeCampoTotaleSpesePromozione = "Spesa_prevista_promozione_totale",
				NumeroMinimoImpreseCheRichiedonoContributo = 6,
				PercentualePromozioneSuSpeseAmmissibili = 10,
				ValoreMaxSpeseAmmissibili = 600000,
				ValoreMinSpeseAmmissibili = 300000,
				ValoreMaxSpesaAmmissibileSingolaImpresa = 90000,
				ValoreMinSpesaAmmissibileSingolaImpresa = 50000
			};

			cfg.Modello2 = new ImpostazioniModello2
			{
				NomeCampoNominativoDittaPartecipante = "NOME_DITTA_[n]",
				ImportoMassimoSpeseNotarili = 2000,
				NomeCampoSpeseDiPromozione = "Spesa_prevista_promozione_[n]",
				NomeCampoSpeseFideiussioni = "Spese_fideiussioni_[n]",
				NomeCampoSpeseNotarili = "Spese_notarili_promozione_[n]",
				NomeCampoSpesePerInvestimenti = "Spesa_prevista_investimenti_[n]",
				NomeCampoSpesePerizieTecniche = "Spesa_perizie_investimenti_[n]",
				PercentualeSpeseFidejussioni = 2,
				PercentualeSpeseTecnicheSuInvestimenti = 8,
				PercentualeValoreAnticipo = 50,
				PercentualeValoreContributo = 50
			};

			cfg.Save("c:\\temp\\configurazioneBandi.xml");
		}
	}
}
