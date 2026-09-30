<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="GestioneDelegaATrasmettere-file-view.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneDelegaATrasmettere_file_view" %>

    <script type="text/javascript">
        $(function () {
            $('#<%=cmdUpload.ClientID%>').on('click', function () {
                $('#caricamentoFileIncorso').modal('show');
            });
        });
    </script>

<%if (this.DataSource != null) {%>
    <div class="form-group has-feedback <%= this.DataSource.MostraAvvertimentoFirma ? "has-error" : "has-success"%>">
        <a href="<%=ResolveClientUrl(this.DataSource.LinkDownload)%>" target="_blank" class="form-control">
            <i class="glyphicon glyphicon-eye-open"></i>
            <%=this.DataSource.NomeFile %>
        </a>
        
        <%if (this.DataSource.MostraAvvertimentoFirma)  { %>
            <span class="glyphicon glyphicon-exclamation-sign form-control-feedback" aria-hidden="true"></span>

            <div class="help-block with-errors">
                Attenzione, il file deve essere firmato digitalmente
            </div>
        <%} else {%>
            <span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
        <%} %>
    </div>

    <div class="spacer"></div>

    <%if (this.DataSource.MostraAvvertimentoFirma)  { %>
        <asp:Button runat="server" ID="cmdFirma" Text="Firma" CssClass="btn btn-primary"
                OnClick="cmdFirma_Click" />
    <%} %>
    <asp:Button runat="server" ID="cmdEliminaDelega" Text="Sostituisci file" CssClass="btn btn-danger"
        OnClientClick="return confirm('Sostituire il file caricato\?')"
        OnClick="cmdEliminaDelega_Click" />
<%} %>

<%if (this.DataSource == null) {%>
    <asp:FileUpload runat="server" ID="fileUpload" CssClass="form-control" />

    <div class="spacer"></div>

    <asp:Button runat="server" ID="cmdUpload" Text="Invia file" CssClass="btn btn-primary"
        OnClick="cmdUpload_Click" />

    <script type="text/javascript">
        $(function () {
            $('#<%=fileUpload.ClientID%>').on('change', function () {
                $('#<%=cmdUpload.ClientID%>')[0].click();
            });
        });
    </script>
<%} %>