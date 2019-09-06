<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="visura-endoprocedimenti.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Visura.visura_endoprocedimenti" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<script>
    $(function() {
        var urlAllegatiEndo = '<%=UrlAllegatiEndo%>'; 

        $(".allegati-procedimenti").click(function (e) {

            var el = $(this),
                idEndo = el.data("idProcedimento"),
                codiceIstanza = el.data("codiceIstanza"),
                url = '<%=UrlAllegatiEndo%>&Istanza=' + codiceIstanza + "&endo=" + idEndo,
                modal = $("#<%=bmAllegatiEndo.ClientID%>")
                panel = modal.find(".lista-allegati-endo");

            panel.load(url, function () {
                modal.modal("show");
            });

            e.preventDefault();
        });
    });
</script>

<asp:GridView GridLines="None" runat="server" ID="dgProcedimenti" CssClass="table" AutoGenerateColumns="false">
    <Columns>
        <asp:TemplateField HeaderText="Procedimento">
            <ItemTemplate>
                <asp:Literal runat="server" ID="ltrProcedimento" Text='<%# DataBinder.Eval(Container.DataItem , "Endoprocedimento")%>' />
            </ItemTemplate>
        </asp:TemplateField>
        <asp:TemplateField>
            <ItemTemplate>
                <asp:LinkButton runat="server" 
                    Text='<%# Eval("NumeroAllegati", "{0} allegati") %>' 
                    Visible='<%# Eval("HaAllegati") %>' 
                    CssClass="allegati-procedimenti" 
                    data-id-procedimento='<%# Eval("Id") %>'
                    data-codice-istanza='<%# Eval("CodiceIstanza") %>'/>
            </ItemTemplate>
        </asp:TemplateField>
    </Columns>
    <EmptyDataTemplate>
        <div class="alert alert-info">Nella pratica non sono presenti endoprocedimenti attivati</div>
    </EmptyDataTemplate>
</asp:GridView>

<ar:BootstrapModal runat="server" ID="bmAllegatiEndo" Title="Allegati dell'endoprocedimento" ShowOkButton="false">
    <ModalBody>
        <div class="lista-allegati-endo"></div>
    </ModalBody>
</ar:BootstrapModal>
