using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using Init.SIGePro.Data;
using SIGePro.Net;
using SIGePro.Net.Navigation;
using Init.SIGePro.Manager;
using Init.SIGePro.Exceptions.IstanzeAllegati;
using Init.Utils.Web.UI;
using System.Text;
using Init.SIGePro.Verticalizzazioni;
//using System.Web.UI.MobileControls;
using System.Collections.Generic;

namespace Sigepro.net.Istanze.SIT
{
    public partial class GeoIn : BasePage
    {
		protected string Via_Foglio
		{
			get { return Request.QueryString["param1"]; }
		}

		protected string Civico_Particella
		{
			get { return Request.QueryString["param2"]; }
		}

		protected string Colore_Catasto
		{
			get { return Request.QueryString["param3"]; }
		}

		protected string Contesto
        {
            get { return Request.QueryString["Contesto"]; }
        }

		protected string Codice
        {
            get { return Request.QueryString["Codice"]; }
        }

		protected string Modalita
        {
            get { return Request.QueryString["Modalita"]; }
        }

        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);

            //Verifico a quanti software è abilitato l'operatore
            var listaSoftwaredaUtilizzare = UtenteCorrenteIsAmministratore() ? GetCodiciSoftwareAttivi() : GetListaCodiciSoftwareDellUtente();
            

            //Verifico se la pratica è stata georeferenziata
            string key = string.Empty;
            string software = string.Empty;

            switch (Modalita)
            {
                case "A":
                    string sql;

                    sql = @"SELECT KEY 
						FROM
						  VW_I_ATTIVITALISTA
						WHERE
                           IDCOMUNE = {0} and                
                           I_IDATTIVITA = {1}";

                    sql = String.Format(sql, Database.Specifics.QueryParameterName("IDCOMUNE"),
                                         Database.Specifics.QueryParameterName("I_IDATTIVITA"));

                    using (IDbCommand cmd = Database.CreateCommand(sql))
                    {
                        cmd.Parameters.Add(Database.CreateParameter("IDCOMUNE", IdComune));
                        cmd.Parameters.Add(Database.CreateParameter("I_IDATTIVITA", Codice));

						using (IDataReader dataRdr = cmd.ExecuteReader())
						{
							if (dataRdr.Read())
							{
								key = dataRdr["KEY"].ToString();
								software = dataRdr["SOFTWARE"].ToString();
							}
						}
                    }
                    break;
                case "I":
                    var filtroStradario = new IstanzeStradario
					{
						PRIMARIO = "1",
						IDCOMUNE = IdComune,
						CODICEISTANZA = Codice
					};

                    var stradari = new IstanzeStradarioMgr(Database).GetList(filtroStradario);

                    if (stradari.Count != 0)
                    {
                        //Da inserire il campo KEY
                        key = stradari[0].COLORE;
                        software = new IstanzeMgr(Database).GetById(IdComune, Convert.ToInt32(Codice)).SOFTWARE;
                    }
                    break;
            }


			SoftwareMgr softMgr = new SoftwareMgr(Database);

            foreach (string codiceSoftware in listaSoftwaredaUtilizzare)
            {
                var soft = softMgr.GetById(codiceSoftware);

				var lblCheckBox = new LabeledCheckBox
				{
					Descrizione = "Layer " + soft.DESCRIZIONE,
					ID = "lbl" + soft.CODICE
				};

                //Aggiungo funzioni javascript ai controlli per la visualizzazione dei layer dei software ai quali
                //l'operatore è abilitato
				var eventName = "onclick";
				var jsEventFmtStr = "visibility_layer('{0}','{1}','{2}','{3}')";
				var jsEventCode = String.Format(jsEventFmtStr, lblCheckBox.Item.ClientID , lblAttive.Item.ClientID , lblCessate.Item.ClientID , lblCheckBox.ID.Substring(3) );

                lblCheckBox.Attributes.Add( eventName , jsEventCode );
                Fldset_Layer.Controls.Add(lblCheckBox);

                if (string.IsNullOrEmpty(key) && soft.CODICE != software)
                    lblCheckBox.Enabled = false;
            }

