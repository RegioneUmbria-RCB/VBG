<%@ Page Title="Titolo" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneLocalizzazioni.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneLocalizzazioni" %>

<%@ Register Assembly="Init.Utils" Namespace="Init.Utils.Web.UI" TagPrefix="cc2" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>



<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<asp:Content ID="Content2" ContentPlaceHolderID="head" runat="server">
    <script type="text/javascript">
        $(function () {

            $.fn.validator.Constructor.INPUT_SELECTOR = ':input:not([type="hidden"], [type="submit"], [type="reset"], button, [formnovalidate], :hidden)';
            $('#aspnetForm').validator('update');

        });
    </script>
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
    <div class="inputForm">

        <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
            <asp:View runat="server" ID="listaView">
                <fieldset>
                    <asp:GridView ID="dgStradario" Width="100%"
                        AutoGenerateColumns="False"
                        DataKeyNames="Id"
                        OnRowDeleting="dgStradario_DeleteCommand"
                        OnRowEditing="dgStradario_RowEditing"
                        GridLines="None"
                        runat="server"
                        CssClass="table">
                        <Columns>
                            <asp:TemplateField HeaderText="Indirizzo">
                                <ItemTemplate>
                                    <asp:Literal runat="server" ID="ltrindirizzo" Text='<%# DataBinder.Eval(Container,"DataItem")%>' />
                                </ItemTemplate>
                            </asp:TemplateField>

                            <asp:TemplateField HeaderText="Altri dati">
                                <ItemTemplate>
                                    <asp:Literal runat="server" ID="ltrAltriDati" Text='<%# FormattaAltriDati(DataBinder.Eval(Container,"DataItem"))%>' />
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:BoundField DataField="Km" />
                            <asp:BoundField DataField="Note" HeaderText="Note" />
                            <asp:TemplateField HeaderText="Coordinate">
                                <ItemTemplate>
                                    <asp:Literal runat="server" ID="txtLongitudine" Text='<%# FormattaCoordinate(DataBinder.Eval(Container,"DataItem"))%>' />
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="Rif.Cat.">
                                <ItemTemplate>
                                    <asp:Literal runat="server" ID="txtRifCat" Text='<%# Bind("PrimoRiferimentoCatastale") %>' />
                                </ItemTemplate>
                            </asp:TemplateField>

                            <asp:TemplateField HeaderStyle-Width="70px" ItemStyle-HorizontalAlign="Right">
                                <ItemTemplate>
                                    <asp:LinkButton runat="server" ID="lnkEdit" CommandName="Edit">Modifica</asp:LinkButton>
                                    <asp:LinkButton runat="server" ID="lnk1" CommandName="Delete" Text="Rimuovi" OnClientClick="return confirm('Proseguire con l\'eliminazione?')" />
                                </ItemTemplate>
                            </asp:TemplateField>
                        </Columns>
                    </asp:GridView>

                    <div class="bottoni">
                        <asp:Button runat="server" ID="cmdAddNew" Text="Aggiungi" OnClick="cmdAddNew_Click" />
                    </div>
                </fieldset>
            </asp:View>

            <asp:View runat="server" ID="dettaglioView">

                <%=LoadScript("~/js/app/autocompleteStradario.js") %>

                <script type="text/javascript">

                        $(function () {
                            var tipoCatasto = $('#<%=ddlTipoCatasto.Inner.ClientID%>'),
                                subalterno = $('.subalterno'),
                                comuneLocalizzazione = $('.comuneLocalizzazione'),
                                listaComuni = $('.ddlComuneLocalizzazione > option'),
                                autocompleteStradario;


                            autocompleteStradario = new AutocompleteStradario({
                                idCampoIndirizzo: '<%= acIndirizzo.Inner.ClientID %>',
                                idCampoCodiceStradario: '<%= acIndirizzo.HiddenClientID%>',
                                idCampoComuneLocalizzazione: '<%= ddlComuneLocalizzazione.Inner.ClientID%>',
                                serviceUrl: '<%=ResolveClientUrl("~/Public/WebServices/AutocompleteStradario.asmx") %>/AutocomlpeteStradario',
                                idComune: '<%=IdComune %>',
                                codiceComune: '<%=CodiceComune %>'
                            });

                            tipoCatasto.on('change', function () {
                                var display = $(this).val() == 'F' ? 'block' : 'none';
    
                                subalterno.css('display', display);
                                updateValidators();
                            });

                            if (tipoCatasto.val() != 'F')
                                subalterno.css('display', 'none');

                            if (listaComuni.length < 2) {
                                comuneLocalizzazione.hide();
                            }

                            comuneLocalizzazione.on('change', function () {
                                autocompleteStradario.svuotaCampi();
                            });

                            updateValidators();

                            function onlyNumeric(e) {
                                return !(e.which != 8 && e.which != 0 &&
                                        (e.which < 48 || e.which > 57) && e.which != 46);
                            }

							<%if (CivicoNumerico){%>
                            $('#<%=txtCivico.Inner.ClientID%>').on('keypress', onlyNumeric);
                            <%}%>

							<%if (EsponenteNumerico){%>
                            $('#<%=txtEsponente.Inner.ClientID%>').on('keypress', onlyNumeric);
                            <%}%>
                        });

                </script>

                <asp:GridView runat="server" ID="dgIndirizzi"
                    GridLines="None"
                    AutoGenerateColumns="False"
                    DataKeyNames="CODICESTRADARIO"
                    OnSelectedIndexChanged="dgIndirizzi_SelectedIndexChanged"
                    CssClass="table">
                    <Columns>
                        <asp:TemplateField HeaderText="Via">
                            <ItemTemplate>
                                <asp:Literal runat="server" ID="ltrDescrizione" Text='<%# Bind("NomeVia") %>' />
                            </ItemTemplate>
                        </asp:TemplateField>
                        <asp:ButtonField CommandName="Select" Text="Seleziona" />
                    </Columns>
                </asp:GridView>

                <div>
                    <label>&nbsp;</label>
                    <span><i>I campi contrassegnati con * sono obbligatori</i></span>
                </div>

                <div class="form-small">
                    <ar:DropDownList runat="server" ID="ddlComuneLocalizzazione"
                        Label="Comune"
                        DataTextField="Comune"
                        DataValueField="CodiceComune" />

                    <asp:HiddenField runat="server" ID="HiddenIdLocalizzazione" />
                    <asp:HiddenField runat="server" ID="HiddenCodiceCivico" />
                    <asp:HiddenField runat="server" ID="HiddenCodiceViario" />
                    <ar:Autocomplete runat="server" ID="acIndirizzo" MaxLength="100" Label="Indirizzo" Required="true" />
                    <span id="errMsgOuput"></span>

                    <div class="row">
                        <ar:TextBox runat="server" ID="txtCivico" Label="Civico" MaxLength="10" BtSize="Col4" />
                        <ar:TextBox runat="server" ID="txtEsponente" Label="Esponente" MaxLength="10" BtSize="Col4" />
                        <ar:DropDownList runat="server" ID="ddlColore" Label="Colore" DataTextField="COLORE" DataValueField="CODICECOLORE" BtSize="Col4" />
                    </div>

                    <div class="row">
                        <ar:TextBox runat="server" ID="txtScala" Label="Scala" MaxLength="10" BtSize="Col4" />
                        <ar:TextBox runat="server" ID="txtPiano" Label="Piano" MaxLength="10" BtSize="Col4" />
                        <ar:TextBox runat="server" ID="txtInterno" Label="Interno" MaxLength="10" BtSize="Col4" />
                    </div>

                    <div class="row">
                        <ar:TextBox runat="server" ID="txtEsponenteInterno" Label="Esponente interno" MaxLength="10" BtSize="Col4" />
                        <ar:TextBox runat="server" ID="txtFabbricato" Label="Fabbricato" MaxLength="10" BtSize="Col4" />
                        <ar:TextBox runat="server" ID="txtKm" Label="Km" MaxLength="10" BtSize="Col4" />
                    </div>

                    <ar:TextBox runat="server" ID="txtNote" Label="Note" MaxLength="80" Rows="4" TextMode="MultiLine" />


                    <%if (CoordinateVisibili)
                      { %>
                    <fieldset>
                        <legend>
                            <asp:Literal runat="server" Text="Coordinate" ID="ltrTitoloBloccoCoordinate" />
                        </legend>
                        <div class="row">
                            <ar:TextBox runat="server" ID="txtLongitudine" Label="Longitudine" MaxLength="50" BtSize="Col6" />
                            <ar:TextBox runat="server" ID="txtLatitudine" Label="Latitudine" MaxLength="50" BtSize="Col6" />
                        </div>
                    </fieldset>
                    <%} %>

                    <%if (DatiCatastaliVisibili)
                      { %>
                    <fieldset>
                        <legend>Riferimenti catastali</legend>
                        <div class="row">
                            <ar:DropDownList runat="server" ID="ddlTipoCatasto" Label="Tipo catasto" CssClass="tipoCatasto" BtSize="Col4" />
                            <asp:HiddenField runat="server" ID="txtSezione" />
                            <ar:TextBox runat="server" ID="txtFoglio" Label="Foglio" MaxLength="10" BtSize="Col2" />
                            <ar:TextBox runat="server" ID="txtParticella" Label="Particella" MaxLength="10" BtSize="Col2" />
                            <ar:TextBox runat="server" ID="txtSub" Label="Subalterno" MaxLength="10" CssClass="subalterno" BtSize="Col2" />
                        </div>
                    </fieldset>
                    <%} %>


                    <asp:HiddenField runat="server" ID="txtAccessoTipo" Value="" />
                    <asp:HiddenField runat="server" ID="txtAccessoNumero" Value="" />
                    <asp:HiddenField runat="server" ID="txtAccessoDescrizione" Value="" />

                    <asp:Button ID="cmdConfirm" CssClass="btn btn-primary" runat="server" Text="Conferma" OnClick="cmdConfirm_Click" />
                    <asp:LinkButton ID="cmdCancel" CssClass="btn btn-default" runat="server" Text="Annulla" OnClick="cmdCancel_Click" />
                </div>
            </asp:View>
        </asp:MultiView>

    </div>
</asp:Content>
