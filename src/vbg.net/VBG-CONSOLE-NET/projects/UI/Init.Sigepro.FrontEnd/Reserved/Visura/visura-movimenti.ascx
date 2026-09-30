<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="visura-movimenti.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Visura.visura_movimenti" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<script>
    $(function () {
        $("#<%= this.ClientID%> .allegati-movimento").click(function (e) {

            var el = $(this),
                codiceIstanza = el.data("codiceIstanza"),
                idMovimento = el.data("idMovimento"),
                url = "<%=UrlAllegati%>&Istanza=" + codiceIstanza + "&movimento=" + idMovimento,
                modal = $("#<%=bmAllegatiMovimento.ClientID%>"),
                corpoModal = modal.find(".lista-allegati-movimento");
                
            corpoModal.load(url, function () {
                modal.modal("show");
                
            });


            e.preventDefault();
        });

        $("#<%=this.ClientID%> .effettua-sub-visura").on("click", function effettuaSubvisura(e) {

            var el = $(this),
                modal = $("#<%=bmSubVisuraPratica.ClientID%>"),
                uuid = el.data("idPraticaCollegata"),
                urlVisura = "<%=UrlPopupVisura%>&uuid-pratica=" + uuid,
                corpoModal = modal.find(".segnaposto-sub-visura"),
                loader = el.find('.loader');

            loader.css('display', 'inline-block');

            corpoModal.load(urlVisura, function mostraVisura() {

                loader.css('display', 'none');
                modal.modal("show");
            });

            e.preventDefault();
        });
        
        
    });
</script>

<div id="<%=this.ClientID %>">
    <asp:GridView GridLines="None" runat="server" ID="dgMovimenti" CssClass="table" AutoGenerateColumns="false">
        <Columns>
            <asp:BoundField DataField="Descrizione" HeaderText="Movimento" />
            <asp:BoundField DataField="Data" HeaderText="Data" ItemStyle-HorizontalAlign="Center"
                DataFormatString="{0:dd/MM/yyyy}" HtmlEncode="false" />
            <asp:BoundField DataField="Parere" HeaderText="Parere" />
            <asp:TemplateField HeaderText="Protocollo">
                <ItemTemplate>
                    <asp:Literal runat="server" ID="ltrNumeoProtocollo" Text='<%# Bind("NumeroProtocollo" , "n. {0}") %>' />
                    <asp:Literal runat="server" ID="ltrDataProtocollo" Text='<%# Bind("DataProtocollo"," del {0:dd/MM/yyyy}") %>' />
                </ItemTemplate>
            </asp:TemplateField>
            <%-- 
					    le note del movimento sono state rimosse il 13/04/2012 su richiesta del comune di como
					    in quanto nelle note potrebbero essere presenti informazioni che il cittadino non dovrebbe vedere
            --%>
            <%--<asp:BoundField DataField="Note" HeaderText="Note" />--%>
            <asp:TemplateField HeaderText="Allegati">
                <ItemTemplate>
                    <asp:LinkButton runat="server" CssClass="allegati-movimento"
                        Text='<%# Eval("NumeroAllegati", "{0} allegati") %>'
                        Visible='<%# Eval("HaAllegati") %>'
                        data-codice-istanza='<%# Eval("CodiceIstanza") %>'
                        data-id-movimento='<%# Eval("Id") %>' />
                </ItemTemplate>
            </asp:TemplateField>

            <asp:TemplateField HeaderText="Pratiche collegate">
                <ItemTemplate>
                    <a href="#" runat="server" class="effettua-sub-visura" data-id-pratica-collegata='<%# Eval("UuidPraticaCollegata") %>' visible='<%# Eval("HaPraticaCollegata") %>'>
                        <i class="glyphicon glyphicon-new-window"></i> Visualizza
                        <div class="loader" style="display:none"></div>
                    </a>
                </ItemTemplate>
            </asp:TemplateField>
        </Columns>
        <EmptyDataTemplate>
            <div class="alert alert-info">La pratica non contiene movimenti</div>
        </EmptyDataTemplate>
    </asp:GridView>
</div>
<ar:BootstrapModal runat="server" ID="bmAllegatiMovimento" ShowOkButton="false" Title="Allegati del movimento">
    <ModalBody>
        <div class="lista-allegati-movimento"></div>
    </ModalBody>
</ar:BootstrapModal>

<ar:BootstrapModal runat="server" ID="bmSubVisuraPratica" ShowOkButton="false" Title="Pratica collegata" ExtraCssClass="popup-visura">
    <ModalBody>
        <div class="segnaposto-sub-visura"></div>
    </ModalBody>
</ar:BootstrapModal>
