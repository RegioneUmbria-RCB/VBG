using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Web.Services;
using System.Xml.Serialization;
using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager;
using Init.SIGePro.Utils;
using PersonalLib2.Data;

namespace SIGePro.Net.WebServices.WsSIGePro
{


	/// <summary>
	/// Descrizione di riepilogo per Movimenti.
	/// </summary>
	[WebService(Namespace="http://init.sigepro.it")]
	public class Movimenti : System.Web.Services.WebService
	{
        const int ERR_AUTHENTICATION_FAILED = 58001;
        const int ERR_INSERT_FAILED = 58002;
        const int ERR_PROT_FAILED = 58003;
        const int ERR_ELAB_FAILED = 58004;

		public Movimenti()
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
		protected override void Dispose( bool disposing )
		{
			if(disposing && components != null)
			{
				components.Dispose();
			}
			base.Dispose(disposing);		
		}
		
		#endregion

        #region

        int _protocollamovimentoSource = 16;
        protected int ProtocollamovimentoSource
        {
            get { return _protocollamovimentoSource; }
            set { _protocollamovimentoSource = value; }
        }

        #endregion

        #region

        int _fascicolamovimentoSource = 16;
        protected int FascicolamovimentoSource
        {
            get { return _fascicolamovimentoSource; }
            set { _fascicolamovimentoSource = value; }
        }

        #endregion


	
		
		//[WebMethod(Description="Ottiene i dati relativi ad un tipo di movimento")]
		//public DatiTipoMovimento GetTipoMovimento( string token , string codiceTipoMovimento )
		//{
		//    AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

		//    if ( authInfo == null )
		//        throw new InvalidTokenException(token);

		//    using(DataBase database = authInfo.CreateDatabase() )
		//    {
		//        TipiMovimentoMgr mgr = new TipiMovimentoMgr( database );
		//        TipiMovimento tipiMov = mgr.GetById( codiceTipoMovimento , authInfo.IdComune );

		//        if ( tipiMov == null ) return null;

		//        DatiTipoMovimento datiTipoMov = new DatiTipoMovimento();

		//        datiTipoMov.Codice		= tipiMov.Tipomovimento;
		//        datiTipoMov.Descrizione = tipiMov.Movimento;
		//        datiTipoMov.CodEnte		= tipiMov.Idcomune;
		//        datiTipoMov.CodSportello= tipiMov.Software;

		//        return datiTipoMov;
		//    }
		//}

	}
}
