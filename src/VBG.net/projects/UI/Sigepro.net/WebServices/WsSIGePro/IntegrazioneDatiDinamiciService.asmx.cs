using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Sigepro.net.Istanze.DatiDinamici;
using Init.SIGePro.Manager;
using Init.SIGePro.Data;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.Manager.Manager;
using Init.SIGePro.DatiDinamici.WebControls;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccessProviders;

namespace Sigepro.net.WebServices.WsSIGePro
{
	/// <summary>
	/// Summary description for IntegrazioneDatiDinamiciService
	/// </summary>
	[WebService(Namespace = "http://init.sigepro.it")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	// [System.Web.Script.Services.ScriptService]
	public class IntegrazioneDatiDinamiciService : SigeproWebService
	{

		public class RichiestaIntegrazioneDatiDinamici
		{
			public int IdIstanza { get; set; }
			public bool ElaboraScriptCaricamento { get; set; }
			public bool ElaboraScriptModifica { get; set; }
			public bool ElaboraScriptSalvataggio { get; set; }
		}


		public class RispostaIntegrazioneDatiDinamici
		{
			public bool IntegrazioneEffettuata { get; set; }
			public List<string> ListaErrori { get; set; }

			public RispostaIntegrazioneDatiDinamici()
			{
				ListaErrori = new List<string>();
			}
		}



		[WebMethod]
		public RispostaIntegrazioneDatiDinamici EffettuaIntegrazioneDatiDinamici(string token , RichiestaIntegrazioneDatiDinamici options)
		{
			var authInfo = CheckToken( token );
			var rVal = new RispostaIntegrazioneDatiDinamici { IntegrazioneEffettuata = true };

			using(var db = authInfo.CreateDatabase())
			{
				var listaModelli = new IstanzeDyn2ModelliTMgr(db).GetList(new IstanzeDyn2ModelliT { Idcomune = authInfo.IdComune , Codiceistanza = options.IdIstanza });

				for (int i = 0; i < listaModelli.Count; i++)
				{
					try
					{
						var dap = new IstanzeDyn2DataAccessProvider(db, options.IdIstanza, authInfo.IdComune);
						var loader = new ModelloDinamicoLoader(dap, authInfo.IdComune, false);
						var modello = new ModelloDinamicoIstanza(loader,
																	listaModelli[i].FkD2mtId.Value,
																	options.IdIstanza,
																	0, false);

						if (options.ElaboraScriptCaricamento)
							modello.EseguiScriptCaricamento();

						var defaultElement = default(KeyValuePair<string, string>);

						foreach (var campoDinamico in modello.Campi)
						{
							Dictionary<string,string> dictionaryValori = null;

							if (campoDinamico.TipoCampo == TipoControlloEnum.Lista)
							{
								var elementoListaValori = campoDinamico.ProprietaControlloWeb.Find( x => x.Key == "ElementiLista");

								if (!elementoListaValori.Equals(defaultElement))
									dictionaryValori = ParseListaValori(elementoListaValori.Value);

							}

							for (int idxValore = 0; idxValore < campoDinamico.ListaValori.Count; idxValore++)
							{
								var valoreCampo = campoDinamico.ListaValori[idxValore];

								if (String.IsNullOrEmpty(valoreCampo.ValoreDecodificato))
									valoreCampo.ValoreDecodificato = valoreCampo.Valore;

								if (dictionaryValori != null && dictionaryValori.ContainsKey(valoreCampo.Valore))
									valoreCampo.ValoreDecodificato = dictionaryValori[valoreCampo.Valore];
							}

							if (options.ElaboraScriptModifica && campoDinamico.ScriptModifica != null)
								campoDinamico.ScriptModifica.Esegui(campoDinamico);

						}

						modello.Salva(options.ElaboraScriptSalvataggio);
					}
					catch (Exception ex)
					{
						rVal.IntegrazioneEffettuata = false;
						rVal.ListaErrori.Add(ex.Message);
					}

				}

				return rVal;
			}
		}

		private Dictionary<string, string> ParseListaValori(string listaValori)
		{
			var rVal = new Dictionary<string, string>();

			var elems = listaValori.Split(';');

			for (int i = 0; i < elems.Length; i++)
			{
				var parts = elems[i].Split('$');

				if (parts.Length > 1)
					rVal.Add(parts[0], parts[1]);
				else
					rVal.Add(elems[i], elems[i]);
			}

			return rVal;

		}
	}
}
