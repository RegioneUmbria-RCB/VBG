using Init.Sigepro.FrontEnd.AppLogic.CopiaDomanda;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CopiaDaTemplate
{
    public partial class CopiaDaTemplate : ReservedBasePage
    {

        [Inject]
        protected CopiaDomandaService _copiaDomandaService { get; set; }

        protected QsUuidIstanza UsaComeTemplate
        {
            get { return new QsUuidIstanza(Request.QueryString); }
        }

        protected QsAliasComune Alias
        {
            get { return new QsAliasComune(Request.QueryString); }
        }

        protected QsSoftware Software
        {
            get { return new QsSoftware(Request.QueryString); }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                var idDomanda = this._copiaDomandaService.CopiaDaUUIDTemplate(this.UsaComeTemplate.ParameterStringValue);

                var url = UrlBuilder.Url("~/reserved/InserimentoIstanza/Benvenuto.aspx", qs =>
                {
                    qs.Add(Alias);
                    qs.Add(Software);
                    qs.Add(new QsIdDomandaOnline(idDomanda));
                });

                Response.Redirect(url);
            }
        }
    }
}

