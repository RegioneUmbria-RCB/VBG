<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="VisuraCtrlV2.ascx.cs"
    Inherits="Init.Sigepro.FrontEnd.Reserved.VisuraCtrlV2" %>
<%@ Register Src="~/Reserved/VisuraCtrlV2_DettaglioEndo.ascx" TagPrefix="uc1" TagName="VisuraCtrlV2_DettaglioEndo" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>


<script type="text/javascript">
    $(function () {
        //hide di tutti gli elementi con testo vuoto
        $('span.form-control:empty').parent().hide();
    });
</script>



<div class="row">
    <ar:LabeledLabel runat="server" ID="lblProtocollo" Label="Numero protocollo" Visible='<%#!DaArchivio %>' BtSize="Col3" />
    <ar:LabeledLabel runat="server" ID="lblDataProtocollo" Label="Data protocollo" Visible='<%#!DaArchivio %>' BtSize="Col3" />
</div>
<div class="row">
    <ar:LabeledLabel runat="server" ID="lblNumeroPratica" Label="Numero pratica" BtSize="Col3" />
    <ar:LabeledLabel runat="server" ID="lblDataPresentazione" Label="Data presentazione" BtSize="Col3" />
</div>
<ar:LabeledLabel runat="server" ID="lblOggetto" Label="Oggetto" />
<ar:LabeledLabel runat="server" ID="lblIntervento" Label="Intervento" />
<div class="row">
    <ar:LabeledLabel runat="server" ID="lblStatoPratica" Label="Stato" BtSize="Col3" />
</div>
<% if (!DaArchivio)
    { %>
<div>
    <h3>Riferimenti</h3>
    <div class="row">
        <ar:LabeledLabel runat="server" ID="lblResponsabileProc" Label="Responsabile procedimento" BtSize="Col4" />
        <ar:LabeledLabel runat="server" ID="lblIstruttore" Label="Istruttore" BtSize="Col4" />
        <ar:LabeledLabel runat="server" ID="lblOperatore" Label="Operatore" BtSize="Col4" />
    </div>
</div>
<div>
    <h3>Soggetti</h3>

    <asp:GridView GridLines="None" runat="server" ID="dgSoggetti" CssClass="table" AutoGenerateColumns="false">
        <Columns>
            <asp:BoundField DataField="Nominativo" HeaderText="Nominativo" />
            <asp:BoundField DataField="InQualitaDi" HeaderText="Tipo soggetto" />
            <asp:BoundField DataField="NominativoCollegato" HeaderText="Anagrafica collegata" />
        </Columns>
    </asp:GridView>
</div>
<%} %>
<div id="divLocalizzazioni" runat="server">
    <h3>Localizzazioni</h3>

    <asp:GridView GridLines="None" runat="server" CssClass="table" ID="dgLocalizzazioni" AutoGenerateColumns="false">
        <Columns>
            <asp:TemplateField HeaderText="Indirizzo" ItemStyle-Width="45%">
                <ItemTemplate>
                    <asp:Literal runat="server" ID="Literal1" Text='<%# DataBinder.Eval(Container.DataItem , "denominazione") %>' />&nbsp;
                </ItemTemplate>
            </asp:TemplateField>

            <asp:BoundField DataField="Civico" HeaderText="Civ." ItemStyle-Width="5%" />
            <asp:BoundField DataField="Esponente" HeaderText="Esp." ItemStyle-Width="5%" />
        </Columns>
        <EmptyDataTemplate>
            <asp:Label ID="lblErrNoLocalizzazioni" runat="server"><i>Non sono presenti localizzazioni</i></asp:Label>
        </EmptyDataTemplate>
    </asp:GridView>
    <asp:GridView GridLines="None" runat="server" ID="dgDatiCatastali" CssClass="table" AutoGenerateColumns="false">
        <Columns>
            <asp:TemplateField HeaderText="Tipo catasto">
                <ItemTemplate>
                    <asp:Literal runat="server" ID="ltrTipoCatasto" Text='<%# Eval("tipoCatasto")%>' />
                </ItemTemplate>
            </asp:TemplateField>
            <asp:BoundField DataField="foglio" HeaderText="Foglio" />
            <asp:BoundField DataField="particella" HeaderText="Particella" />
            <asp:BoundField DataField="sub" HeaderText="Subalterno" />
        </Columns>
        <EmptyDataTemplate>
            <asp:Label ID="lblErrNoLocalizzazioni" runat="server"><i>Non sono presenti dati catastali</i></asp:Label>
        </EmptyDataTemplate>
    </asp:GridView>
