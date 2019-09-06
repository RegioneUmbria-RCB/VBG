<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="GestioneEndoConsole_EndoView.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneEndoConsole_EndoView" %>
<div class="albero-endo-console">
    <asp:Repeater runat="server" ID="rptFamiglie" OnItemDataBound="rptFamiglie_ItemDataBound">
        <HeaderTemplate>
            <ul class="famiglie-endo">
        </HeaderTemplate>

        <ItemTemplate>
            <li class="ramo-albero albero-chiuso">
                <span>
                    <asp:Literal runat="server" Text='<%# Eval("Descrizione") %>'></asp:Literal>
                </span>
                <asp:Repeater runat="server" ID="rptTipi" OnItemDataBound="rptTipi_ItemDataBound">
                    <HeaderTemplate>
                        <ul class="tipi-endo">
                    </HeaderTemplate>

                    <ItemTemplate>
                        <li class="ramo-albero albero-chiuso">
                            <span>
                                <asp:Literal runat="server" Text='<%# Eval("Descrizione") %>'></asp:Literal>
                            </span>
                            <asp:Repeater runat="server" ID="rptEndo" OnItemDataBound="rptEndo_ItemDataBound">
                                <HeaderTemplate>
                                    <ul class="dettaglio-endo">
                                </HeaderTemplate>

                                <ItemTemplate>
                                    <li class="endoprocedimento-check">
                                        <asp:HiddenField runat="server" ID="hidIdEndo" Value='<%# Eval("Codice")%>' />
                                        <asp:CheckBox runat="server" ID="chkSelezionato" Text='<%# Eval("Descrizione") %>' data-richiesto='<%#Eval("Richiesto") %>' data-id-endo='<%#Eval("Codice") %>' />
                                        <i class="fa fa-question-circle" aria-hidden="true" alt="Fare click per ulteriori informazioni" data-id-endo='<%# DataBinder.Eval(Container.DataItem,"Codice")%>'></i>

                                        <asp:Repeater runat="server" ID="rptSubEndo" OnItemDataBound="rptSubEndo_ItemDataBound">
                                            <HeaderTemplate>
                                                <ul class="dettaglio-sub-endo">
                                            </HeaderTemplate>

                                            <ItemTemplate>
                                                <li>
                                                    <asp:HiddenField runat="server" ID="hidIdEndo" Value='<%# Eval("Codice")%>' />
                                                    <asp:CheckBox runat="server" CssClass="chk-sub-endo" ID="chkSelezionato" Text='<%# Eval("Descrizione") %>' data-richiesto='<%#Eval("Richiesto") %>' data-id-endo='<%#Eval("Codice") %>' />
                                                    <i class="fa fa-question-circle" aria-hidden="true" alt="Fare click per ulteriori informazioni" data-id-endo='<%# DataBinder.Eval(Container.DataItem,"Codice")%>'></i>
                                                </li>
                                            </ItemTemplate>

                                            <FooterTemplate>
                                                </ul>
                                            </FooterTemplate>
                                        </asp:Repeater>


                                    </li>
                                </ItemTemplate>

                                <FooterTemplate>
                                    </ul>
                                </FooterTemplate>
                            </asp:Repeater>
                        </li>
                    </ItemTemplate>

                    <FooterTemplate>
                        </ul>
                    </FooterTemplate>
                </asp:Repeater>
            </li>
        </ItemTemplate>

        <FooterTemplate>
            </ul>
        </FooterTemplate>
    </asp:Repeater>
</div>
