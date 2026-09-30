using Init.SIGeProExport.Data;
using PersonalLib2.Data;
using System;
using System.Configuration;
using System.Data;

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
                if (this.Request.QueryString["idesportazione"] != null)
                    return this.Request.QueryString["idesportazione"].ToString();

                return null;
            }
        }

        protected string qsIdComune
        {
            get
            {
                if (this.Request.QueryString["idcomune"] != null)
                    return this.Request.QueryString["idcomune"].ToString();

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
                if (this.Esp == null || String.IsNullOrEmpty(this.Esp.ID))
                    return true;

                //se l'esportazione esiste ed è diversa dal comune di default allora abilito la gestione
                if (this.Esp.IDCOMUNE.ToUpper() != this.IdComuneDefault)
                    return true;

                //se l'esportazione esiste e l'idcomune è quello di default allora controllo ModificaIdcomuneDefault
                return this.ModificaIdcomuneDefault;
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
                if (this._DbDestinazione == null)
                {
                    ProviderType initialProviderType = (ProviderType)Enum.Parse(typeof(ProviderType), ConfigurationSettings.AppSettings["PROVIDERTYPE_CONFIG"].ToString(), true);
                    this._DbDestinazione = new DataBase(ConfigurationSettings.AppSettings["CONNECTIONSTRING_CONFIG"].ToString(), initialProviderType);
                }

                return this._DbDestinazione;
            }
        }

        protected TRACCIATIDETTAGLIO TracDett
        {
            get
            {
                if ((TRACCIATIDETTAGLIO)this.Session["TRACCIATIDETTAGLIO"] != null)
                    return (TRACCIATIDETTAGLIO)this.Session["TRACCIATIDETTAGLIO"];
                else
                {
                    return null;
                }
            }
            set
            {
                this.Session["TRACCIATIDETTAGLIO"] = value;
            }
        }

        protected TRACCIATI Trac
        {
            get
            {
                if ((TRACCIATI)this.Session["TRACCIATI"] != null)
                    return (TRACCIATI)this.Session["TRACCIATI"];
                else
                {
                    return null;
                }
            }
            set
            {
                this.Session["TRACCIATI"] = value;
            }
        }

        protected PARAMETRIESPORTAZIONE Parametro
        {
            get
            {
                if ((PARAMETRIESPORTAZIONE)this.Session["PARAMETRIESPORTAZIONE"] != null)
                    return (PARAMETRIESPORTAZIONE)this.Session["PARAMETRIESPORTAZIONE"];
                else
                {
                    return null;
                }
            }
            set
            {
                this.Session["PARAMETRIESPORTAZIONE"] = value;
            }
        }

        protected ESPORTAZIONI Esp
        {
            get
            {
                if ((ESPORTAZIONI)this.Session["ESPORTAZIONI"] != null)
                    return (ESPORTAZIONI)this.Session["ESPORTAZIONI"];
                else
                {
                    return null;
                }
            }
            set
            {
                this.Session["ESPORTAZIONI"] = value;
            }
        }

        override protected void OnInit(EventArgs e)
        {
            base.OnInit(e);
        }

        protected string GetField(string sCommand, int iInitialValue)
        {
            if (this.DbDestinazione.Connection.State != ConnectionState.Open)
                this.DbDestinazione.Connection.Open();

            IDbCommand command = null;
            int iField;
            try
            {
                command = this.DbDestinazione.CreateCommand(sCommand);
                iField = Convert.ToInt32(command.ExecuteScalar()) + 1;
            }
            catch (Exception)
            {
                iField = iInitialValue;
            }
            finally
            {
                if (command != null)
                    command.Dispose();
                this.DbDestinazione.Connection.Close();
            }
            return iField.ToString();
        }
    }
}
