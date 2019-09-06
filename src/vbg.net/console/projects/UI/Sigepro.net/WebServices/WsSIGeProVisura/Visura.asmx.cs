using System;
using System.ComponentModel;
using System.Web.Services;
using Init.SIGePro.Authentication;
using Init.SIGePro.Utils;
using Init.SIGePro.Manager.Logic.Visura;
using Init.SIGePro.Exceptions.Token;
using log4net;
using Init.Utils;
using Init.SIGePro.Manager;

namespace SIGePro.Net.WebServices.WsSIGeProVisura
{
	/// <summary>
	/// Descrizione di riepilogo per Visura.
	/// </summary>
	[WebService(Namespace="http://init.sigepro.it")]
	public class Visura : WebService
	{
        ILog _log = LogManager.GetLogger(typeof(Visura));

		public Visura()
		{
			//CODEGEN: chiamata richiesta da Progettazione servizi Web ASP.NET.
			InitializeComponent();
		}

		#region Codice generato da Progettazione componenti

		//Richiesto da Progettazione servizi Web 
		private IContainer components = null;

		/// <summary>
		/// Metodo necessario per il supporto della finestra di progettazione. Non modificare
		/// il contenuto del metodo con l'editor di codice.
		/// </summary>
		private void InitializeComponent()
		{
		}

		/// <summary>
		/// Pulire le risorse in uso.
		/// </summary>
		protected override void Dispose(bool disposing)
		{
			if (disposing && components != null)
			{
				components.Dispose();
			}
			base.Dispose(disposing);
		}

		#endregion

		[WebMethod]
		public DettaglioPratiche GetDettaglioPratica(string token, RichiestaDettaglioPratica request)
		{
			AuthenticationInfo ai = AuthenticationManager.CheckToken(token);

			if (ai == null)
                throw new InvalidTokenException(token);


			try
			{
				Type itemType = request.Item.GetType();

				if (itemType == typeof (RichiestaPerCodiceSportelloENumeroPratica))
				{
					((RichiestaPerCodiceSportelloENumeroPratica) request.Item).CodEnte = ai.IdComune;

				}
				else if (itemType == typeof (RichiestaPerNumeroProtocollo))
				{
					((RichiestaPerNumeroProtocollo) request.Item).CodEnte = ai.IdComune;
				}
				else if (itemType == typeof (RichiestaPerIdPratica))
				{
					((RichiestaPerIdPratica) request.Item).CodEnte = ai.IdComune;
				}
				else
				{
					throw new Exception("Tipo di richiesta " + request.GetType().Name.ToString() + " non supportato");
				}

				return new VisuraPraticheManager( ai ).GetDettaglioPratica(request);
			}
			catch (Exception ex)
			{
				Logger.LogEvent(ai, "WSVISURA", ex.Message, "ERR_DETTAGLIO");
				throw new Exception(ex.Message);
			}
		}
        /*
        public DettaglioPratiche GetDettaglioPraticaByUuid(string token, string uuid)
        {
            AuthenticationInfo ai = AuthenticationManager.CheckToken(token);

            if (ai == null)
                throw new InvalidTokenException(token);
            
            try
            {
                var codiceIstanza = new IstanzeMgr(ai.CreateDatabase()).GetCodiceIstanzaDaUuid(ai.IdComune, uuid);

                var request = new RichiestaDettaglioPratica
                {
                    Item = new RichiestaPerIdPratica
                    {
                        CodEnte = ai.IdComune,
                        IdPratica = codiceIstanza.ToString()
                    }
                };

                return new VisuraPraticheManager(ai).GetDettaglioPratica(request);
            }
            catch (Exception ex)
            {
                Logger.LogEvent(ai, "WSVISURA", ex.Message, "ERR_DETTAGLIO");
                throw new Exception(ex.Message);
            }
        }

        */
        [WebMethod]
		public ListaPratiche GetListaPratiche(string token, RichiestaListaPratiche richiestaLista)
		{
			AuthenticationInfo ai = AuthenticationManager.CheckToken(token);

			if (ai == null)
                throw new InvalidTokenException(token);

			try
			{
				richiestaLista.CodEnte = ai.IdComune;

				return new VisuraPraticheManager(ai ).GetListaPratiche(richiestaLista);
			}
			catch (Exception ex)
			{
                _log.ErrorFormat("Errore nella chiamata a GetListaPratiche: {0} - {1}", ex.ToString(), StreamUtils.SerializeClass(richiestaLista));
				throw new Exception(ex.Message);
			}
		}


	}
}