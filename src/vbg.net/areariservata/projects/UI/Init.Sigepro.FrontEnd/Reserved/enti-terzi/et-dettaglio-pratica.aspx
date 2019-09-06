<%@ Page Title="Dettaglio pratica" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="et-dettaglio-pratica.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.enti_terzi.et_dettaglio_pratica" %>


<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<%@ Register Src="~/Reserved/Visura/schede-dinamiche-readonly.ascx" TagPrefix="uc1" TagName="schededinamichereadonly" %>
<%@ Register Src="~/Reserved/Visura/dati-generali.ascx" TagPrefix="uc1" TagName="datigenerali" %>
<%@ Register Src="~/Reserved/Visura/visura-soggetti.ascx" TagPrefix="uc1" TagName="visurasoggetti" %>
<%@ Register Src="~/Reserved/Visura/visura-localizzazioni.ascx" TagPrefix="uc1" TagName="visuralocalizzazioni" %>
<%@ Register Src="~/Reserved/Visura/visura-documenti.ascx" TagPrefix="uc1" TagName="visuradocumenti" %>
<%@ Register Src="~/Reserved/Visura/visura-endoprocedimenti.ascx" TagPrefix="uc1" TagName="visuraendoprocedimenti" %>
<%@ Register Src="~/Reserved/Visura/visura-oneri.ascx" TagPrefix="uc1" TagName="visuraoneri" %>
<%@ Register Src="~/Reserved/Visura/visura-movimenti.ascx" TagPrefix="uc1" TagName="visuramovimenti" %>
<%@ Register Src="~/Reserved/Visura/visura-autorizzazioni.ascx" TagPrefix="uc1" TagName="visuraautorizzazioni" %>


<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">

    <script type="text/javascript">    
        $(function onLoad() {
            $("#<%=cmdMarcaComeElaborata.ClientID%>, #<%=cmdMarcaComeNonElaborata.ClientID%>").on("click", mostraModalCaricamento);
        });

    </script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
       <div class="tabs-container">


        <asp:Repeater runat="server" ID="rptTabs">
            <HeaderTemplate>
                <ul class="nav nav-tabs" role="tablist">
            </HeaderTemplate>

            <ItemTemplate>
                <li runat="server" role="presentation" class='<%# (bool)Eval("IsActive") ? "active" : String.Empty %>'>
                    <a runat="server" href='<%# "#" + this.ClientID + Eval("Id").ToString() %>' aria-controls='<%#Eval("Id") %>' role="tab" data-toggle="tab">
                        <%# Eval("Descrizione") %>

                        <span class="badge" runat="server" visible=' <%# Eval("HasBadge") %>'><%# Eval("ValoreBadge") %></span>
                    </a>
                </li>
            </ItemTemplate>

            <FooterTemplate>
                </ul>
            </FooterTemplate>
        </asp:Repeater>

        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="<%=this.ClientID %>dati-generali">

                <asp:Panel runat="server" ID="pnlPraticaElaborata" Visible="false">
                    <div class="alert alert-info">
                        Pratica contrassegnata come elaborata
                    </div>
                </asp:Panel>

                <uc1:datigenerali runat="server" ID="datiGenerali" />
                <uc1:visurasoggetti runat="server" ID="visuraSoggetti" />
            </div>
            <div role="tabpanel" class="tab-pane" id="<%=this.ClientID %>localizzazioni">
                <uc1:visuralocalizzazioni runat="server" ID="visuraLocalizzazioni" />
            </div>
            <div role="tabpanel" class="tab-pane" id="<%=this.ClientID %>schede">
                <uc1:schededinamichereadonly runat="server" ID="schedeDinamicheReadonly" />
            </div>
            <div role="tabpanel" class="tab-pane" id="<%=this.ClientID %>documenti">
                <uc1:visuradocumenti runat="server" ID="visuraDocumenti" />
            </div>

            <div role="tabpanel" class="tab-pane" id="<%=this.ClientID %>scadenze">
                <asp:GridView GridLines="None" runat="server" ID="dgScadenze" DataKeyNames="CodiceScadenza" CssClass="table"
                    AutoGenerateColumns="false">
                    <Columns>
                        <asp:BoundField DataField="DatiMovimento" HeaderText="Movimento precedente"></asp:BoundField>
                        <asp:BoundField DataField="DescrMovimentoDaFare" HeaderText="Movimento da fare"></asp:BoundField>
                        <asp:BoundField DataField="DataScadenza" HeaderText="Scadenza" DataFormatString="{0:dd/MM/yyyy}"
                            HtmlEncode="false"></asp:BoundField>
                        <asp:BoundField DataField="Procedura" HeaderText="Procedura"></asp:BoundField>
                        <asp:TemplateField>
                            <ItemTemplate>
                                <asp:HyperLink runat="server" NavigateUrl='<%# GetUrlMovimento(DataBinder.Eval(Container.DataItem, "CodiceScadenza")) %>'>
                                    Effettua movimento
                                </asp:HyperLink>
                            </ItemTemplate>
                        </asp:TemplateField>
                    </Columns>
                    <EmptyDataTemplate>
                        <div class="alert alert-info">Non sono presenti scadenze per la pratica corrente</div>
                    </EmptyDataTemplate>
                </asp:GridView>
            </div>
        </div>
    </div>

    <div>
        <asp:Button Text="Chiudi" runat="server" ID="cmdChiudi" OnClick="cmdChiudi_Click" CssClass="btn btn-default" />
        <asp:Button Text="Contrassegna come non elaborata" runat="server" ID="cmdMarcaComeNonElaborata" OnClick="cmdMarcaComeNonElaborata_Click" CssClass="btn btn-primary" />
        <asp:Button Text="Contrassegna come elaborata" runat="server" ID="cmdMarcaComeElaborata" OnClick="cmdMarcaComeElaborata_Click" CssClass="btn btn-primary" />
    </div>
</asp:Content>
