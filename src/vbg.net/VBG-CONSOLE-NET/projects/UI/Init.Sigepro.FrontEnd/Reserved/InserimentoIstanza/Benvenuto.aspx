<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="Benvenuto.aspx.cs"
    Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Benvenuto" Title="Untitled Page" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<%@ Register Src="~/Reserved/InserimentoIstanza/benvenuto-copia-da.ascx" TagPrefix="uc1" TagName="benvenutocopiada" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<asp:Content runat="server" ID="headContent" ContentPlaceHolderID="head">
    <script type="text/javascript">
        $(function () {
            $('#aspnetForm').validator();
        });
    </script>

</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">

    <asp:MultiView runat="server" ID="multiView"  ActiveViewIndex="1">
        <asp:View runat="server" ID="viewCopaDa">
                <uc1:benvenutocopiada runat="server" ID="formCopiaDati" />

                <asp:Button runat="server" ID="confermaCopia" CssClass="btn btn-primary" Text="Conferma" OnClick="confermaCopia_Click"/>
        </asp:View>
        <asp:View runat="server" ID="viewSelezionaComune">
            <div class="descrizioneStep">
                <asp:Literal runat="server" ID="ltrTestoListaStep"></asp:Literal>
            </div>


            <asp:Panel runat="server" ID="pnlSelezioneComune">
                <h2 style="width: 100%; text-align: center">
                    <asp:Literal runat="server" ID="lblComuniAssociati">Selezionare il comune per cui si vuole presentare l'istanza</asp:Literal>
                </h2>

                <div class="row">
                    <div class="col-md-4"></div>
                    <div class="col-md-4">
                        <div class="form-group">
                            <asp:DropDownList ID="cmbComuni" runat="server" CssClass="form-control" DataTextField="Value" DataValueField="Key" required />
                            <div class="help-block with-errors"></div>
                        </div>
                    </div>
                    <div class="col-md-4"></div>
                </div>
            </asp:Panel>
        </asp:View>

    </asp:MultiView>

</asp:Content>
