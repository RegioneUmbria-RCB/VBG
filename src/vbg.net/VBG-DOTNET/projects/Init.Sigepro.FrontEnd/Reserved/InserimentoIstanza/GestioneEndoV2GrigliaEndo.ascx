<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="GestioneEndoV2GrigliaEndo.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneEndoV2GrigliaEndo" %>
<div id="alberoEndo" class="albero-endo">
    <asp:Repeater runat="server" ID="rptFamiglieEndo">
        <HeaderTemplate>
            <%if (MostraFamiglia)
                { %><ul>
                    <%} %>
        </HeaderTemplate>

        <ItemTemplate>
            <%if (MostraFamiglia)
                { %>
            <li>
                <span class="famigliaEndo">
                    <asp:Literal runat="server" ID="ltrFamiglia" Text='<%# Eval("Descrizione") %>' />
                </span>
                <%} %>
                <asp:Repeater runat="server" ID="rptTipiEndo" DataSource='<%# Eval("TipiEndoprocedimenti") %>'>

                    <HeaderTemplate>
                        <% if (MostraTipoEndo)
                            {%><ul>
         <%} %>
                    </HeaderTemplate>

                    <ItemTemplate>

                        <% if (MostraTipoEndo)
                            {%>
                        <li>
                            <span class="tipoEndo">
                                <asp:Literal runat="server" ID="ltrTipoEndo" Text='<%# Eval("Descrizione") %>' />
                            </span>
                            <%} %>

                            <asp:Repeater runat="server" ID="rptEndo" DataSource='<%# Eval("Endoprocedimenti") %>' OnItemDataBound="rptEndo_ItemDataBound">

                                <HeaderTemplate>
                                    <ul>
                                </HeaderTemplate>

                                <ItemTemplate>
                                    <li>
                                        <div class="endo" data-id-endo='<%# Eval("Codice")%>'>
                                            <asp:HiddenField runat="server" ID="hidEndo" Value='<%# Eval("Codice")%>' />
                                            <asp:HiddenField runat="server" ID="hidPrincipale" Value='<%# Eval("Principale")%>' />

                                            <span style='display: <%= this.ModificaProcedimentiProposti ? "inline-block" : "none" %>'>
                                                <asp:CheckBox
                                                    runat="server"
                                                    ID="chkEndo"
                                                    Text='<%# Eval("Descrizione") %>' />
                                            </span>

                                            <span style='display: <%= !this.ModificaProcedimentiProposti ? "inline-block" : "none" %>'>
                                                <asp:CheckBox
                                                    runat="server"
                                                    ID="chkDummy"
                                                    Text='<%# Eval("Descrizione") %>'
                                                    Visible='<%# !ModificaProcedimentiProposti %>'
                                                    Checked="true"
                                                    Enabled="false" />
                                            </span>
                                            <%if (MostraInformazioniEndo){ %>
                                                <i class="fa fa-question-circle" aria-hidden="true" alt="Fare click per ulteriori informazioni" data-id-endo='<%# DataBinder.Eval(Container.DataItem,"Codice")%>'></i>
                                            <%} %>
                                            <asp:Repeater runat="server" ID="rptSubEndo" OnItemDataBound="rptSubEndo_ItemDataBound">
                                                <HeaderTemplate>
                                                    <ul class="dettaglio-sub-endo">
                                                </HeaderTemplate>

                                                <ItemTemplate>
                                                    <li class='sub-endo' data-id-endo='<%# Eval("Codice")%>'>
                                                        <asp:HiddenField runat="server" ID="hidIdEndo" Value='<%# Eval("Codice")%>' />
                                                        <asp:CheckBox
                                                            runat="server"
                                                            CssClass="chk-sub-endo"
                                                            ID="chkSelezionato"
                                                            Text='<%# Eval("Descrizione") %>'
                                                            data-richiesto='<%#Eval("Richiesto") %>'
                                                            data-id-endo='<%#Eval("Codice") %>' />

                                                        <%if (MostraInformazioniEndo){ %>
                                                            <i class="fa fa-question-circle" aria-hidden="true" alt="Fare click per ulteriori informazioni" data-id-endo='<%# Eval("Codice")%>'></i>
                                                        <%} %>
                                                    </li>
                                                </ItemTemplate>

                                                <FooterTemplate>
                                                    </ul>
                                                </FooterTemplate>
                                            </asp:Repeater>
                                        </div>
                                    </li>
                                </ItemTemplate>

                                <FooterTemplate>
                                    </ul>
                                </FooterTemplate>

                            </asp:Repeater>

                            <% if (MostraTipoEndo)
                                {%></li>
                        <%} %>
                    </ItemTemplate>

                    <FooterTemplate>
                        <% if (MostraTipoEndo)
                            {%></ul><%} %>
                    </FooterTemplate>

                </asp:Repeater>

                <%if (MostraFamiglia)
                    { %></li>
            <%} %>
        </ItemTemplate>

        <FooterTemplate>
            <%if (MostraFamiglia)
                { %></ul><%} %>
        </FooterTemplate>
    </asp:Repeater>
</div>
