<%@ Page Title="Parere pratica" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="parere-pratica.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Commissioni.parere_pratica" %>

<%@ MasterType VirtualPath="~/AreaRiservataMaster.Master" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<%@ Register Src="~/Reserved/CommonControls/Files/FileUploadControl.ascx" TagPrefix="uc1" TagName="FileUploadControl" %>


<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
        <asp:View runat="server">
            <script type="text/javascript">

                vbg.ready(() => {
                    document.getElementById('<%=cmdSalvaVoto.ClientID%>').addEventListener('click', (e) => {

                        fireValidatiors();

                        setTimeout(() => {

                            if (document.querySelectorAll('.has-error').length === 0) {
                                vbg.mostraModal('<%=bmConfermaParere.ClientID%>');
                            }
                        }, 100);


                        e.preventDefault();
                    });
                });
            </script>

            <div class="row">
                <ar:DropDownList runat="server" ID="ddlParere" Label="Parere" BtSize="Col6" Required="true" />
            </div>

            <div class="row">
                <ar:TextBox runat="server" TextMode="MultiLine" ID="txtNote" Label="Note" BtSize="Col6" CssClass="limita-lunghezza" />
            </div>

            <div class="row">
                <uc1:FileUploadControl runat="server" ID="fileParere" CssClass="col-md-6" />
            </div>

            <div>
                <asp:LinkButton runat="server" ID="cmdSalvaVoto" Text="Conferma parere" CssClass="btn btn-primary" />
                <asp:LinkButton runat="server" ID="cmdChiudi" Text="Chiudi" CssClass="btn btn-default vbg-show-spinner" OnClick="cmdChiudi_Click" />
            </div>

            <ar:BootstrapModal runat="server" ID="bmConfermaParere" Title="Conferma parere espresso" 
                OkText="Conferma e invia parere" KoText="Annulla e modifica il parere"
                OkCssClass="vbg-show-spinner-if-valid"
                OnOkClicked="cmdSalvaVoto_Click">
                <ModalBody>
                    <ar:RisorsaTestualeLabel runat="server" IdRisorsa="parere-commissione.messaggio.conferma-parere">
                        <b>Attenzione!</b> Il parere può essere espresso una sola volta e non potrà essere modificato.<br />
                        <br />
                        Confermi l'invio del parere?
                    </ar:RisorsaTestualeLabel>
                </ModalBody>
            </ar:BootstrapModal>
        </asp:View>

        <asp:View runat="server">
            <div class="alert alert-success">
                <ar:RisorsaTestualeLabel runat="server" IdRisorsa="parere-commissione.messaggio.salvataggio-riuscito">
                Parere salvato con successo, premi "Chiudi" per tornare alla pratica.
                </ar:RisorsaTestualeLabel>
            </div>

            <asp:LinkButton runat="server" ID="LinkButton1" Text="Chiudi" CssClass="btn btn-default vbg-show-spinner" OnClick="cmdChiudi_Click" />
        </asp:View>

    </asp:MultiView>

</asp:Content>
