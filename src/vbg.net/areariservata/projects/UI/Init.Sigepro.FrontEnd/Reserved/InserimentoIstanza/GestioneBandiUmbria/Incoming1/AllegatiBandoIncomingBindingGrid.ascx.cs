using Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneBandiUmbria.Incoming1
{
    public partial class AllegatiBandoIncomingBindingGrid : System.Web.UI.UserControl
    {

        [Inject]
        protected PathUtils _pathUtils { get; set; }

        [Inject]
        public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }

        public class FileEventArgs : EventArgs
        {
            public string IdAllegato;
        }

        public class FileUploadedEventArgs : FileEventArgs
        {
            public BinaryFile File;
            public string CercaTag;
        }

        public delegate void FileUploadedDelegate(object sender, FileUploadedEventArgs e);
        public event FileUploadedDelegate FileUploaded;

        public delegate void FileDeletedDelegate(object sender, FileEventArgs e);
        public event FileDeletedDelegate FileDeleted;

        public delegate void ErroreDelegate(object sender, Exception e);
        public event ErroreDelegate Errore;

        public int IdDomanda
        {
            get
            {
                return Convert.ToInt32(Request.QueryString["IdPresentazione"]);
            }
        }


        public AziendaBando DataSource { get; set; }
        protected string NomeAzienda { get; set; }



        public AllegatiBandoIncomingBindingGrid()
        {
            FoKernelContainer.Inject(this);
        }

        protected void Page_Load(object sender, EventArgs e)
        {
        }

        protected void onFileUploaded(object sender, EventArgs e)
        {
            try
            {
                var button = (Button)sender;

                var fuAllegato = (FileUpload)button.NamingContainer.FindControl("fuAllegato");
                var hidId = (HiddenField)button.NamingContainer.FindControl("hidId");
                var cercaTag = (HiddenField)button.NamingContainer.FindControl("cercaTag");

                if (this.FileUploaded != null)
                {
                    this.FileUploaded(this, new FileUploadedEventArgs
                    {
                        File = new BinaryFile(fuAllegato, this._validPostedFileSpecification),
                        IdAllegato = hidId.Value,
                        CercaTag = cercaTag.Value
                    });
                }
            }
            catch (Exception ex)
            {
                if (this.Errore != null)
                    this.Errore(this, ex);
            }
        }

        protected void OnDeleteClicked(object sender, EventArgs e)
        {
            var lnkRimuoviAllegato = (LinkButton)sender;
            var file = lnkRimuoviAllegato.CommandArgument;

            if (this.FileDeleted != null)
            {
                this.FileDeleted(this, new FileEventArgs
                {
                    IdAllegato = file
                });
            }

        }

        public class DataBindingItem
        {
            public string Id { get; set; }
            public string Descrizione { get; set; }
            public int? IdModello { get; set; }
            public string NomeFile { get; set; }

            public bool HaModello { get; set; }
            public string UrlDownloadModello { get; set; }
            public bool HaAllegato { get; set; }
            public string UrlDownloadAllegatoConModello { get; set; }
            public string UrlDownloadAllegato { get; set; }
            public string TagModello { get; set; }

            public DataBindingItem(AllegatoDomandaBandi x, PathUtils pathUtils, int idDomanda, AziendaBando azienda)
            {
                this.Id = x.Id;
                this.Descrizione = x.Descrizione;
                this.IdModello = x.IdModello;
                this.NomeFile = x.NomeFile;
                this.HaModello = x.IdModello.HasValue;
                this.HaAllegato = x.IdAllegato.HasValue;
                this.TagModello = x.TagModello;

                if (this.HaModello)
                {
                    if (x.Categoria == CategoriaAllegatoBandoEnum.IncomingAllegato3)
                    {
                        this.UrlDownloadModello = pathUtils
                                                    .Reserved
                                                    .InserimentoIstanza
                                                    .GestioneBandi
                                                    .GetUrlDownloadPdfCompilabileAzienda(x.IdModello.Value, idDomanda, azienda.CodiceFiscale, azienda.PartitaIva);

                        if (this.HaAllegato)
                        {
                            this.UrlDownloadAllegatoConModello = pathUtils
                                                                    .Reserved
                                                                    .InserimentoIstanza
                                                                    .GestioneBandi
                                                                    .GetUrlDownloadPdfCompilabileAzienda( x.IdAllegato.Value, idDomanda, azienda.CodiceFiscale, azienda.PartitaIva);
                        }

                    }
                    else
                    {
                        this.UrlDownloadModello = pathUtils.Reserved.InserimentoIstanza.GetUrlDownloadPdfCompilabile(x.IdModello.Value, idDomanda);

                        if (this.HaAllegato)
                        {
                            this.UrlDownloadAllegatoConModello = pathUtils.Reserved.InserimentoIstanza.GetUrlDownloadPdfCompilabile(x.IdAllegato.Value, idDomanda);
                        }
                    }
                }

                if (x.IdAllegato.HasValue)
                {
                    this.UrlDownloadAllegato = pathUtils.Reserved.GetUrlMostraOggettoFo(x.IdAllegato.Value, idDomanda);
                }
            }
        }

        public override void DataBind()
        {
            this.Visible = this.DataSource != null;

            if (this.DataSource == null)
            {
                this.rptAllegati.DataSource = null;
                this.rptAllegati.DataBind();

                this.NomeAzienda = String.Empty;

                return;
            }

            this.NomeAzienda = this.DataSource.ToString();
            this.rptAllegati.DataSource = this.DataSource.Allegati.Select(x => new DataBindingItem(x, this._pathUtils, IdDomanda, DataSource));
            this.rptAllegati.DataBind();
        }
    }
}