</div>
<% if (!DaArchivio)
    { %>
<div class="blocco aperto" id="divAllegati" runat="server">
    <h3>Documenti istanza</h3>

    <asp:GridView GridLines="None" runat="server" ID="gvAllegati" CssClass="table" AutoGenerateColumns="false"
        OnRowDataBound="gvAllegati_RowDataBound">
        <Columns>
            <asp:TemplateField ItemStyle-Width="16px">
                <ItemTemplate>
                    <asp:HyperLink runat="server" ID="hlDownloadAllegato" Target="_blank">
							<img src='<%= ResolveClientUrl("~/Images/attachment-icon.png") %>' />
                    </asp:HyperLink>
                </ItemTemplate>
            </asp:TemplateField>
            <asp:TemplateField HeaderText="Documento">
                <ItemTemplate>
                    <div class="md5-allegato">
                        [<asp:Literal runat="server" ID="ltrNomeFile" Text='<%# DataBinder.Eval( Container.DataItem , "NomeAllegato" )%>' />
                        <asp:Literal runat="server" ID="ltrMd5" Text='<%# DataBinder.Eval( Container.DataItem , "Md5", "- MD5: {0}" )%>' Visible='<%# Eval("HasMd5")%>' />]
                    </div>
                    <div class="note-allegato">
                        <asp:Literal runat="server" ID="Literal4" Text='<%# Eval( "annotazioni" , "Note: {0}")%>' Visible='<%# Eval("HasAnnotazioni")%>' />
                    </div>
                </ItemTemplate>
            </asp:TemplateField>
        </Columns>
        <EmptyDataTemplate>
            <asp:Label ID="lblErrNoLocalizzazioni" runat="server"><i>Non sono presenti documenti</i></asp:Label>
        </EmptyDataTemplate>
    </asp:GridView>
</div>
<%} %>
<% if (TempisticheVisibili)
    {%>
<fieldset class="blocco aperto">
    <legend>Tempistiche</legend>
    <div>
        <label>Data inizio</label>
        <asp:Label runat="server" ID="lblDataInizio" />
    </div>

    <div>
        <label>Data fine</label>
        <asp:Label runat="server" ID="lblDataFine" />
    </div>

    <div>
        <label>Durata</label>
        <asp:Label runat="server" ID="lblDurataGiorni" />
    </div>
</fieldset>
<%} %>
<% if (!DaArchivio)
    { %>
<div class="blocco aperto" id="divProcedimenti" runat="server">
    <h3>Endoprocedimenti attivati</h3>

    <asp:GridView GridLines="None" runat="server" ID="dgProcedimenti" CssClass="table" AutoGenerateColumns="false">
        <Columns>
            <asp:TemplateField HeaderText="Procedimento">
                <ItemTemplate>
                    <asp:Literal runat="server" ID="ltrProcedimento" Text='<%# DataBinder.Eval(Container.DataItem , "descrizione")%>' />
                </ItemTemplate>
            </asp:TemplateField>
        </Columns>
        <EmptyDataTemplate>
            <asp:Label ID="lblErrNoEndo" runat="server"><i>Non sono presenti procedimenti attivati</i></asp:Label>
        </EmptyDataTemplate>
    </asp:GridView>
</div>
<div runat="server" id="divOneri">
    <h3>Oneri</h3>
    <asp:GridView GridLines="None" runat="server" ID="dgOneri" CssClass="table" AutoGenerateColumns="false">
        <Columns>
            <asp:TemplateField HeaderText="Onere">
                <ItemTemplate>
                    <asp:Literal runat="server" ID="ltrCausaleOnere" Text='<%# DataBinder.Eval(Container.DataItem,"causale.causale") %>'></asp:Literal>
                </ItemTemplate>
            </asp:TemplateField>
            <asp:BoundField DataField="importo" HeaderText="Importo" ItemStyle-HorizontalAlign="Right" DataFormatString="€ {0:N2}" HtmlEncode="false" />
            <asp:TemplateField HeaderText="Rate">
                <ItemTemplate>
                    <asp:Repeater runat="server" ID="rptScadenzeOnere" DataSource='<%# DataBinder.Eval(Container.DataItem , "scadenze") %>'>
                        <HeaderTemplate>
                            <ul>
                        </HeaderTemplate>
                        <ItemTemplate>
                            <li>Rata n.<asp:Literal runat="server" ID="ltr1" Text='<%# Eval("numeroRata") %>' /><br />
                                Importo:
                                        <asp:Literal runat="server" ID="Literal2" Text='<%# Eval("importoRata", "{0:N2} €") %>' /><br />
                                Scadenza:
											<asp:Literal runat="server" ID="Literal3" Text='<%# Eval("dataScadenza", "{0:dd/MM/yyyy}") %>' /><br />
                            </li>
                        </ItemTemplate>
                        <FooterTemplate>
                            </ul>
                        </FooterTemplate>
                    </asp:Repeater>
                </ItemTemplate>
            </asp:TemplateField>
        </Columns>
        <EmptyDataTemplate>
            <asp:Label ID="lblErrNoLocalizzazioni" runat="server"><i>Non sono presenti oneri per la pratica</i></asp:Label>
        </EmptyDataTemplate>
    </asp:GridView>
</div>
<div id="divMovimenti" runat="server">
    <h3>Movimenti</h3>
    <asp:GridView GridLines="None" runat="server" ID="dgMovimenti" CssClass="table" AutoGenerateColumns="false">
        <Columns>
            <asp:BoundField HeaderText="Movimento" DataField="tipoAttivita.descrizione" />
            <asp:BoundField DataField="dataAttivita" HeaderText="Data" ItemStyle-HorizontalAlign="Center"
                DataFormatString="{0:dd/MM/yyyy}" HtmlEncode="false" />
            <asp:BoundField DataField="parere" HeaderText="Parere" />
            <asp:TemplateField HeaderText="Protocollo">
                <ItemTemplate>
                    <asp:Literal runat="server" ID="ltrNumeoProtocollo" Text='<%# Bind("numeroProtocolloGenerale" , "n. {0}")%>' Visible='<%# !String.IsNullOrEmpty((string)Eval("numeroProtocolloGenerale")) %>' />
                    <asp:Literal runat="server" ID="ltrDataProtocollo" Text='<%# Bind("dataProtocolloGenerale"," del {0:dd/MM/yyyy}") %>' Visible='<%#Eval("dataProtocolloGeneraleSpecified") %>' />
                </ItemTemplate>
            </asp:TemplateField>
            <asp:BoundField DataField="note" HeaderText="Note" />
        </Columns>
        <EmptyDataTemplate>
            <asp:Label ID="lblErrNoLocalizzazioni" runat="server"><i>Non sono presenti movimenti attivati</i></asp:Label>
        </EmptyDataTemplate>
    </asp:GridView>
</div>
<%} %>
<div id="popupDettagli">
</div>
