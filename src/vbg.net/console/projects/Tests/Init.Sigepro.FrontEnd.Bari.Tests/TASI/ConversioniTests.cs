// -----------------------------------------------------------------------
// <copyright file="ConversioneDatiCatastaliTypeToDatiCatastaliTasiDtoTest.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.Tests.TASI
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.Bari.TASI.wsdl;
	using Init.Sigepro.FrontEnd.Bari.TASI;
	using AutoMapper;
	using Init.Sigepro.FrontEnd.Bari.TASI.DTOs;
	using Init.Sigepro.FrontEnd.Bari.TASI.DTOs.Converters;
	using Init.Sigepro.FrontEnd.Bari.Tests.TASI.TasiServiceCalls;

	[TestClass]
	public class ConversioniTests
	{
		datiCatastaliType datiCatastaliSrc;
		indirizzoImmobileType indirizzoSrc;
		datiImmobileResponseType datiImmobileSrc;
		datiIndirizzoType datiIndirizzoSrcNonCodificato;
		datiImmobiliResponseType datiImmobiliResponsePfSrc;
		datiImmobiliResponseType datiImmobiliResponsePgSrc;

		[TestInitialize]
		public void Initialize()
		{
			datiCatastaliSrc = new datiCatastaliType
			{
				categoriaCatastale = categoriaCatastaleType.A02,
				foglio = "Foglio",
				particella = "Particella",
				sezione = "Sezione",
				subalterno = "sub"
			};

			indirizzoSrc = new indirizzoImmobileType
			{
				civico = 1,
				civicoSpecified = true,
				esponente = "Esponente",
				interno = "interno",
				Item = "Nome via",
				ItemElementName = ItemChoiceType2.viaNonCodificata,
				palazzina = "palazzina",
				piano = "piano",
				scala = "scala"
			};

			datiImmobileSrc = new datiImmobileResponseType
			{
				dataInizioDecorrenza = "01/01/2011",
				datiCatastali = datiCatastaliSrc,
				idImmobile = "id immobile",
				percentualePossesso = 20.5m,
				ubicazione = indirizzoSrc
			};

			datiIndirizzoSrcNonCodificato = new datiIndirizzoType
			{
				cap = "cap",
				esponente = "esponente",
				interno = "interno",
				Item = "via",
				ItemElementName = ItemChoiceType1.viaNonCodificata,
				numeroCivico = 1,
				numeroCivicoSpecified = true,
				palazzina = "palazzina",
				piano = "piano",
				scala = "scala",
				suffisso = "suffisso"
			};

			datiImmobiliResponsePfSrc = new datiImmobiliResponseType
			{
				codContribuente = "cod contribuente",
				datiResidenzaContribuente = datiIndirizzoSrcNonCodificato,
				elencoImmobili = new datiImmobileResponseType[] { datiImmobileSrc, datiImmobileSrc },
				idContribuente = "id contribuente",
				Item = new datiAnagraficiPersonaFisicaType
				{
					codiceFiscale = "codiceFiscale",
					cognome = "cognome",
					comuneEsteroNascita = "comuneEsteroNascita",
					comuneNascita = "comuneNascita",
					dataNascita = "01/01/2014",
					nome = "nome",
					provinciaNascita = "provinciaNascita",
					sesso = sessoType.M,
					sessoSpecified = true
				}
			};

			datiImmobiliResponsePgSrc = new datiImmobiliResponseType
			{
				codContribuente = "cod contribuente",
				datiResidenzaContribuente = datiIndirizzoSrcNonCodificato,
				elencoImmobili = new datiImmobileResponseType[] { datiImmobileSrc, datiImmobileSrc },
				idContribuente = "id contribuente",
				Item = new datiAnagraficiPersonaGiuridicaType
				{
					denominazione = "denominazione",
					partitaIva = "partitaiva"
				}
			};
		}

		[TestMethod]
		public void Conversione_da_datiCatastaliType_a_DatiCatastaliTasiDto()
		{
			var mapper = new DatiCatastaliTypeToDatiCatastaliDto();

			var c = mapper.Map(datiCatastaliSrc);

			Assert.AreEqual<string>(datiCatastaliSrc.categoriaCatastale.ToString(), c.CategoriaCatastale);
			Assert.AreEqual<string>(datiCatastaliSrc.foglio, c.Foglio);
			Assert.AreEqual<string>(datiCatastaliSrc.particella, c.Particella);
			Assert.AreEqual<string>(datiCatastaliSrc.sezione, c.Sezione);
			Assert.AreEqual<string>(datiCatastaliSrc.subalterno, c.Subalterno);
		}

		[TestMethod]
		public void Conversione_da_indirizzoImmobileType_a_IndirizzoTasiDto_con_civico_specificato()
		{
			var mapper = new IndirizzoImmobileTypeToIndirizzoTasiDto(new MockViaResolver());

			var i = mapper.Map(indirizzoSrc);

			Assert.AreEqual<string>(indirizzoSrc.civico.ToString(), i.Civico);
			Assert.AreEqual<string>(indirizzoSrc.esponente, i.Esponente);
			Assert.AreEqual<string>(indirizzoSrc.interno, i.Interno);
			Assert.AreEqual<string>(indirizzoSrc.Item, i.Via);
			Assert.AreEqual<string>(indirizzoSrc.palazzina, i.Palazzina);
			Assert.AreEqual<string>(indirizzoSrc.piano, i.Piano);
			Assert.AreEqual<string>(indirizzoSrc.scala, i.Scala);
		}

		[TestMethod]
		public void Conversione_da_indirizzoImmobileType_a_IndirizzoTasiDto_con_civico_non_specificato()
		{
			indirizzoSrc.civico = 0;
			indirizzoSrc.civicoSpecified = false;

			var mapper = new IndirizzoImmobileTypeToIndirizzoTasiDto(new MockViaResolver());

			var i = mapper.Map(indirizzoSrc);

			Assert.AreEqual<string>(String.Empty, i.Civico);
		}

		[TestMethod]
		public void Conversione_da_datiImmobileResponseType_a_ImmobileTasiDto()
		{
			var mapper = new DatiImmobileMapper();
			var d = mapper.Map(datiImmobileSrc);

			Assert.AreEqual<string>(datiImmobileSrc.dataInizioDecorrenza, d.DataInizioDecorrenza);
			Assert.AreEqual<string>(datiImmobileSrc.idImmobile, d.IdImmobile);
			Assert.AreEqual<string>("20.5", d.PercentualePossesso);

			Assert.AreEqual<string>(datiImmobileSrc.ubicazione.civico.ToString(), d.Ubicazione.Civico);
			Assert.AreEqual<string>(datiImmobileSrc.ubicazione.esponente, d.Ubicazione.Esponente);
			Assert.AreEqual<string>(datiImmobileSrc.ubicazione.interno, d.Ubicazione.Interno);
			Assert.AreEqual<string>(datiImmobileSrc.ubicazione.Item, d.Ubicazione.Via);
			Assert.AreEqual<string>(datiImmobileSrc.ubicazione.palazzina, d.Ubicazione.Palazzina);
			Assert.AreEqual<string>(datiImmobileSrc.ubicazione.piano, d.Ubicazione.Piano);
			Assert.AreEqual<string>(datiImmobileSrc.ubicazione.scala, d.Ubicazione.Scala);

			Assert.AreEqual<string>(datiImmobileSrc.datiCatastali.categoriaCatastale.ToString(), d.RiferimentiCatastali.CategoriaCatastale);
			Assert.AreEqual<string>(datiImmobileSrc.datiCatastali.foglio, d.RiferimentiCatastali.Foglio);
			Assert.AreEqual<string>(datiImmobileSrc.datiCatastali.particella, d.RiferimentiCatastali.Particella);
			Assert.AreEqual<string>(datiImmobileSrc.datiCatastali.sezione, d.RiferimentiCatastali.Sezione);
			Assert.AreEqual<string>(datiImmobileSrc.datiCatastali.subalterno, d.RiferimentiCatastali.Subalterno);
		}

		[TestMethod]
		public void Conversione_da_datiIndirizzoType_a_IndirizzoTasiDto_con_civico()
		{
			var mapper = new DatiIndirizzoTypeToIndirizzoTasiDto(new MockViaResolver());
			var i = mapper.Map(datiIndirizzoSrcNonCodificato);

			Assert.AreEqual<string>(datiIndirizzoSrcNonCodificato.cap, i.Cap);
			Assert.AreEqual<string>(datiIndirizzoSrcNonCodificato.esponente,i.Esponente);
			Assert.AreEqual<string>(datiIndirizzoSrcNonCodificato.interno, i.Interno);
			Assert.AreEqual<string>(datiIndirizzoSrcNonCodificato.Item , i.Via);
			Assert.AreEqual<string>(datiIndirizzoSrcNonCodificato.numeroCivico.ToString(), i.Civico);
			Assert.AreEqual<string>(datiIndirizzoSrcNonCodificato.palazzina, i.Palazzina);
			Assert.AreEqual<string>(datiIndirizzoSrcNonCodificato.piano, i.Piano);
			Assert.AreEqual<string>(datiIndirizzoSrcNonCodificato.scala, i.Scala);
			Assert.AreEqual<string>(datiIndirizzoSrcNonCodificato.suffisso, i.Suffisso);
		}

		[TestMethod]
		public void Conversione_da_datiIndirizzoType_a_IndirizzoTasiDto_senza_civico()
		{
			datiIndirizzoSrcNonCodificato.numeroCivico = 1;
			datiIndirizzoSrcNonCodificato.numeroCivicoSpecified = false;

			var mapper = new DatiIndirizzoTypeToIndirizzoTasiDto(new MockViaResolver());
			var i = mapper.Map(datiIndirizzoSrcNonCodificato);

			Assert.AreEqual<string>(String.Empty, i.Civico);
		}

		[TestMethod]
		public void Conversione_da_datiImmobiliResponseType_a_DatiContribuenteTasiDto_con_persona_fisica()
		{
			var mapper = new ResponseMapper();

			var i = mapper.Map(datiImmobiliResponsePfSrc);
			
			Assert.AreEqual<string>(datiImmobiliResponsePfSrc.codContribuente, i.CodiceContribuente);
			Assert.IsNotNull(i.Residenza);
			Assert.AreEqual<int>(datiImmobiliResponsePfSrc.elencoImmobili.Length, i.ListaImmobili.Count());
			Assert.AreEqual<string>(datiImmobiliResponsePfSrc.idContribuente, i.IdContribuente);

			var anagrafica = (datiAnagraficiPersonaFisicaType)datiImmobiliResponsePfSrc.Item;

			Assert.AreEqual<DatiContribuenteTasiDto.TipoPersonaEnum>(DatiContribuenteTasiDto.TipoPersonaEnum.Fisica, i.TipoPersona);
			Assert.AreEqual<string>(anagrafica.codiceFiscale, i.CodiceFiscale);
			Assert.AreEqual<string>(anagrafica.cognome, i.Cognome);
			Assert.AreEqual<string>("Nome comune", i.ComuneNascita);
			Assert.AreEqual<string>(anagrafica.comuneNascita, i.CodiceIstatComuneNascita);
			Assert.AreEqual<string>(anagrafica.dataNascita, i.DataNascita.Value.ToString("dd/MM/yyyy"));
			Assert.AreEqual<string>(anagrafica.nome, i.Nome);
			Assert.AreEqual<string>(anagrafica.provinciaNascita, i.ProvinciaNascita);
			Assert.IsTrue(i.Sesso.HasValue);
			Assert.AreEqual<DatiContribuenteTasiDto.SessoEnum>(DatiContribuenteTasiDto.SessoEnum.Maschio,i.Sesso.Value);
			
		}


		[TestMethod]
		public void Conversione_da_datiImmobiliResponseType_a_DatiContribuenteTasiDto_con_persona_giuridica()
		{
			var mapper = new ResponseMapper();

			var i = mapper.Map(datiImmobiliResponsePgSrc);

			Assert.AreEqual<string>(datiImmobiliResponsePfSrc.codContribuente, i.CodiceContribuente);
			Assert.IsNotNull(i.Residenza);
			Assert.AreEqual<int>(datiImmobiliResponsePfSrc.elencoImmobili.Length, i.ListaImmobili.Count());
			Assert.AreEqual<string>(datiImmobiliResponsePfSrc.idContribuente, i.IdContribuente);

			var anagrafica = (datiAnagraficiPersonaGiuridicaType)datiImmobiliResponsePgSrc.Item;

			Assert.AreEqual<DatiContribuenteTasiDto.TipoPersonaEnum>(DatiContribuenteTasiDto.TipoPersonaEnum.Giuridica, i.TipoPersona);
			Assert.AreEqual<string>(anagrafica.denominazione, i.Cognome);
			Assert.AreEqual<string>(anagrafica.partitaIva, i.CodiceFiscale);
			Assert.IsFalse(i.Sesso.HasValue);

		}	
	}		
}			
