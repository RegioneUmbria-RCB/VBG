<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="visura-movimenti.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Visura.visura_movimenti" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<script>
    $(() => {

        $("#<%=this.ClientID%> .effettua-sub-visura").on("click", function effettuaSubvisura(e) {

            var el = $(this),
                modal = $("#<%=bmSubVisuraPratica.ClientID%>"),
                uuid = el.data("idPraticaCollegata"),
                urlVisura = "<%=UrlPopupVisura%>&uuid-pratica=" + uuid,
                corpoModal = modal.find(".segnaposto-sub-visura"),
                loader = el.find('.loader');

            loader.css('display', 'inline-block');
            modal.find('.modal-dialog').css('width', el.closest('.container').css('width'));

            corpoModal.load(urlVisura, function mostraVisura() {

                loader.css('display', 'none');
                modal.modal("show");
            });

            e.preventDefault();
        });


    });
</script>

<div id="<%=this.ClientID %>">
    <asp:GridView GridLines="None" runat="server" ID="dgMovimenti" CssClass="table" AutoGenerateColumns="false" OnRowDataBound="dgMovimenti_RowDataBound">
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
                    
                    <asp:Repeater runat="server" ID="rptAllegatiMovimento">
                        <HeaderTemplate>

                            <div class="dropdown">
                                <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                                    <asp:Literal runat="server" ID="ltrNumeroAllegati" />
                                    <span class="caret"></span>
                                </button>

                                <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                        </HeaderTemplate>

                        <ItemTemplate>

                            <li class="nome-allegato-endo">
                                <%# Eval("Descrizione") %>
                            </li>
                            <li class="download-allegato-endo">
                                <a href="<%# Eval("UrlDownload") %>" target="_blank">
                                    <%# Eval("NomeFile") %>
                                    <%# Eval("Md5", ", MD5: {0}")%>
                                </a>
                            </li>

                        </ItemTemplate>

                        <FooterTemplate>
                            </ul>
                                </div>
                        </FooterTemplate>
                    </asp:Repeater>

                </ItemTemplate>
            </asp:TemplateField>

            <asp:TemplateField HeaderText="Pratiche collegate">
                <ItemTemplate>
                    <a href="#" runat="server" class="effettua-sub-visura" data-id-pratica-collegata='<%# Eval("UuidPraticaCollegata") %>' visible='<%# Eval("HaPraticaCollegata") %>'>
                        <i class="glyphicon glyphicon-new-window"></i>Visualizza
                        <div class="loader" style="display: none"></div>
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
