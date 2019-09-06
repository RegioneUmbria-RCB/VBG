// -----------------------------------------------------------------------
// <copyright file="SerializzazioneDatiContribuenteTasiDtoTests.cs" company="">
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
	using Init.Sigepro.FrontEnd.Bari.TASI.DTOs;
	using System.IO;
	using System.Xml.Serialization;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	[TestClass]
	public class SerializzazioneDatiContribuenteTasiDtoTests
	{
		[TestMethod]
		public void Serializzazione_DatiContribuenteTasiDto()
		{
			var cls = new DatiContribuenteTasiDto
			{
				CodiceContribuente = "123",
				CodiceFiscale = "cf",
				Cognome = "cognome",
				ComuneNascita = "CESTERO",
				DataNascita = new DateTime(),
				IdContribuente = "456",
				Nome = "nome",
				ProvinciaNascita = "PG",
				Sesso = null,
				TipoPersona = DatiContribuenteTasiDto.TipoPersonaEnum.Fisica,
				Residenza = new IndirizzoTasiDto
				{
					Cap = "c",
					Civico = "c",
					Esponente = "e",
					Interno = "i",
					Palazzina = "p",
					Piano = "p",
					Scala = "s",
					Suffisso = "s",
					Via = "v"
				},
				ListaImmobili = new List<ImmobileTasiDto>{
					new ImmobileTasiDto
					{
						DataInizioDecorrenza = "01/01/2014",
						IdImmobile = "123",
						PercentualePossesso = "50.0",
						RiferimentiCatastali = new DatiCatastaliTasiDto
						{
							Foglio = "F",
							Particella = "p",
							Sezione = "s",
							Subalterno = "s"
						},
						Ubicazione = new IndirizzoTasiDto
						{
							Cap = "c",
							Civico = "c",
							Esponente = "e",
							Interno = "i",
							Palazzina = "p",
							Piano = "p",
							Scala = "s",
							Suffisso = "s",
							Via = "v"
						}
					}
				}
			};

			using (var ms = new MemoryStream())
			{
				var xs = new XmlSerializer(cls.GetType());
				xs.Serialize(ms, cls);
			}
			
		}
	}
}
