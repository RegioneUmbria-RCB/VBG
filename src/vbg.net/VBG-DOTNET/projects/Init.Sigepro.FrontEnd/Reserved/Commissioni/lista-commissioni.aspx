<%@ Page Title="Elenco commissioni/conferenze aperte" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="lista-commissioni.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Commissioni.lista_commissioni" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">

    <script type="text/javascript">

        vbg.ready(() => {
            document.getElementById('accedi-con-pin').addEventListener('click', (e) => {

                document.getElementById('<%=txtPin.Inner.ClientID%>').value = '';
                vbg.mostraModal('<%=bmAccediConPin.ClientID%>');
            })
        });

    </script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    <asp:GridView runat="server" ID="gvCommissioni" CssClass="table" GridLines="None" 
        AutoGenerateColumns="false" DataKeyNames="Id" OnSelectedIndexChanged="gvCommissioni_SelectedIndexChanged">
        <Columns>            
            <asp:BoundField DataField="Numero" HeaderText="Numero" />
            <asp:BoundField DataField="Descrizione" HeaderText="Descrizione" />
            <asp:BoundField DataField="Tipologia" HeaderText="Tipologia" />
            <asp:BoundField DataField="Data" HeaderText="Data" />
            <asp:BoundField DataField="OraInizio" HeaderText="Ora inizio" />
            <asp:BoundField DataField="OraFine" HeaderText="Ora fine" />
            <asp:TemplateField HeaderText="Modalità">
                <ItemTemplate>
                    <%#((bool)Eval("Asincrona")) ? "Asincrona": "Sincrona" %>
                </ItemTemplate>
            </asp:TemplateField>
            <asp:ButtonField CommandName="Select" Text="Partecipa <i class='fa fa-chevron-right'></i>" HeaderText="Azioni" ControlStyle-CssClass="vbg-show-spinner" />
        </Columns>
        <EmptyDataTemplate>
            <div class="alert alert-info">
                <ar:RisorsaTestualeLabel runat="server" IdRisorsa="lista-commissioni.messaggio.no-commissioni-trovate">
                    Non sono state trovate commissioni o conferenze a cui sei stato invitato. <br />
                    Se hai ricevuto un PIN di accesso utilizza la funzionalità "Accedi con PIN" per cercare la commissione
                </ar:RisorsaTestualeLabel>
            </div>
        </EmptyDataTemplate>
    </asp:GridView>

    <div>
        <div class="btn btn-primary" id="accedi-con-pin">Accedi con pin</div>
        <asp:LinkButton Text="Chiudi" CssClass="btn btn-default vbg-show-spinner" runat="server" ID="cmdChiudi" OnClick="cmdChiudi_Click" />
    </div>

    <ar:BootstrapModal runat="server" ID="bmAccediConPin" Title="Immetti PIN commissione" OnOkClicked="bmAccediConPin_OkClicked"
        OkCssClass="vbg-show-spinner-if-valid">
        <ModalBody>

            <ar:RisorsaTestualeLabel runat="server" IdRisorsa="lista-commissioni.messaggio.accedi-con-pin">
                Immetti il codice PIN della commissione a cui sei stato invitato
            </ar:RisorsaTestualeLabel>

            <ar:TextBox runat="server" ID="txtPin" Label="PIN" />
        </ModalBody>
    </ar:BootstrapModal>
</asp:Content>
