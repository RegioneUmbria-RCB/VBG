// -----------------------------------------------------------------------
// <copyright file="MappaturaCampoDinamico.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Core.MappatureCampiDinamici
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using System.IO;
	using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
	using System.Xml;


	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class MappaturaCampoDinamico
	{
		public int IdCampo { get; set; }
		public string XPath { get; set; }
        public bool IsDate { get; set; }

		public MappaturaCampoDinamico()
		{
		}

		public MappaturaCampoDinamico(int id, string xpath, bool isDate=false)
		{
			this.IdCampo = id;
			this.XPath = xpath;
            this.IsDate = isDate;
		}
	}

	public class MappatureCampi
	{
		private class ValoreCampoDinamico
		{
			public readonly int Id;
			public readonly string Valore;
			public readonly string ValoreDecodificato;
			public readonly int Indice;

			public ValoreCampoDinamico(int id, string valore, string valoreDecodificato, int indice)
			{
				this.Id = id;
				this.Valore = valore;
                this.ValoreDecodificato = valoreDecodificato;
				this.Indice = indice;
			}
		}


		public List<MappaturaCampoDinamico> Mappature { get; set; }

		public MappatureCampi()
		{
			this.Mappature = new List<MappaturaCampoDinamico>();
		}

		public static MappatureCampi Load(string path)
		{
			var fileContent = File.ReadAllText(path);

			return fileContent.DeserializeXML<MappatureCampi>();
		}

		public static void Test()
		{
			var m = new MappatureCampi();

			m.Mappature.Add(new MappaturaCampoDinamico(1, "xpath1"));
			m.Mappature.Add(new MappaturaCampoDinamico(2, "xpath2"));
			m.Mappature.Add(new MappaturaCampoDinamico(3, "xpath3"));

			File.WriteAllText(@"c:\temp\testmappature.xml", m.ToXmlString());
		}

		internal void ApplicaA(DomandaOnline domanda)
		{
			var valori = EstraiValoriDaDomanda(domanda);

			foreach (var valore in valori)
			{
				domanda.WriteInterface.DatiDinamici.AggiornaOCrea(valore.Id, 0, valore.Indice, valore.Valore, valore.ValoreDecodificato, string.Empty);
			}
		}

		private IEnumerable<ValoreCampoDinamico> EstraiValoriDaDomanda(DomandaOnline domanda)
		{
			var datiContribuente = domanda.ReadInterface.DenunceTaresBari.DatiContribuente.ToXmlString();
			var valori = new List<ValoreCampoDinamico>();

			var doc = new XmlDocument();

			doc.LoadXml(datiContribuente);

			var rootNode = doc.DocumentElement;

			foreach (var mappatura in this.Mappature)
			{
				var nodes = rootNode.SelectNodes(mappatura.XPath);

				//foreach (var node in nodes.Cast<XmlNode>())
				for (int i = 0; i < nodes.Count; i++)
				{
					var node = nodes[i];
					var valore = node.InnerText;
                    var valoreDecodificato = valore;


					if (!String.IsNullOrEmpty(valore))
					{
                        if (mappatura.IsDate)
                        {
                            var date = DateTime.ParseExact(valore, "yyyyMMdd", null);

                            valoreDecodificato = date.ToString("dd/MM/yyyy");
                        }

                        valori.Add(new ValoreCampoDinamico(mappatura.IdCampo, valore, valoreDecodificato, i));
					}
				}
			}

			return valori;
		}
	}
}
