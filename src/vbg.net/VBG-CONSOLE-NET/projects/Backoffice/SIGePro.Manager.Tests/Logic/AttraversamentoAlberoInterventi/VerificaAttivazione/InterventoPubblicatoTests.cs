using System;
using System.Collections.Generic;
using Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi;
using Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione;
using Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaPubblicazione;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace SIGePro.Manager.Tests.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione
{
	
	public class InterventoPubblicatoTests
	{
		[TestClass]
		public class PubblicazioneFrontoffice
		{
			[TestMethod]
			public void True_se_intervento_ha_flag_pubblica()
			{
				var list = new List<IIntervento>();

				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.Frontoffice });

				var test = new InterventoPubblicatoNelFrontoffice(list);

				var result = test.IsTrue();

				Assert.IsTrue(result);
			}

			[TestMethod]
			public void True_se_intervento_ha_flag_pubblica_area_riservata_e_frontoffice()
			{
				var list = new List<IIntervento>();

				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.AreaRiservataEFrontoffice });

				var test = new InterventoPubblicatoNelFrontoffice(list);

				var result = test.IsTrue();

				Assert.IsTrue(result);
			}

			[TestMethod]
			public void False_se_intervento_ha_flag_non_pubblicare()
			{
				var list = new List<IIntervento>();

				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.NonPubblicare });

				var test = new InterventoPubblicatoNelFrontoffice(list);

				var result = test.IsTrue();

				Assert.IsFalse(result);
			}

			[TestMethod]
			public void True_se_intervento_eredita_e_almeno_un_intervento_padre_ha_flag_pubblica()
			{
				var list = new List<IIntervento>();

				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.Frontoffice });
				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.EreditaDalPadre });
				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.EreditaDalPadre });

				var test = new InterventoPubblicatoNelFrontoffice(list);

				var result = test.IsTrue();

				Assert.IsTrue(result);
			}

			[TestMethod]
			public void True_se_intervento_eredita_e_almeno_un_intervento_padre_ha_flag_pubblica_area_riservata_e_frontoffice()
			{
				var list = new List<IIntervento>();

				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.AreaRiservataEFrontoffice });
				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.EreditaDalPadre });
				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.EreditaDalPadre });

				var test = new InterventoPubblicatoNelFrontoffice(list);

				var result = test.IsTrue();

				Assert.IsTrue(result);
			}

			[TestMethod]
			public void False_se_intervento_eredita_e_almeno_un_intervento_padre_ha_flag_non_pubblicare()
			{
				var list = new List<IIntervento>();

				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.NonPubblicare });
				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.EreditaDalPadre });
				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.EreditaDalPadre });

				var test = new InterventoPubblicatoNelFrontoffice(list);

				var result = test.IsTrue();

				Assert.IsFalse(result);
			}

			[TestMethod]
			public void False_se_intervento_eredita_e_almeno_un_intervento_padre_ha_flag_pubblica_su_area_riservata()
			{
				var list = new List<IIntervento>();

				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.AreaRiservata });
				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.EreditaDalPadre });
				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.EreditaDalPadre });

				var test = new InterventoPubblicatoNelFrontoffice(list);

				var result = test.IsTrue();

				Assert.IsFalse(result);
			}
		}




		[TestClass]
		public class AreaRiservata
		{
			[TestMethod]
			public void True_se_intervento_ha_flag_pubblica()
			{
				var list = new List<IIntervento>();

				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.AreaRiservata });

				var test = new InterventoPubblicatoNellAreaRiservata(list);

				var result = test.IsTrue();

				Assert.IsTrue(result);
			}

			[TestMethod]
			public void True_se_intervento_ha_flag_pubblica_area_riservata_e_frontoffice()
			{
				var list = new List<IIntervento>();

				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.AreaRiservataEFrontoffice });

				var test = new InterventoPubblicatoNellAreaRiservata(list);

				var result = test.IsTrue();

				Assert.IsTrue(result);
			}

			[TestMethod]
			public void False_se_intervento_ha_flag_non_pubblicare()
			{
				var list = new List<IIntervento>();

				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.NonPubblicare });

				var test = new InterventoPubblicatoNellAreaRiservata(list);

				var result = test.IsTrue();

				Assert.IsFalse(result);
			}

			[TestMethod]
			public void True_se_intervento_eredita_e_almeno_un_intervento_padre_ha_flag_pubblica()
			{
				var list = new List<IIntervento>();

				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.AreaRiservata });
				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.EreditaDalPadre });
				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.EreditaDalPadre });

				var test = new InterventoPubblicatoNellAreaRiservata(list);

				var result = test.IsTrue();

				Assert.IsTrue(result);
			}

			[TestMethod]
			public void True_se_intervento_eredita_e_almeno_un_intervento_padre_ha_flag_pubblica_area_riservata_e_frontoffice()
			{
				var list = new List<IIntervento>();

				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.AreaRiservataEFrontoffice });
				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.EreditaDalPadre });
				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.EreditaDalPadre });

				var test = new InterventoPubblicatoNellAreaRiservata(list);

				var result = test.IsTrue();

				Assert.IsTrue(result);
			}

			[TestMethod]
			public void False_se_intervento_eredita_e_almeno_un_intervento_padre_ha_flag_non_pubblicare()
			{
				var list = new List<IIntervento>();

				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.NonPubblicare });
				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.EreditaDalPadre });
				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.EreditaDalPadre });

				var test = new InterventoPubblicatoNellAreaRiservata(list);

				var result = test.IsTrue();

				Assert.IsFalse(result);
			}

			[TestMethod]
			public void False_se_intervento_eredita_e_almeno_un_intervento_padre_ha_flag_pubblica_su_frontoffice()
			{
				var list = new List<IIntervento>();

				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.Frontoffice });
				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.EreditaDalPadre });
				list.Add(new InterventoStub { Pubblica = FlagPubblicazione.EreditaDalPadre });

				var test = new InterventoPubblicatoNellAreaRiservata(list);

				var result = test.IsTrue();

				Assert.IsFalse(result);
			}
		}


	}
}
