<%@ Page Title="Dttaglio configurazione validità coefficienti" Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="CCValiditaCoefficientiDettaglio.aspx.cs" Inherits="Sigepro.net.Archivi.CalcoloOneri.CostoCostruzione.CCValiditaCoefficientiDettaglio" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<%@ Register TagPrefix="init" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils.Web" %>

<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.Ajax" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="bs" Namespace="SIGePro.WebControls.Bootstrap" Assembly="SIGePro.WebControls" %>


<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
    <script type="text/javascript">
        vbg.ready(() => {

            const modal = document.querySelector('#<%=bmCreaNuovo.ClientID%>');

            const showModal = (e) => {
                e.preventDefault();

                modal.show();

            }

            document.querySelector('#<%=cmdNew.ClientID%>').addEventListener('click', showModal);
        });

    </script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    <asp:GridView runat="server" AutoGenerateColumns="false" DataKeyNames="Id" ID="gvLista"
        OnRowDeleting="gvLista_RowDeleting"
        OnRowDataBound="gvLista_RowDataBound" style="min-width: 50vw">

        <Columns>
            <asp:BoundField DataField="Intervento" HeaderText="Intervento" />
            <asp:BoundField DataField="Destinazione" HeaderText="Destinazione" />
            <asp:BoundField DataField="CostoMq" ItemStyle-HorizontalAlign="Right" DataFormatString="{0:N2}" HeaderText="Costo Mq (€)" />
            <asp:TemplateField>
                <ItemTemplate>
                    <asp:LinkButton ID="cmdElimina" runat="server" Text="Elimina" CommandName="Delete"></asp:LinkButton>
                    <%--<asp:ImageButton  CommandName="Delete" ImageUrl="~/images/Delete.gif" />--%>
                </ItemTemplate>
            </asp:TemplateField>
        </Columns>
        <EmptyDataTemplate>
            Non sono stati trovati dati per questo listino
        </EmptyDataTemplate>
    </asp:GridView>

    <div class="Bottoni">
        <init:SigeproButton runat="server" ID="cmdNew" Text="Nuovo" OnClick="cmdNew_Click" />
        <init:SigeproButton runat="server" ID="cmdClose" Text="Chiudi" OnClick="cmdClose_Click" />
    </div>

    <bs:BootstrapModal runat="server" ID="bmCreaNuovo" Title="Nuovo importo"
        OnOkClicked="bmModificaRiduzioni_OkClicked"
        OnKoClicked="bmModificaRiduzioni_KoClicked">
        <ModalBody runat="server">
            <fieldset>
                <init:LabeledDropDownList ID="ddlIntervento" runat="server" Descrizione="Intervento" item-required="true" Item-DataTextField="Descrizione" Item-DataValueField="Id"/>
                <init:LabeledDropDownList ID="ddlDestinazione" runat="server" Descrizione="Destinazione" item-required="true" Item-DataTextField="Descrizione" Item-DataValueField="Id" />
                <init:LabeledDecimalTextBox ID="dtbImporto" runat="server" Descrizione="Importo" Columns="10" MaxLength="7" Required="true" />
            </fieldset>
        </ModalBody>
    </bs:BootstrapModal>
</asp:Content>
