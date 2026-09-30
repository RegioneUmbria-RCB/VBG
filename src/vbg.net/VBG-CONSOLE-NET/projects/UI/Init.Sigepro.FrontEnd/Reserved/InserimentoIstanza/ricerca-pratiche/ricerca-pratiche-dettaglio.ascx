<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="ricerca-pratiche-dettaglio.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.ricerca_pratiche.ricerca_pratiche_dettaglio" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<fieldset>
    <legend>Estremi pratica</legend>
    <div class="row">
        <ar:labeledlabel runat="server" label="Numero" id="lblNumeroPratica" btsize="Col6" />
        <ar:labeledlabel runat="server" label="Protocollo" id="lblNumeroProtocollo" btsize="Col6" />
    </div>

    <ar:labeledlabel runat="server" label="Intervento" id="lblIntervento" />
    <ar:labeledlabel runat="server" label="Descrizione" id="lblDescrizioneLavori" />
</fieldset>

<fieldset>
    <legend>Anagrafiche</legend>

    <asp:Repeater runat="server" ID="rptAnagrafiche">
        <HeaderTemplate>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Nominativo</th>
                        <th>Qualifica</th>
                        <th></th>
                    </tr>
                </thead>
        </HeaderTemplate>
        <ItemTemplate>
            <tbody>
                <tr>
                    <td><%#Eval("Nominativo") %></td>
                    <td><%#Eval("Qualifica") %></td>
                    <td></td>
                </tr>
            </tbody>
        </ItemTemplate>
        <FooterTemplate>
            </table>
        </FooterTemplate>
    </asp:Repeater>
</fieldset>

<fieldset>
    <legend>Localizzazioni</legend>

    <asp:Repeater runat="server" ID="rptLocalizzazioni" OnItemDataBound="rptLocalizzazioni_ItemDataBound">
        <HeaderTemplate>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Indirizzo</th>
                        <th>Mappali</th>
                    </tr>
                </thead>
        </HeaderTemplate>
        <ItemTemplate>
            <tbody>
                <tr>
                    <td><%#Eval("Stradario") %> <%#Eval("Civico") %> <%#Eval("Esponente") %></td>
                    <td>
                        <asp:Repeater runat="server" ID="rptMappali">
                            <HeaderTemplate>
                                <ul>
                            </HeaderTemplate>
                            <ItemTemplate>
                                <li><b><%#Eval("Catasto") %></b>, <%#Eval("Estremi") %></li>
                            </ItemTemplate>
                            <FooterTemplate></ul></FooterTemplate>
                        </asp:Repeater>

                    </td>
                </tr>
            </tbody>
        </ItemTemplate>
        <FooterTemplate>
            </table>
        </FooterTemplate>
    </asp:Repeater>
</fieldset>