            //Aggiungo funzioni javascript ai controlli per la visualizzazione dei layer: cartografia e toponomastica
            lblCART.Attributes.Add("onclick", "visibility_layer('" + lblCART.Item.ClientID + "','" + lblAttive.Item.ClientID + "','" + lblCessate.Item.ClientID + "','" + lblCART.ID.Substring(3) + "')");
            lblTOPO.Attributes.Add("onclick", "visibility_layer('" + lblTOPO.Item.ClientID + "','" + lblAttive.Item.ClientID + "','" + lblCessate.Item.ClientID + "','" + lblTOPO.ID.Substring(3) + "')");

            //Aggiungo funzione javascript al controllo per la visualizzazione dei filtri di ricerca
            lblClearFiltro.Attributes.Add("onclick", "clear_elements('" + lblClearFiltro.Item.ClientID + "')");
        }

		private bool UtenteCorrenteIsAmministratore()
		{
			return new ResponsabiliMgr(Database).GetById(IdComune, AuthenticationInfo.CodiceResponsabile.Value).AMMINISTRATORE == "1";
		}

		/// <summary>
		/// Ottiene la lista dei codici software attivi nell'installazione
		/// </summary>
		/// <returns></returns>
		private string[] GetCodiciSoftwareAttivi()
		{
			//Elimino il software TT e splitto per la virgola
			string sListSoft = AuthenticationInfo.SoftwareAttivi.Replace(",TT", "");
			return sListSoft.Split(new char[] { ',' });
		}


		/// <summary>
		/// Ottiene la lista dei codici software abilitati dell'utente corrente
		/// </summary>
		/// <returns></returns>
		private string[] GetListaCodiciSoftwareDellUtente()
		{

			ArrayList list = new ResponsabiliSoftwareMgr(Database).GetList(new ResponsabiliSoftware
			{
				IDCOMUNE = IdComune,
				CODICERESPONSABILE = AuthenticationInfo.CodiceResponsabile.ToString()
			});


			var rVal = new string[list.Count];
			for (int iCount = 0; iCount < list.Count; iCount++)
			{
				rVal[iCount] = ((ResponsabiliSoftware)list[iCount]).SOFTWARE;
			}

			return rVal;
		}

        protected void Page_Load(object sender, EventArgs e)
        {
            //this.PreRender += new EventHandler(GeoIn_PreRender);
            //this.Init += new EventHandler(GeoIn_Init);


            Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;

            if (!IsPostBack)
            {
                switch (Contesto)
                {
                    case "Stradario":
                        lblViaFoglio.Descrizione = "Via: ";
                        lblCivicoParticella.Descrizione = "Civico: ";
                        lblColoreCatasto.Descrizione = "Colore: ";
                        break;

                    case "Mappale":
                        lblColoreCatasto.Descrizione = "Catasto: ";
                        lblViaFoglio.Descrizione = "Foglio: ";
                        lblCivicoParticella.Descrizione = "Particella: ";
                        break;
                }

                lblViaFoglio.Item.Text			= Via_Foglio;
                lblCivicoParticella.Item.Text	= Civico_Particella;
                lblColoreCatasto.Item.Text		= Colore_Catasto;
            }


            /*
            //Includo il file GeoIn.js
            if (!Page.ClientScript.IsClientScriptIncludeRegistered(this.GetType(), "GeoIn"))
            {
                Page.ClientScript.RegisterClientScriptInclude(this.GetType(), "GeoIn", "GeoIn.js");
            }

            //Registro lo script che carica il plug-in cartografico
            if (!Page.ClientScript.IsStartupScriptRegistered(this.GetType(), "LoadPlugIn"))
            {
                StringBuilder cstext2 = new StringBuilder();
                cstext2.Append("_gis_plugin = new tabula_map();");
                cstext2.Append("_gis_plugin.init('web_gis');");
                cstext2.Append("_gis_plugin.set_proxy_url('http://suapsrv:8080/proxy.jsp');");
                cstext2.Append("_gis_plugin.manage_layout();");
                cstext2.Append("attach_signaler();");
                cstext2.Append("getQueryString();");
                cstext2.Append("switch (param4){");
                cstext2.Append("case 'Stradario' :");
                cstext2.Append("if( param3 == 'R' )");
                cstext2.Append("param2 = param2+ ' ' + param3;");
                cstext2.Append("_gis_plugin.find_via_civico(param1,param2, searcher.STRICT_MODE); break;");
                cstext2.Append("case 'Mappale' :");
                cstext2.Append("_gis_plugin.find_foglio_particella(param1,param2, searcher.STRICT_MODE); break}");
                Page.ClientScript.RegisterStartupScript(this.GetType(), "LoadPlugIn", cstext2.ToString(), true);
            }
            */
        }

        /*
        void GeoIn_PreRender(object sender, EventArgs e)
        {
            // Aggiunge il foglio di stile
            HtmlLink css = new HtmlLink();
            css.Href = "~/Istanze/SIT/GeoIn.css";
            css.Attributes["rel"] = "stylesheet";
            css.Attributes["type"] = "text/css";

            Page.Header.Controls.Add(css);
        }


        void GeoIn_Init(object sender, EventArgs e)
        {
            //Verifico a quanti software e abilitato l'operatore
            string[] listSoft;
            if (new ResponsabiliMgr(Database).GetById(IdComune, AuthenticationInfo.CodiceResponsabile.Value).AMMINISTRATORE == "1")
            {
                //Elimino il software TT e splitto per la virgola
                string sListSoft = AuthenticationInfo.SoftwareAttivi.Replace(",TT", "");
                listSoft = sListSoft.Split(new char[] { ',' });
            }
            else
            {
                ResponsabiliSoftwareMgr respSoftMgr = new ResponsabiliSoftwareMgr(Database);
                ResponsabiliSoftware respSoft = new ResponsabiliSoftware();
                respSoft.IDCOMUNE = IdComune;
                respSoft.CODICERESPONSABILE = AuthenticationInfo.CodiceResponsabile.ToString();
                ArrayList list = respSoftMgr.GetList(respSoft);
                listSoft = new string[list.Count];
                for (int iCount = 0; iCount < list.Count; iCount++)
                {
                    listSoft[iCount] = ((ResponsabiliSoftware)list[iCount]).SOFTWARE;
                }
            }

            foreach (string elem in listSoft)
            {
                LabeledCheckBox lblCheckBox = new LabeledCheckBox();
                SoftwareMgr softMgr = new SoftwareMgr(Database);
                Init.SIGePro.Data.Software soft = new Software();
                soft = softMgr.GetById(elem);
                lblCheckBox.Descrizione = "Layer " + soft.DESCRIZIONE;
                lblCheckBox.ID = "lbl" + soft.CODICE;
                //Aggiungo funzioni javascript ai controlli per la visualizzazione dei layer dei software ai quali
                //l'operatore è abilitato
                lblCheckBox.Attributes.Add("onclick", "visibility_layer('" + lblCheckBox.Item.ClientID + "','" + lblAttive.Item.ClientID + "','" + lblCessate.Item.ClientID + "','" + lblCheckBox.Item.ID.Substring(3) + "')");
                Fldset_Layer.Controls.Add(lblCheckBox);
            }

            //Aggiungo funzioni javascript ai controlli per la visualizzazione dei layer: cartografia e toponomastica
            lblCART.Attributes.Add("onclick", "visibility_layer('" + lblCART.Item.ClientID + lblAttive.Item.ClientID + "','" + lblCessate.Item.ClientID + "','" + lblCART.Item.ID.Substring(3) + "')");
            lblTOPO.Attributes.Add("onclick", "visibility_layer('" + lblTOPO.Item.ClientID + lblAttive.Item.ClientID + "','" + lblCessate.Item.ClientID + "','" + lblTOPO.Item.ID.Substring(3) + "')");

            //Aggiungo funzione javascript al controllo per la visualizzazione dei filtri di ricerca
            lblClearFiltro.Attributes.Add("onclick", "clear_elements('" + lblClearFiltro.Item.ClientID + "')");
        }
         */

    }
}
