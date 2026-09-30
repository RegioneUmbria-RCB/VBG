//using Init.SIGePro.Data;
//using Init.SIGePro.Manager;
//using SIGePro.Manager.Verticalizzazioni;
//using SIGePro.Manager.VerticalizzazioniSIT;
//using System;
//using System.Web;
//using System.Web.Services;

//namespace Sigepro.net.Istanze.SIT.Handlers
//{
//    /// <summary>
//    /// Summary description for $codebehindclassname$
//    /// </summary>
//    [WebService(Namespace = "http://tempuri.org/")]
//    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
//    public class GeoInUpdateAttivitaIstanze : BaseHandler, IHttpHandler
//    {

//        protected override void DoProcessRequest(HttpContext context)
//        {
//            context.Response.ContentType = "text/plain";
//            try
//            {
//                string result = string.Empty;
//                string software = HttpContext.Current.Request.QueryString["Software"];
//                string codice = HttpContext.Current.Request.QueryString["Codice"];
//                string key = HttpContext.Current.Request.QueryString["Key"];
//                string tipoIntervento = HttpContext.Current.Request.QueryString["TipoIntervento"];
//                IstanzeStradarioMgr istanzeStradarioMgr = new IstanzeStradarioMgr(this.Database);
//                IstanzeStradario istanzeStradario = new IstanzeStradario();

//                switch (tipoIntervento)
//                {
//                    case "add":
//                        //istanzeStradario.PANELKEY = vertSit.PanelKey;
//                        //istanzeStradario.LAYERKEY = vertSit.LayerKey;
//                        //Perchè la GetById non riceve IdComune??????
//                        //istanzeStradario.RENDERERKEY = attMgr.GetById(Convert.ToInt32(codice)).ATTIVA == "1" ? vertSit.RendererKeyAttivo : vertSit.RendererKeyCessato ;
//                        //istanzeStradario.KEY = key;
//                        //istanzeStradario.IDCOMUNE = IdComune;
//                        //istanzeStradario.CODICEISTANZA = Convert.ToInt32(codice);
//                        result = "Georeferenzazione: elemento aggiunto correttamente";
//                        break;
//                    case "remove":
//                        //att.PANELKEY = null;
//                        //att.LAYERKEY = null;
//                        //Perchè la GetById non riceve IdComune??????
//                        //att.RENDERERKEY = null ;
//                        //att.KEY = null;
//                        //att.IDCOMUNE = IdComune;
//                        //att.ID = Convert.ToInt32(codice);
//                        result = "Georeferenzazione: elemento eliminato correttamente";
//                        break;
//                }


//                VerticalizzazioneSitQuaestioflorenzia vertSit = new VerticalizzazioneSitQuaestioflorenzia(this.AuthenticationInfo.Alias, software);
//                if (vertSit.Attiva)
//                {
//                    VerticalizzazioneIAttivita vertAttivita = new VerticalizzazioneIAttivita(this.AuthenticationInfo.Alias, software);
//                    if (vertAttivita.Attiva)
//                    {
//                        IAttivita att = new IAttivita();
//                        IAttivitaMgr attMgr = new IAttivitaMgr(this.Database);

//                        switch (tipoIntervento)
//                        {
//                            case "add":
//                                //att.PANELKEY = vertSit.PanelKey;
//                                //att.LAYERKEY = vertSit.LayerKey;
//                                //Perchè la GetById non riceve IdComune??????
//                                //att.RENDERERKEY = attMgr.GetById(Convert.ToInt32(codice)).ATTIVA == "1" ? vertSit.RendererKeyAttivo : vertSit.RendererKeyCessato ;
//                                //att.KEY = key;
//                                //att.IDCOMUNE = IdComune;
//                                //att.ID = Convert.ToInt32(codice);
//                                result = "Georeferenzazione: elemento aggiunto correttamente";
//                                break;
//                            case "remove":
//                                //att.PANELKEY = null;
//                                //att.LAYERKEY = null;
//                                //Perchè la GetById non riceve IdComune??????
//                                //att.RENDERERKEY = null ;
//                                //att.KEY = null;
//                                //att.IDCOMUNE = IdComune;
//                                //att.ID = Convert.ToInt32(codice);
//                                result = "Georeferenzazione: elemento eliminato correttamente";
//                                break;
//                        }
//                        //attMgr.Update(att);
//                    }
//                    else
//                    {
//                        //Gestione caso in cui non vengono gestite le attività
//                    }
//                }
//                else
//                    throw new Exception("La verticalizzazione QuaestioFlorentia non è attiva");

//                context.Response.Write(result);
//            }
//            catch (Exception ex)
//            {
//                context.Response.StatusCode = 500;
//                context.Response.Write("Error: " + ex.ToString());
//            }
//        }

//    }
//}
