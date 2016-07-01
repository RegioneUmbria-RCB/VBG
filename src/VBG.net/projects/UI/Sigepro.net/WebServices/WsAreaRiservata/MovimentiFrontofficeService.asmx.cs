using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Sigepro.net.WebServices.WsSIGePro;
using Init.SIGePro.Manager;
using System.Xml.Serialization;
using Init.SIGePro.Data;
using SIGePro.Net.WebServices.WsSIGePro;
using Init.SIGePro.Scadenzario;

namespace Sigepro.net.WebServices.WsAreaRiservata
{




	/// <summary>
	/// Summary description for MovimentiFrontofficeService
	/// </summary>
	[WebService(Namespace = "http://init.sigepro.it")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	// [System.Web.Script.Services.ScriptService]
	public class MovimentiFrontofficeService : SigeproWebService
	{
		#region Classi restituite dal servizio

		/// <summary>
		/// Descrizione di riepilogo per DatiMovimento.
		/// </summary>
		public class DatiMovimentoDaEffettuareDto
		{
			[XmlElement(Order = 0)]
			public int CodiceMovimento{get;set;}

			[XmlElement(Order = 1)]
			public string TipoMovimento{get;set;}

			[XmlElement(Order = 2)]
			public string DescInventario{get;set;}

			[XmlElement(Order = 3)]
			public string Amministrazione{get;set;}

			[XmlElement(Order = 4)]
			public string Esito{get;set;}

			[XmlElement(Order = 5)]
			public string Parere{get;set;}

			[XmlElement(Order = 6)]
			public string Note{get;set;}

			[XmlElement(Order = 7)]
			public List<MovimentiAllegati> Allegati{get;set;}

			[XmlElement(Order = 8)]
			public string Descrizione{get;set;}

			[XmlElement(Order = 9)]
			public DateTime? DataMovimento{get;set;}

			[XmlElement(Order = 10)]
			public int CodiceIstanza{get;set;}

			[XmlElement(Order = 11)]
			public string NumeroIstanza{get;set;}

			[XmlElement(Order = 12)]
			public bool VisualizzaParere{get;set;}

			[XmlElement(Order = 13)]
			public bool VisualizzaEsito{get;set;}

			[XmlElement(Order = 14)]
			public bool Pubblica{get;set;}

			[XmlElement(Order = 15)]
			public string NumeroProtocollo{get;set;}

			[XmlElement(Order = 16)]
			public DateTime? DataProtocollo{get;set;}

			[XmlElement(Order = 17)]
			public string IdComune{get;set;}

			[XmlElement(Order = 18)]
			public string NumeroProtocolloIstanza{get;set;}

			[XmlElement(Order = 19)]
			public DateTime? DataProtocolloIstanza{get;set;}

			[XmlElement(Order = 20)]
			public DateTime DataIstanza{get;set;}

			[XmlElement(Order = 21)]
			public List<SchedaDinamica> SchedeDinamiche { get; set; }
			
			[XmlElement(Order = 22)]
			public string CodiceInventario { get; set; }


			public DatiMovimentoDaEffettuareDto()
			{
				this.Allegati = new List<MovimentiAllegati>();
				this.SchedeDinamiche = new List<SchedaDinamica>();
			}
		}



		public class DatiTipoMovimento
		{
			[XmlElement(Order = 0)]
			public string Codice { get; set; }

			[XmlElement(Order = 1)]
			public string Descrizione { get; set; }

			[XmlElement(Order = 2)]
			public string CodEnte { get; set; }

			[XmlElement(Order = 3)]
			public string CodSportello { get; set; }
		}


		public class SchedaDinamica
		{
			[XmlElement(Order = 0)]
			public int Id { get; set; }

			[XmlElement(Order = 1)]
			public string Titolo { get; set; }

			[XmlElement(Order = 2)]
			public List<ValoreDatoDinamico> Valori { get; set; }

			[XmlElement(Order = 3)]
			public List<int> IdCampiContenuti { get;set; }

			public SchedaDinamica()
			{
				this.Valori = new List<ValoreDatoDinamico>();
			}
		}

		public class ValoreDatoDinamico
		{
			[XmlElement(Order = 0)]
			public int Id { get; set; }
			[XmlElement(Order = 1)]
			public int Indice { get; set; }
			[XmlElement(Order = 2)]
			public string Valore { get; set; }
			[XmlElement(Order = 3)]
			public string ValoreDecodificato { get; set; }
		}

		#endregion



		/// <summary>
		/// Legge i dati relativi ad un movimento
		/// </summary>
		/// <param name="token">Token ottenuto con l'autenticazione</param>
		/// <param name="strCodiceMovimento">Codice del movimento di cui occorre leggere i dati</param>
		/// <returns></returns>
		[WebMethod(Description = "Permette di leggere i dati di un movimento effettuato")]
		public DatiMovimentoDaEffettuareDto GetMovimento(string token, string strCodiceMovimento)
		{
			var authInfo = CheckToken(token);

			using (var database = authInfo.CreateDatabase())
			{
				var idComune = authInfo.IdComune;
				var codiceMovimento = Convert.ToInt32(strCodiceMovimento);

				var movimentoSigepro = new MovimentiMgr(database).GetById(authInfo.IdComune, codiceMovimento);

				if (movimentoSigepro == null) 
					return null;

				var tipoMovimento = new TipiMovimentoMgr(database).GetById(movimentoSigepro.TIPOMOVIMENTO, idComune);

				var istanza = new IstanzeMgr(database).GetById(idComune, Convert.ToInt32(movimentoSigepro.CODICEISTANZA));

				var movimentiDyn2Mgr = new MovimentiDyn2ModelliTMgr( database );
				var istanzeDyn2DatiMgr = new IstanzeDyn2DatiMgr( database ); 

				var codiceIstanza = Convert.ToInt32(istanza.CODICEISTANZA); 

				DatiMovimentoDaEffettuareDto rVal = new DatiMovimentoDaEffettuareDto
				{
					IdComune = movimentoSigepro.IDCOMUNE,
					CodiceIstanza = codiceIstanza,
					NumeroIstanza = istanza.NUMEROISTANZA,
					NumeroProtocolloIstanza = istanza.NUMEROPROTOCOLLO,
					DataProtocolloIstanza = istanza.DATAPROTOCOLLO,					
					CodiceMovimento = codiceMovimento,
					NumeroProtocollo = movimentoSigepro.NUMEROPROTOCOLLO,
					DataProtocollo = movimentoSigepro.DATAPROTOCOLLO,
					DataIstanza = istanza.DATA.Value,
					Amministrazione = !string.IsNullOrEmpty(movimentoSigepro.CODICEAMMINISTRAZIONE) ? new AmministrazioniMgr(database).GetById(idComune, Convert.ToInt32(movimentoSigepro.CODICEAMMINISTRAZIONE)).AMMINISTRAZIONE : String.Empty,
					CodiceInventario = movimentoSigepro.CODICEINVENTARIO,
					DescInventario = !string.IsNullOrEmpty(movimentoSigepro.CODICEINVENTARIO) ? new InventarioProcedimentiMgr(database).GetById(idComune, Convert.ToInt32(movimentoSigepro.CODICEINVENTARIO)).Procedimento : String.Empty,
					Esito = movimentoSigepro.ESITO != "0" ? "Positivo" : "Negativo",
					Note = movimentoSigepro.NOTE,
					Parere = movimentoSigepro.PARERE,
					Descrizione = movimentoSigepro.MOVIMENTO,
					Pubblica = movimentoSigepro.PUBBLICA != "0",
					DataMovimento = movimentoSigepro.DATA,
					VisualizzaParere = movimentoSigepro.PUBBLICAPARERE != "0",
					VisualizzaEsito = tipoMovimento.Tipologiaesito.GetValueOrDefault(0) != 0,
					Allegati = new MovimentiAllegatiMgr(database).GetList(new MovimentiAllegati
					{
						IDCOMUNE = authInfo.IdComune,
						CODICEMOVIMENTO = movimentoSigepro.CODICEMOVIMENTO,
						FlagPubblica = 1
					}),
					SchedeDinamiche = movimentiDyn2Mgr.GetSchedeDelMovimento( idComune , codiceMovimento )
													  .Select( x => new SchedaDinamica{
															Id = x.Id.Value,
															Titolo = x.Descrizione,
															Valori = istanzeDyn2DatiMgr.GetListByCodiceIstanzaIdModello( idComune , codiceIstanza , x.Id.Value )
																						.Select( y => new ValoreDatoDinamico
																						{
																							Id = y.FkD2cId.Value,
																							Indice = y.IndiceMolteplicita.Value,
																							Valore = y.Valore,
																							ValoreDecodificato = y.Valoredecodificato
																						}).ToList(),
															IdCampiContenuti = new Dyn2ModelliDMgr( database ).GetCampiDinamiciModello( idComune , x.Id.Value)
																											  .Select( cd => cd.FkD2cId.Value ).ToList()
																											  
														}).ToList()
				};

				return rVal;
			}
		}

		[WebMethod(Description = "Permette di leggere le scadenze delle pratiche in base a una serie di filtri")]
		public ListaScadenze GetListaScadenze(string token, RichiestaListaScadenze richiesta)
		{
			var authInfo = CheckToken(token);

			ScadenzeManager scadMgr = new ScadenzeManager(authInfo);
			return scadMgr.GetListaScadenze(richiesta);
		}

		[WebMethod(Description = "Permette di leggere i dati di una scadenza in base al suo id univoco")]
		public ElementoListaScadenze GetScadenza(string token, int codiceScadenza)
		{
			var authInfo = CheckToken(token);

			ScadenzeManager scadMgr = new ScadenzeManager(authInfo);
			return scadMgr.GetScadenza(codiceScadenza);
		}

		/// <summary>
		/// Legge i dati json del movimento identificato dall'id univoco specificato
		/// </summary>
		/// <param name="token">Token</param>
		/// <param name="idMovimento">Identificativo del movimento</param>
		/// <returns>Dati in formato json del movimento o null se il movimento non esiste nella base dati</returns>
		[WebMethod(Description = "Restituisce i dati di un movimento parzialmente compilato dall'utente")]
		public string GetJsonMovimentoFrontoffice(string token, int idMovimento)
		{
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
				return new FoMovimentiMgr(db).GetDati(authInfo.IdComune, idMovimento);
		}

		/// <summary>
		/// Salva i dati in formato json di un movimento del frontoffice identificato dall'identificativo univoco specificato
		/// </summary>
		/// <param name="token">Token</param>
		/// <param name="idMovimento">Identificativo del movimento</param>
		/// <param name="datiJson">Dati in formato json del movimento</param>
		[WebMethod(Description = "Salva i dati di un movimento parzialmente compilato dall'utente")]
		public void SalvaJsonMovimentoFrontoffice(string token, int idMovimento, string datiJson)
		{
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
				new FoMovimentiMgr(db).SalvaDati(authInfo.IdComune, idMovimento, datiJson);
		}

		/// <summary>
		/// Imposta il movimento frontoffice come inviato
		/// </summary>
		/// <param name="token">Token</param>
		/// <param name="idMovimento">Identificativo del movimento</param>
		[WebMethod(Description = "Imposta un movimento effettuato dall'utente come inviato")]
		public void ImpostaFlagTrasmesso(string token, int idMovimento)
		{
			var authInfo = CheckToken(token);

			using (var db = authInfo.CreateDatabase())
				new FoMovimentiMgr(db).ImpostaMovimentoComeTrasmesso(authInfo.IdComune, idMovimento);
		}


		/// <summary>
		/// Contrassegna un movimento effettuato dal frontoffice come inviato
		/// </summary>
		/// <param name="token">token</param>
		/// <param name="idMovimento">Identificativo del movimento</param>
		[WebMethod(Description = "Marca un movimento compilato compilato dall'utente come compilato")]
		public void MarcaMovimentoFrontofficeComeInviato(string token, int idMovimento)
		{
			throw new NotImplementedException();
		}
	}
}
