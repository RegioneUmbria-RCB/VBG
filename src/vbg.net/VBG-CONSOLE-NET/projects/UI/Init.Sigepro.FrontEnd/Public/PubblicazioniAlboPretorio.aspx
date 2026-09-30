<%@ Page Language="C#" MasterPageFile="~/AreaRiservataPopupMaster.Master" AutoEventWireup="true"
    Codebehind="PubblicazioniAlboPretorio.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Public.PubblicazioniAlboPretorio"
    Title="PUBBLICAZIONI ALBO PRETORIO" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div class="inputFormPopup">
        <div class="titoloPaginaPopup">
            PUBBLICAZIONI ALBO PRETORIO</div>
        <fieldset>
            <legend>Criteri di ricerca</legend>
            <div>
                <asp:Label runat="server" ID="lblOggettoSearch" AssociatedControlID="txtOggettoSearch"
                    Text="Oggetto" />
                <asp:TextBox runat="server" ID="txtOggettoSearch" Columns="20" />
            </div>
            <div>
                <asp:Label runat="server" ID="lblCategoriaSearch" AssociatedControlID="ddlCategoriaSearch"
                    Text="Categoria" />
                <asp:DropDownList runat="server" ID="ddlCategoriaSearch" DataTextField="DESCRIZIONE"
                    DataValueField="ID" />
            </div>
            <div>
                <asp:Label runat="server" ID="lblValidiAl" AssociatedControlID="txtValidaAl" Text="Validi al" />
                <asp:TextBox runat="server" ID="txtValidaAl" Columns="11" />
                <asp:ImageButton runat="server" Style="vertical-align: top;" ToolTip="Visualizza / chiudi il calendario."
                    ID="imgCalendar" ImageUrl="~/Images/Calendar.gif" OnClick="imgCalendar_Click" />
            </div>
            <div>
                <asp:Label runat="server" ID="lblFake" AssociatedControlID="calValidiAl" />
                <asp:Calendar runat="server" ID="calValidiAl" Visible="False" OnSelectionChanged="calValidiAl_SelectionChanged"
                    BackColor="White" BorderColor="#999999" CellPadding="4" DayNameFormat="Shortest"
                    Font-Names="Verdana" Font-Size="8pt" ForeColor="Black" Height="180px" Width="200px">
                    <SelectedDayStyle BackColor="#666666" Font-Bold="True" ForeColor="White" />
                    <SelectorStyle BackColor="#CCCCCC" />
                    <WeekendDayStyle BackColor="#FFFFCC" />
                    <TodayDayStyle BackColor="#CCCCCC" ForeColor="Black" />
                    <OtherMonthDayStyle ForeColor="#808080" />
                    <NextPrevStyle VerticalAlign="Bottom" />
                    <DayHeaderStyle BackColor="#CCCCCC" Font-Bold="True" Font-Size="7pt" />
                    <TitleStyle BackColor="#999999" BorderColor="Black" Font-Bold="True" />
                </asp:Calendar>
            </div>
            <div class="bottoni">
                <asp:Button runat="server" ID="cmdCerca" Text="Cerca" OnClick="cmdCerca_Click" />
                <asp:Button runat="server" ID="cmdChiudi" Text="Chiudi" OnClick="cmdChiudi_Click" />
            </div>
        </fieldset>
    </div>
    <asp:Panel runat="server" ID="pnLista">
        <div class="inputFormPopup">
            <fieldset>
                <legend>Elenco</legend>
                <asp:GridView ID="gvAlboPretorio" CssClass="tabellaElencoAlboPretorio" DataKeyNames="ID"
                    AllowPaging="true" PageSize="10" runat="server" AutoGenerateColumns="False" OnSelectedIndexChanged="gvAlboPretorio_SelectedIndexChanged"
                    OnPageIndexChanging="gvAlboPretorio_PageIndexChanging">

                    <Columns>
                        <asp:BoundField DataField="NUMERO_PUBBLICAZIONE" HeaderText="Numero" SortExpression="NUMERO_PUBBLICAZIONE" />
                        <asp:BoundField DataField="DATA_PUBBLICAZIONE" HeaderText="Anno" SortExpression="DATA_PUBBLICAZIONE"
                            DataFormatString="{0:yyyy}" HtmlEncode="false" />
                        <asp:BoundField DataField="ALBO_CATEGORIE_DESCR" HeaderText="Tipo" SortExpression="ALBO_CATEGORIE_DESCR" />
                        <asp:BoundField DataField="AMMINISTRAZIONE_DESCR" HeaderText="Ente" SortExpression="AMMINISTRAZIONE_DESCR" />
                        <asp:ButtonField CommandName="Select" DataTextField="DESCRIZIONE" HeaderText="Oggetto" />
                        <asp:BoundField DataField="DATA_PUBBLICAZIONE" HtmlEncode="false" HeaderText="Data pubblicazione" SortExpression="DATA_PUBBLICAZIONE"
                            DataFormatString="{0:dd/MM/yyyy}" />
                        <asp:BoundField DataField="VALIDA_DAL" HeaderText="Inizio validit&#224;" SortExpression="VALIDA_DAL"
                            DataFormatString="{0:dd/MM/yyyy}" HtmlEncode="false" />
                        <asp:BoundField DataField="VALIDA_AL" HtmlEncode="false" HeaderText="Fine validit&#224;" SortExpression="VALIDA_AL"
                            DataFormatString="{0:dd/MM/yyyy}" />
                    </Columns>
                    <EmptyDataTemplate>
                        Non è stata trovata nessuna pubblicazione
                    </EmptyDataTemplate>
                </asp:GridView>
            </fieldset>
        </div>
    </asp:Panel>
    <asp:Panel runat="server" ID="pnDettaglio" Visible="false">
        <div id="mainFormPopup">
            <div class="inputFormPopup">
                <fieldset>
                    <legend>Dettaglio pubblicazione</legend>
                    <div>
                        <asp:Label runat="server" ID="etNumero" AssociatedControlID="lblNumero" Text="Atto numero" />
                        <asp:Label Font-Bold="true" runat="server" ID="lblNumero" />
                        del
                        <asp:Label runat="server" ID="lblAnno" Font-Bold="true" />
                    </div>
                    <div>
                        <asp:Label runat="server" ID="etTipo" AssociatedControlID="lblCategoria" Text="Tipo" />
                        <asp:Label Font-Bold="true" runat="server" ID="lblCategoria" />
                    </div>
                    <div>
                        <asp:Label runat="server" ID="etUfficio" AssociatedControlID="lblUfficio" Text="Ufficio" />
                        <asp:Label Font-Bold="true" runat="server" ID="lblUfficio" />
                    </div>
                    <div>
                        <asp:Label runat="server" ID="etOggetto" AssociatedControlID="lblOggettoDelibera"
                            Text="Oggetto" />
                        <asp:Label runat="server" Font-Bold="true" ID="lblOggettoDelibera" />
                    </div>
                    <div>
                        <asp:Label runat="server" ID="etProtocollo" AssociatedControlID="lblProtocollo" Text="Protocollo numero" />
                        <asp:Label runat="server" Font-Bold="true" ID="lblProtocollo" />
                        del
                        <asp:Label runat="server" ID="lblAnnoProtocollo" Font-Bold="true" />
                    </div>
                    <div>
                        <asp:Label runat="server" ID="etValidita" AssociatedControlID="lblValiditaDal" Text="Validità dal" />
                        <asp:Label runat="server" Font-Bold="true" ID="lblValiditaDal" />
                        al
                        <asp:Label runat="server" ID="lblValiditaAl" Font-Bold="true" />
                    </div>
                    <div>
                        <asp:Label runat="server" ID="etAnnotazioni" AssociatedControlID="lblAnnotazioni"
                            Text="Annotazioni" />
                        <asp:Label runat="server" ID="lblAnnotazioni" Font-Bold="true" />
                    </div>
                </fieldset>
                <asp:Panel runat="server" ID="pnAllegati">
                    <fieldset>
                        <legend>Allegati</legend>
                        <asp:Repeater runat="server" ID="rptAllegati">
                            <HeaderTemplate>
                                <ul>
                            </HeaderTemplate>
                            <ItemTemplate>
                                <li><i>
                                    <asp:HyperLink ID="hlDownloadAllegato" CssClass="" runat="server" NavigateUrl='<%# DataBinder.Eval(Container.DataItem, "CODICEOGGETTO" , "~/MostraOggetto.ashx?codiceoggetto={0}&idcomune=" + IdComune) %>'
                                       ToolTip='<%# DataBinder.Eval(Container.DataItem, "NOMEFILE", "Scarica il file {0}") %>' Text='<%# DataBinder.Eval(Container.DataItem,"DESCRIZIONE","{0}") %>' /></i>&nbsp;&nbsp;<asp:Label runat="server" ID="lblNomeFile" Text='<%# DataBinder.Eval(Container.DataItem, "NOMEFILE") %>' />&nbsp;<asp:Label runat="server" ID="lblDimensioneFile" Text='<%# DataBinder.Eval(Container.DataItem, "DIMENSIONEFILE","( {0})") %>' />
                                </li>
                            </ItemTemplate>
                            <FooterTemplate>
                                </ul>
                            </FooterTemplate>
                        </asp:Repeater>
                    </fieldset>
                </asp:Panel>
                <div class="bottoni">
                    <asp:Button runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" OnClick="cmdChiudiDettaglio_Click" />
                </div>
            </div>
        </div>
    </asp:Panel>
</asp:Content>
