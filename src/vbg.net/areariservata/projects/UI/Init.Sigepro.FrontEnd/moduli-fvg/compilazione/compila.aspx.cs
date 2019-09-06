using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.QsParameters.Fvg;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.DatiDinamici.WebControls.MaschereSolaLettura;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.moduli_fvg.compilazione
{
    public partial class compila : BasePage
    {
        private static class Constants
        {
            public const string UrlPaginaLista = "~/moduli-fvg/compilazione/lista.aspx";
        }


        [Inject]
        public IModelliDinamiciService _modelliService { get; set; }
        [Inject]
        public ServiziFVGService _fvgService { get; set; }


        protected QsFvgCodiceIstanza CodiceIstanza { get => new QsFvgCodiceIstanza(Request.QueryString); }
        protected QsFvgIdModulo IdModulo { get => new QsFvgIdModulo(Request.QueryString); }
        protected QsFvgIdScheda IdScheda { get => new QsFvgIdScheda(Request.QueryString); }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.MostraTitoloPagina = false;

            HttpContext.Current.Items["UserAuthenticationResult"] = UserAuthenticationResult.CreateFake(IdComune);

            if (!IsPostBack)
            {
                DataBind();
            }
        }

        public override void DataBind()
        {
            CaricaSchedaDinamica(this.IdScheda.Value, 0);

        }

        private void CaricaSchedaDinamica(int idScheda, int indiceScheda = 0)
        {
            var scheda = this._fvgService.GetModelloDinamico(CodiceIstanza.Value, IdModulo.Value, idScheda, indiceScheda);
            
            scheda.ModelloFrontoffice = true;

            this.Page.Title = scheda.NomeModello;
            

            scheda.EseguiScriptCaricamento();

            renderer.ImpostaMascheraSolaLettura(new MascheraSolaLetturaVuota());
            //renderer.RicaricaModelloDinamico += new SIGePro.DatiDinamici.WebControls.ModelloDinamicoRenderer.RicaricaModelloDinamicoDelegate(renderer_RicaricaModelloDinamico);
            renderer.DataSource = scheda;
            renderer.DataBind();

            /*
            paginatoreSchedeDinamiche.Visible = scheda.ModelloMultiplo;
            paginatoreSchedeDinamiche.IndiciSchede = ModelliDinamiciService.GetIndiciScheda(idDomanda, idScheda);
            paginatoreSchedeDinamiche.IndiceCorrente = indiceScheda;
            paginatoreSchedeDinamiche.DataBind();
            */
            //cmdSalvaEResta.Visible = scheda.ModelloMultiplo;
        }

        protected void cmdSalvaEContinua_Click(object sender, EventArgs e)
        {
            try
            {
                if (renderer.DataSource == null)
                    return;

                try
                {
                    renderer.DataSource.ValidaModello();
                }
                catch (ValidazioneModelloDinamicoException /*ex*/)
                {
                    MostraErroreSalvataggio();
                    return;
                }

                renderer.DataSource.Salva();

                VaiAllaSchedaSuccessiva();
            }
            catch (SalvataggioModelloDinamicoException)
            {
                MostraErroreSalvataggio();
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
        }



        protected void cmdSalva_Click(object sender, EventArgs e)
        {
            try
            {
                if (renderer.DataSource == null)
                    return ;

                try
                {
                    renderer.DataSource.ValidaModello();
                }
                catch (ValidazioneModelloDinamicoException /*ex*/)
                {
                    MostraErroreSalvataggio();
                    return;
                }

                renderer.DataSource.Salva();

                TornaAllaLista();
            }
            catch (SalvataggioModelloDinamicoException)
            {
                MostraErroreSalvataggio();
            }
            catch(Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
        }

        private void MostraErroreSalvataggio()
        {
            Page.ClientScript.RegisterStartupScript(this.GetType(), "notifica", "alert('Si sono verificati errori durante il salvataggio');", true);
        }

        protected void lnkChiudi_Click(object sender, EventArgs e)
        {
            TornaAllaLista();
        }

        private void VaiAllaSchedaSuccessiva()
        {
            var url = UrlBuilder.Url(Constants.UrlPaginaLista, x =>
            {
                x.Add(new QsAliasComune(this.IdComune));
                x.Add(new QsSoftware(this.Software));
                x.Add(this.CodiceIstanza);
                x.Add(this.IdModulo);
                x.Add(new QsFvgPassaASuccessiva(IdScheda.Value));
            });

            Response.Redirect(url);
        }

        private void TornaAllaLista()
        {
            var url = UrlBuilder.Url(Constants.UrlPaginaLista, x =>
            {
                x.Add(new QsAliasComune(this.IdComune));
                x.Add(new QsSoftware(this.Software));
                x.Add(this.CodiceIstanza);
                x.Add(this.IdModulo);
            });

            Response.Redirect(url);
        }
    }
}