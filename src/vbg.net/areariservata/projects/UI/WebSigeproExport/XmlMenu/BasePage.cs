using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using PersonalLib2.Data;
using Init.SIGeProExport.Data;

namespace WebSigeproExport
{
    public class BasePage : System.Web.UI.Page
    {
        //protected string sMod;
		protected string sPagina;
		protected string sDesc;
		protected string sTipo;
        protected string sTipoContesto;

        protected string qsIdEsportazione
        {
            get 
            {
                if (Request.QueryString["idesportazione"] != null)
                    return Request.QueryString["idesportazione"].ToString();

                return null;
            }
        }

        protected string qsIdComune
        {
            get 
            { 
                if( Request.QueryString["idcomune"] != null  )
                    return Request.QueryString["idcomune"].ToString();

                return null;
            }
        }

        protected int DatagridPageSize
        {
            get 
            { 
                return Convert.ToInt32(ConfigurationManager.AppSettings["PAGE_SIZE"]); 
            }
        }

        protected string IdComuneDefault
        {
            get { return ConfigurationManager.AppSettings["IDCOMUNE_DEFAULT"].ToString(); }
        }

        private bool ModificaIdcomuneDefault
        {
            get 
            {
                if (ConfigurationManager.AppSettings["MODIFICA_IDCOMUNE_DEFAULT"] == null)
                    return false;

                return Convert.ToBoolean(ConfigurationManager.AppSettings["MODIFICA_IDCOMUNE_DEFAULT"]);
            }
        }

        protected bool ModificaIdcomune
        {
            get 
            {
                //se l'esportazione non esiste allora abilito la gestione
                if (Esp == null || String.IsNullOrEmpty(Esp.ID))
                    return true;

                //se l'esportazione esiste ed è diversa dal comune di default allora abilito la gestione
                if (Esp.IDCOMUNE.ToUpper() != IdComuneDefault)
                    return true;

                //se l'esportazione esiste e l'idcomune è quello di default allora controllo ModificaIdcomuneDefault
                return ModificaIdcomuneDefault;
            }
        }

        public BasePage()
		{
		}

		private DataBase _DbDestinazione = null;

		protected DataBase DbDestinazione
		{
			get 
			{
				if ( _DbDestinazione == null )
				{
					ProviderType initialProviderType = (ProviderType)Enum.Parse( typeof(ProviderType) , ConfigurationSettings.AppSettings["PROVIDERTYPE_CONFIG"].ToString() , true );
					_DbDestinazione = new DataBase( ConfigurationSettings.AppSettings["CONNECTIONSTRING_CONFIG"].ToString(), initialProviderType );
				}

				return _DbDestinazione;
			}
		}
        
		protected TRACCIATIDETTAGLIO TracDett
		{
			get
			{
				if ((TRACCIATIDETTAGLIO)Session["TRACCIATIDETTAGLIO"] != null)
					return (TRACCIATIDETTAGLIO)Session["TRACCIATIDETTAGLIO"];
				else
				{
					return null;
				}
			}
			set
			{
				Session["TRACCIATIDETTAGLIO"] = value;
			}
		}

		protected TRACCIATI Trac
		{
			get
			{
				if ((TRACCIATI)Session["TRACCIATI"] != null)
					return (TRACCIATI)Session["TRACCIATI"];
				else
				{
					return null;
				}
			}
			set
			{
				Session["TRACCIATI"] = value;
			}
		}

		protected PARAMETRIESPORTAZIONE Parametro
		{
			get
			{
				if ((PARAMETRIESPORTAZIONE)Session["PARAMETRIESPORTAZIONE"] != null)
					return (PARAMETRIESPORTAZIONE)Session["PARAMETRIESPORTAZIONE"];
				else
				{
					return null;
				}
			}
			set
			{
				Session["PARAMETRIESPORTAZIONE"] = value;
			}
		}

		protected ESPORTAZIONI Esp
		{
			get
			{
				if ((ESPORTAZIONI)Session["ESPORTAZIONI"] != null)
					return (ESPORTAZIONI)Session["ESPORTAZIONI"];
				else
				{
					return null;
				}
			}
			set
			{
				Session["ESPORTAZIONI"] = value;
			}
		}

		override protected void OnInit(EventArgs e)
		{
			base.OnInit(e);
		}

		protected string GetField(string sCommand, int iInitialValue)
		{
			if ( DbDestinazione.Connection.State != ConnectionState.Open )
				DbDestinazione.Connection.Open();

            IDbCommand command = null;
			int iField;
            try
            {
                command = DbDestinazione.CreateCommand(sCommand);
                iField = Convert.ToInt32(command.ExecuteScalar()) + 1;
            }
            catch (Exception)
            {
                iField = iInitialValue;
            }
            finally
            {
                if(command!=null)
                    command.Dispose();
                DbDestinazione.Connection.Close();
            }
			return iField.ToString();
		}
    }
}
