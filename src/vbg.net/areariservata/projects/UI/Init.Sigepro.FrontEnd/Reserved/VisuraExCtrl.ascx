<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="VisuraExCtrl.ascx.cs"
    Inherits="Init.Sigepro.FrontEnd.Reserved.VisuraExCtrl" %>

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

<style>
    .popup-visura > .modal-dialog {
        width: 90%;
    }
</style>
<div>
    <asp:Literal ID="ltrIntestazioneDettaglio" runat="server"></asp:Literal>

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
                <uc1:datigenerali runat="server" ID="datiGenerali" />
                <uc1:visurasoggetti runat="server" ID="visuraSoggetti" />

                <%if (!DaArchivio)  {%>
                <div>
                    <h3><ar:RisorsaTestualeLabel runat="server" ID="titoloMovimenti" ValoreDefault="Movimenti" /></h3>
                    <uc1:visuramovimenti runat="server" ID="visuraMovimenti" />
                </div>
                <%} %>
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

            <div role="tabpanel" class="tab-pane" id="<%=this.ClientID %>endoprocedimenti">
                <uc1:visuraendoprocedimenti runat="server" ID="visuraEndoprocedimenti" />
            </div>

            <div role="tabpanel" class="tab-pane" id="<%=this.ClientID %>oneri">
                <uc1:visuraoneri runat="server" ID="visuraOneri" />
            </div>

            <div role="tabpanel" class="tab-pane" id="<%=this.ClientID %>autorizzazioni">
                <uc1:visuraautorizzazioni runat="server" ID="visuraAutorizzazioni" />
            </div>

            <div role="tabpanel" class="tab-pane" id="<%=this.ClientID %>scadenze">
                <asp:GridView GridLines="None" runat="server" ID="dgScadenze" DataKeyNames="CodiceScadenza" CssClass="table"
                    AutoGenerateColumns="false" OnSelectedIndexChanged="dgScadenze_SelectedIndexChanged">
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
                        <%--<asp:ButtonField Text="Effettua movimento" CommandName="Select"></asp:ButtonField>--%>
                    </Columns>
                    <EmptyDataTemplate>
                        <div class="alert alert-info">Non sono presenti scadenze per la pratica corrente</div>
                    </EmptyDataTemplate>
                </asp:GridView>
            </div>
        </div>
    </div>
</div>


