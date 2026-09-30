<%@ Page Title="Dettaglio pratica" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="dettaglio-pratica.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Commissioni.dettaglio_pratica" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
    <style>
        .descrizione-allegato {
            width: 600px;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
        }

        #<%=lblParereEsteso.Inner.ClientID%>,
        #<%=lblIntervento.Inner.ClientID%>,
        #<%=lblLavori.Inner.ClientID%>,
        #<%=lblNoteVoto.Inner.ClientID%>
        {
            white-space: pre-line; 
        }
    </style>

    <script type="text/javascript">
        $(() => {
            document.querySelectorAll(".descrizione-allegato").forEach(item => {
                item.setAttribute("title", item.innerText);
            });
        });

    </script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    <fieldset>
        <legend>
            <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettaglio-pratica.legend-pratica">Dati pratica</ar:RisorsaTestualeLabel>
        </legend>
        <div class="row">
            <ar:LabeledLabel runat="server" ID="lblComune" Label="Comune" BtSize="Col4" />
            <ar:LabeledLabel runat="server" ID="lblNumero" Label="Numero" BtSize="Col4" />
            <ar:LabeledLabel runat="server" ID="lblProtocollo" Label="Protocollo" BtSize="Col4" />
        </div>

        <div class="row">
            <ar:LabeledLabel runat="server" ID="lblRichiedente" Label="Richiedente" BtSize="Col12" />
        </div>
        <div class="row">
            <ar:LabeledLabel runat="server" ID="lblIntervento" Label="Intervento" BtSize="Col12" />
        </div>
        <div class="row">
            <ar:LabeledLabel runat="server" ID="lblLavori" Label="Descrizione lavori" BtSize="Col12" />
        </div>
    </fieldset>

    <fieldset runat="server" id="fsLocalizzazioni">
        <legend>
            <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettaglio-pratica.legend-localizzazioni">Localizzazioni</ar:RisorsaTestualeLabel>
        </legend>

        <asp:GridView runat="server" ID="gvLocalizzazioni" GridLines="None" CssClass="table">
        </asp:GridView>
    </fieldset>

    <fieldset runat="server" id="fsParere">
        <legend>
            <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettaglio-pratica.legend-esito">Esito</ar:RisorsaTestualeLabel>
        </legend>

        <div class="row">
            <ar:LabeledLabel runat="server" ID="lblDescrizioneParere" Label="Tipologia parere" BtSize="Col6" />
            <ar:LabeledLabel runat="server" ID="lblParereEsteso" Label="Parere" BtSize="Col6" />
        </div>
    </fieldset>

    <fieldset runat="server" id="fsParereEspresso">
        <legend>
            <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettaglio-pratica.legend-parere-espresso">Parere espresso</ar:RisorsaTestualeLabel>
        </legend>

        <div class="row">
            <ar:LabeledLabel runat="server" ID="lblVotoEspresso" Label="Voto" BtSize="Col4" />
            <ar:LabeledLabel runat="server" ID="lblNoteVoto" Label="Note" BtSize="Col4" />
            <ar:LabeledLabel runat="server" ID="lblProtocolloVoto" Label="Protocollo" BtSize="Col4" />
        </div>
    </fieldset>

    <fieldset runat="server" id="fsDocumenti">
        <legend>
            <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettaglio-pratica.legend-documenti">Documenti della pratica</ar:RisorsaTestualeLabel>
        </legend>

        <asp:GridView runat="server" ID="gvDocumenti" GridLines="None" CssClass="table" AutoGenerateColumns="false"
            DataKeyNames="CodiceOggetto, FirmatoDigitalmente" OnSelectedIndexChanged="gvDocumenti_SelectedIndexChanged">
            <Columns>
                <asp:BoundField DataField="Categoria" HeaderText="Categoria" />
                <asp:TemplateField HeaderText="Descrizione">
                    <ItemTemplate>
                        <div class="descrizione-allegato">
                            <%#Eval("Descrizione") %>
                        </div>
                    </ItemTemplate>
                </asp:TemplateField>
                <asp:BoundField DataField="NomeFile" HeaderText="Nome File" />
                <asp:ButtonField HeaderText="Azioni" ButtonType="Link" Text="Scarica" CommandName="Select" />
            </Columns>
        </asp:GridView>
    </fieldset>

    <%--<asp:BoundField DataField="CodiceOggetto" HeaderText="Categoria" />
                <asp:BoundField DataField="FirmatoDigitalmente" HeaderText="Categoria" />
                <asp:BoundField DataField="Uid" HeaderText="Categoria" />--%>

    <div>
        <asp:Button runat="server" ID="cmdParere" Text="Esprimi parere" CssClass="btn btn-primary vbg-show-spinner" OnClick="lnkParere_Click" />
        <asp:LinkButton Text="Chiudi" CssClass="btn btn-default vbg-show-spinner" runat="server" ID="cmdChiudi" OnClick="cmdChiudi_Click" />
    </div>
</asp:Content>
