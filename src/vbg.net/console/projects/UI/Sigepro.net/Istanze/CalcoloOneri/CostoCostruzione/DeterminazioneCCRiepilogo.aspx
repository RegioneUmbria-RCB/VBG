<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Inherits="Istanze_CalcoloOneri_CostoCostruzione_DeterminazioneCCRiepilogo"
    Title="Prospetto A - Determinazione costo di costruzione" Codebehind="DeterminazioneCCRiepilogo.aspx.cs" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>

<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>



<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <table width="100%" class="TabellaOneri">
        <colgroup width="50" />
        <colgroup width="50%" />
        <tr>
            <td colspan="2">
                <fieldset>
                    <legend>Tabella 1 - Incremento per superficie utile abitabile</legend>
                    <div>
                        <asp:Repeater runat="server" ID="rptTabella1" DataSourceID="CCITabella1DataSource"
                            OnItemDataBound="rptTabella1_ItemDataBound">
                            <HeaderTemplate>
                                <table>
                                    <tr>
                                        <th>
                                            Classi di superficie (mq)</th>
                                        <th>
                                            Alloggi (n)</th>
                                        <th>
                                            Superficie utile abitabile (mq)</th>
                                        <th>
                                            Rapporto rispetto al totale Su</th>
                                        <th>
                                            % Incremento (Art. 5)</th>
                                        <th>
                                            % Incremento per classi di superficie</th>
                                    </tr>
                                    <tr>
                                        <td style="text-align: center">
                                            (1)</td>
                                        <td style="text-align: center">
                                            (2)</td>
                                        <td style="text-align: center">
                                            (3)</td>
                                        <td style="text-align: center">
                                            (4) = (3) : Su</td>
                                        <td style="text-align: center">
                                            (5)</td>
                                        <td style="text-align: center">
                                            (6) = (4) x (5)</td>
                                    </tr>
                            </HeaderTemplate>
                            <ItemTemplate>
                                <tr>
                                    <td>
                                        <asp:Label ID="lblClasseSuperficie" runat="server" Text="" /></td>
                                    <td style="text-align: center">
                                        <asp:Label ID="Label1" runat="server" Text='<%# Bind("Alloggi") %>' />
                                    </td>
                                    <td style="text-align: center">
                                        <asp:Label ID="Label2" runat="server" Text='<%# Bind("Su") %>' />
                                    </td>
                                    <td style="text-align: center">
                                        <%--<asp:Label ID="Label3" runat="server" Text='<%# Bind("Su") %>' />
										:
										<asp:Label ID="Label4" runat="server" Text='<%# GetSu() %>' />
										=--%>
                                        <asp:Label ID="Label5" runat="server" Text='<%# Bind("RapportoSu","{0:N3}") %>' />
                                    </td>
                                    <td style="text-align: center">
                                        <asp:Label ID="Label6" runat="server" Text='<%# Bind("Incremento") %>' /></td>
                                    <td style="text-align: center">
                                        <asp:Label ID="Label7" runat="server" Text='<%# Bind("Incrementoxclassi") %>' /></td>
                                </tr>
                            </ItemTemplate>
                            <FooterTemplate>
                                <tr>
                                    <td colspan="2">
                                    </td>
                                    <td style="text-align: center">
                                        Su: <b>
                                            <%= GetSu() %>
                                        </b>
                                    </td>
                                    <td colspan="2">
                                    </td>
                                    <td style="text-align: center">
                                        I1: <b>
                                            <%= GetI1() %>
                                        </b>
                                    </td>
                                </tr>
                                </table>
                            </FooterTemplate>
                        </asp:Repeater>
                        <asp:ObjectDataSource ID="CCITabella1DataSource" runat="server" OldValuesParameterFormatString="original_{0}"
                            SelectMethod="Find" TypeName="Init.SIGePro.Manager.CCITabella1Mgr">
                            <SelectParameters>
                                <asp:QueryStringParameter Name="token" QueryStringField="Token" Type="String" />
                                <asp:QueryStringParameter Name="idCalcolo" QueryStringField="IdCalcolo" Type="Int32" />
                            </SelectParameters>
                        </asp:ObjectDataSource>
                    </div>
                </fieldset>
            </td>
        </tr>
        <tr style="vertical-align: top">
            <td>
                <fieldset>
                    <legend>Tabella 2 - Superfici per servizi o accessori relativi alla parte residenziale</legend>
                    <div>
                        <asp:Repeater runat="server" ID="rptTabella2" DataSourceID="CCITabella2DataSource"
                            OnItemDataBound="rptTabella2_ItemDataBound">
                            <HeaderTemplate>
                                <table>
                                    <tr>
                                        <th>
                                            Destinazioni</th>
                                        <th>
                                            Superficie netta di servizi e accessori (q)</th>
                                    </tr>
                                    <tr>
                                        <td style="text-align: center">
                                            (7)</td>
                                        <td style="text-align: center">
                                            (8)</td>
                                    </tr>
                            </HeaderTemplate>
                            <ItemTemplate>
                                <tr>
                                    <td>
                                        <asp:Label ID="lblDestinazione" runat="server" Text="Label"></asp:Label></td>
                                    <td style="text-align: center">
                                        <asp:Label ID="Label8" runat="server" Text='<%# Bind("Superficie") %>'></asp:Label>
                                    </td>
                                </tr>
                            </ItemTemplate>
                            <FooterTemplate>
                                <tr>
                                    <td>
                                    </td>
                                    <td style="text-align: center">
                                        Snr: <b>
                                            <%= GetSnr() %>
                                        </b>
                                    </td>
                                </tr>
                                </table>
                            </FooterTemplate>
                        </asp:Repeater>
                        <asp:ObjectDataSource ID="CCITabella2DataSource" runat="server" OldValuesParameterFormatString="original_{0}"
                            SelectMethod="Find" TypeName="Init.SIGePro.Manager.CCITabella2Mgr">
                            <SelectParameters>
                                <asp:QueryStringParameter Name="token" QueryStringField="Token" Type="String" />
                                <asp:QueryStringParameter Name="idCalcolo" QueryStringField="IdCalcolo" Type="Int32" />
                            </SelectParameters>
                        </asp:ObjectDataSource>
                    </div>
                </fieldset>
            </td>
            <td>
                <fieldset>
                    <legend>Tabella 3 - Incremento per servizi ed accessori relativi alla parte residenziale
                        (art. 6)</legend>
                    <div>
                        <asp:Repeater runat="server" ID="rptTabella3" DataSourceID="CCITabella3DataSource"
                            OnItemDataBound="rptTabella3_ItemDataBound">
                            <HeaderTemplate>
                                <table>
                                    <tr>
                                        <th>
                                            Intervalli di variabilit&agrave; del rapporto percentuale (Snr/Su) x 100</th>
                                        <%if (Tabella3HaDettagliSuperficie)
                                          { %>
                                        <th>
                                            Destinazione</th>
                                        <%} //if (Tabella3HaDettagliSuperficie)%>
                                        <th>
                                            Ipotesi che ricorre</th>
                                        <th>
                                            % Incremento</th>
                                    </tr>
                                    <tr>
                                        <td style="text-align: center">
                                            (9)</td>
                                        <%if (Tabella3HaDettagliSuperficie)
                                          { %>
                                        <td style="text-align: center">
                                            &nbsp;</td>
                                        <%} //if (Tabella3HaDettagliSuperficie) %>
                                        <td style="text-align: center">
                                            (10)</td>
                                        <td style="text-align: center">
                                            (11)</td>
                                    </tr>
                            </HeaderTemplate>
                            <ItemTemplate>
                                <tr>
                                    <td>
                                        <asp:Label ID="lblIntervallo" runat="server" Text='' />
                                    </td>
                                    <%if (Tabella3HaDettagliSuperficie)
                                      { %>
                                    <td style="text-align: center">
                                        <asp:Label ID="lblDettagliSuperficie" runat="server" Text='<%# DataBinder.Eval(Container.DataItem,"Tabella3.DettagliSuperficie")%>' />
                                    </td>
                                    <%} //if (Tabella3HaDettagliSuperficie) %>
                                    <td style="text-align: center">
                                        <asp:Image  style="float:none" runat="server" ID="imgSelezionato" ImageUrl='<%# "~/images/" + ( ( DataBinder.Eval( Container.DataItem , "Ipotesichericorre" ).ToString() == "1" ) ? "on.gif" : "off.gif" ) %>' />
                                    </td>
                                    <td style="text-align: center">
                                        <asp:Label ID="Label8" runat="server" Text='<%# Bind("Incremento") %>' />
                                    </td>
                                </tr>
                            </ItemTemplate>
                            <FooterTemplate>
                                <tr>
                                    <td colspan="2">
                                    </td>
                                    <td style="text-align: center">
                                        I2: <b>
                                            <%= GetI2() %>
                                        </b>
                                    </td>
                                </tr>
                                </table>
                            </FooterTemplate>
                        </asp:Repeater>
                        <asp:ObjectDataSource ID="CCITabella3DataSource" runat="server" OldValuesParameterFormatString="original_{0}"
                            SelectMethod="Find" TypeName="Init.SIGePro.Manager.CCITabella3Mgr" OnSelecting="CCITabella3DataSource_Selecting">
                            <SelectParameters>
                                <asp:QueryStringParameter Name="token" QueryStringField="Token" Type="String" />
                                <asp:QueryStringParameter Name="idCalcolo" QueryStringField="IdCalcolo" Type="Int32" />
                            </SelectParameters>
                        </asp:ObjectDataSource>
                    </div>
                </fieldset>
            </td>
        </tr>
        <tr style="vertical-align: top">
            <td>
                <fieldset>
                    <legend>Superfici residenziali e relativi servizi ed accessori (Articoli 2 e 3)</legend>
                    <div>
                        <table>
                            <colgroup width="10%" />
                            <colgroup width="15%" />
                            <colgroup width="55%" />
                            <colgroup width="20%" />
                            <tr>
                                <th colspan="2">
                                    Sigla</th>
                                <th>
                                    Denominazione</th>
                                <th>
                                    Superficie (mq)</th>
                            </tr>
                            <tr>
                                <td colspan="2" style="text-align: center">
                                    (17)</td>
                                <td style="text-align: center">
                                    (18)</td>
                                <td style="text-align: center">
                                    (19)</td>
                            </tr>
                            <tr>
                                <td>
                                    1</td>
                                <td style="text-align: center">
                                    Su (art. 3)</td>
                                <td style="text-align: center">
                                    Superficie utile abitabile</td>
                                <td style="text-align: center">
                                    <%= GetSu() %>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    2</td>
                                <td style="text-align: center">
                                    Snr (art. 2)</td>
                                <td style="text-align: center">
                                    Superficie netta non residenziale</td>
                                <td style="text-align: center">
                                    <%= GetSnr() %>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    3</td>
                                <td style="text-align: center">
                                    60% Snr</td>
                                <td style="text-align: center">
                                    Superficie ragguagliata</td>
                                <td style="text-align: center">
                                    <%= (GetSnr() * 0.6f).ToString("N2") %>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    4=1+3</td>
                                <td style="text-align: center">
                                    Sc (art. 2)</td>
                                <td style="text-align: center">
                                    Superficie complessiva</td>
                                <td style="text-align: center">
                                    <%= GetSc() %>
                                </td>
                            </tr>
                        </table>
                    </div>
                </fieldset>
            </td>
            <td>
                <fieldset>
                    <legend>Tabella 4 - Incremento per particolari caratteristiche (art. 7)</legend>
                    <div>
                        <asp:Repeater runat="server" ID="rptTabella4" DataSourceID="CCITabella4DataSource"
                            OnItemDataBound="rptTabella4_ItemDataBound">
                            <HeaderTemplate>
                                <table>
                                    <tr>
                                        <th>
                                            Numero di caratteristiche</th>
                                        <th>
                                            Ipotesi che ricorre</th>
                                        <th>
                                            % Incremento</th>
                                    </tr>
                                    <tr>
                                        <td style="text-align: center">
                                            (12)</td>
                                        <td style="text-align: center">
                                            (13)</td>
                                        <td style="text-align: center">
                                            (14)</td>
                                    </tr>
                            </HeaderTemplate>
                            <ItemTemplate>
                                <tr>
                                    <td>
                                        <asp:Label ID="lblCaratteristica" runat="server" Text='' />
                                    </td>
                                    <td style="text-align: center;">
                                        <asp:Image style="float:none" runat="server" ID="imgSelezionato" ImageUrl='<%# "~/images/" + ( ( DataBinder.Eval( Container.DataItem , "Selezionata" ).ToString() == "1" ) ? "on.gif" : "off.gif" ) %>' />
                                    </td>
                                    <td style="text-align: center">
                                        <asp:Label ID="Label8" runat="server" Text='<%# Bind("Incremento") %>' />
                                    </td>
                                </tr>
                            </ItemTemplate>
                            <FooterTemplate>
                                <tr>
                                    <td colspan="2">
                                    </td>
                                    <td style="text-align: center">
                                        I3: <b>
                                            <%= GetI3() %>
                                        </b>
                                    </td>
                                </tr>
                                </table>
                            </FooterTemplate>
                        </asp:Repeater>
                        <asp:ObjectDataSource ID="CCITabella4DataSource" runat="server" OldValuesParameterFormatString="original_{0}"
                            SelectMethod="Find" TypeName="Init.SIGePro.Manager.CCITabella4Mgr">
                            <SelectParameters>
                                <asp:QueryStringParameter Name="token" QueryStringField="Token" Type="String" />
                                <asp:QueryStringParameter Name="idCalcolo" QueryStringField="IdCalcolo" Type="Int32" />
                            </SelectParameters>
                        </asp:ObjectDataSource>
                    </div>
                </fieldset>
            </td>
        </tr>
        <tr style="vertical-align: top">
            <td>
                <fieldset>
                    <legend>Superfici per attività turistiche, commerciali e direzioni e relativi accessori
                        (Art. 9)</legend>
                    <div>
                        <table>
                            <colgroup width="10%" />
                            <colgroup width="15%" />
                            <colgroup width="55%" />
                            <colgroup width="20%" />
                            <tr>
                                <th colspan="2">
                                    Sigla</th>
                                <th>
                                    Denominazione</th>
                                <th>
                                    Superficie (mq)</th>
                            </tr>
                            <tr>
                                <td colspan="2" style="text-align: center">
                                    (20)</td>
                                <td style="text-align: center">
                                    (21)</td>
                                <td style="text-align: center">
                                    (22)</td>
                            </tr>
                            <tr>
                                <td>
                                    1</td>
                                <td style="text-align: center">
                                    Su (art. 9)</td>
                                <td style="text-align: center">
                                    Superficie netta non residenziale</td>
                                <td style="text-align: center">
                                    <%= GetSuArt9() %>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    2</td>
                                <td style="text-align: center">
                                    Sa (art. 9)</td>
                                <td style="text-align: center">
                                    Superficie accessori</td>
                                <td style="text-align: center">
                                    <%= GetSa() %>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    3</td>
                                <td style="text-align: center">
                                    60% Sa</td>
                                <td style="text-align: center">
                                    Superficie ragguagliata</td>
                                <td style="text-align: center">
                                    <%= (GetSa() * 0.6f).ToString("N2") %>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    4=1+3</td>
                                <td style="text-align: center">
                                    St (art. 9)</td>
                                <td style="text-align: center">
                                    Superficie totale non residenziale</td>
                                <td style="text-align: center">
                                    <%= GetSt() %>
                                </td>
                            </tr>
                        </table>
                    </div>
                </fieldset>
            </td>
            <td>
                <fieldset>
                    <legend></legend>
                    <div>
                        <table>
                            <tr>
                                <th>
                                    TOTALE INCREMENTI <i>(I = i1+i2+i3)</i></th>
                                <td style="text-align: center">
                                    I: <b>
                                        <%= GetI1() + GetI2() + GetI3() %>
                                    </b>
                                </td>
                            </tr>
                        </table>
                        <br />
                        <table>
                            <colgroup width="50%" />
                            <colgroup width="50%" />
                            <tr>
                                <th>
                                    Classe edificio</th>
                                <th>
                                    % Maggiorazione</th>
                            </tr>
                            <tr>
                                <td style="text-align: center">
                                    (15)</td>
                                <td style="text-align: center">
                                    (16)</td>
                            </tr>
                            <tr>
                                <td style="text-align: center">
                                    <%= GetClasseEdificio() %>
                                </td>
                                <td style="text-align: center">
                                    <%= GetMaggiorazione() %>
                                </td>
                            </tr>
                        </table>
                    </div>
                </fieldset>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <fieldset>
                    <legend>Riepilogo</legend>
                    <div style="text-align: center">
                        <table style="text-align: left">
                            <tr>
                                <td>
                                    A - Costo di costruzione dell'edilizia agevolata</td>
                                <td style="text-align: right">
                                    <%= GetCostoCostruzioneMq().ToString("N2") %>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    B - Costi a mq di costruzione, maggiorato A x (1 + M / 100 ) = <b>
                                        <%= GetCostoCostruzioneMq() %>
                                        x ( 1 +
                                        <%= GetMaggiorazione() %>
                                        / 100 )</b></td>
                                <td style="text-align: right">
                                    <%= GetCostoCostruzioneMaggiorato().ToString("N2")%>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    C - costo di costruzione dell'edificio ( Sc + St ) x B = <b>(
                                        <%= GetSc() %>
                                        +
                                        <%= GetSt() %>
                                        ) x
                                        <%= GetCostoCostruzioneMaggiorato()%>
                                    </b>
                                </td>
                                <td style="text-align: right">
                                    <%= GetCostoEdificio().ToString("N2")%>
                                </td>
                            </tr>
                        </table>
                    </div>
                </fieldset>
            </td>
        </tr>
    </table>
    <fieldset>
        <div class="Bottoni">
            <init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
        </div>
    </fieldset>
</asp:Content>
