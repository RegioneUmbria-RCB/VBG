<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneStradario.aspx.cs"
    Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneStradario" Title="Untitled Page" %>

<%@ Register Assembly="Init.Utils" Namespace="Init.Utils.Web.UI" TagPrefix="cc2" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>


<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">

    <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
        <asp:View runat="server" ID="listaView">

            <script type="text/javascript">    
                $(function () {
                    $('.note-stradario').each(function () {
                        var $el = $(this);
                        $el.attr('title', $(this).text());
                        $el.tooltip();
                    });
                });
            </script>

            <style>
                .note-stradario {
                    text-overflow: ellipsis;
                    white-space: nowrap;
                    overflow: hidden;
                    display: inline-block;
                    width: 200px;
                }
            </style>

            <asp:GridView ID="dgStradario" Width="100%"
                AutoGenerateColumns="False"
                DataKeyNames="Id"
                OnRowDeleting="dgStradario_DeleteCommand"
                GridLines="None"
                runat="server"
                CssClass="table">
                <Columns>
                    <asp:BoundField DataField="Indirizzo" ReadOnly="True" HeaderText="Indirizzo">
                        <HeaderStyle Width="40%"></HeaderStyle>
                    </asp:BoundField>
                    <asp:BoundField DataField="Civico" ItemStyle-Width="5%" />
                    <asp:BoundField DataField="Esponente" ItemStyle-Width="5%" />
                    <asp:BoundField DataField="Colore" ItemStyle-Width="5%" />
                    <asp:BoundField DataField="Scala" ItemStyle-Width="5%" />
                    <asp:BoundField DataField="Piano" ItemStyle-Width="5%" />
                    <asp:BoundField DataField="Interno" ItemStyle-Width="5%" />
                    <asp:BoundField DataField="EsponenteInterno" ItemStyle-Width="5%" />
                    <asp:BoundField DataField="Fabbricato" ItemStyle-Width="5%" />
                    <asp:BoundField DataField="Km" ItemStyle-Width="5%" />
                    <%--<asp:BoundField DataField="Note" ReadOnly="True" HeaderText="Note" ItemStyle-Width="20%" />--%>
                    <asp:TemplateField HeaderText="Note">
                        <ItemTemplate>
                            <div class="note-stradario" data-toggle="tooltip">
                                <%#Eval("Note") %>
                            </div>
                        </ItemTemplate>
                    </asp:TemplateField>

                    <asp:TemplateField HeaderStyle-Width="70px" ItemStyle-HorizontalAlign="Right">
                        <ItemTemplate>
                            <asp:LinkButton runat="server" ID="lnk1" CommandName="Delete" Text="Rimuovi" OnClientClick="return confirm('Proseguire con l\'eliminazione?')"></asp:LinkButton>
                        </ItemTemplate>
                    </asp:TemplateField>
                </Columns>
            </asp:GridView>

            <div class="bottoni">
                <asp:Button runat="server" ID="cmdAddNew" Text="Aggiungi" OnClick="cmdAddNew_Click" />
            </div>
        </asp:View>
        <asp:View runat="server" ID="dettaglioView">

            <%=LoadScript("~/js/app/autocompleteStradario.js") %>

            <script type="text/javascript">
                $(function () {
                    new AutocompleteStradario({
                        idCampoIndirizzo: '<%= Indirizzo.Inner.ClientID %>',
                            idCampoCodiceStradario: '<%= Indirizzo.HiddenClientID%>',
                            serviceUrl: '<%=ResolveClientUrl("~/Public/WebServices/AutocompleteStradario.asmx") %>/AutocomlpeteStradario',
                            idComune: '<%=IdComune %>',
                            codiceComune: '<%=CodiceComune %>'
                    });

                    function onlyNumeric(e) {
                        return !(e.which != 8 && e.which != 0 &&
                            (e.which < 48 || e.which > 57) && e.which != 46);
                    }

                    <%if (CivicoNumerico) {%>
                        $('#<%=txtCivico.Inner.ClientID%>').on('keypress', onlyNumeric);
				    <%}%>

				    <%if (EsponenteNumerico) {%>
                        $('#<%=txtEsponente.Inner.ClientID%>').on('keypress', onlyNumeric);
				    <%}%>
                });
            </script>

            <asp:GridView runat="server" ID="dgIndirizzi"
                GridLines="None"
                AutoGenerateColumns="False"
                DataKeyNames="CODICESTRADARIO"
                OnSelectedIndexChanged="dgIndirizzi_SelectedIndexChanged">
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
                <ar:Autocomplete runat="server" ID="Indirizzo" Label="Indirizzo" />
                <span id="errMsgOuput"></span>

                <ar:TextBox runat="server" ID="txtCivico" Label="Civico" MaxLength="10" />
                <ar:TextBox runat="server" ID="txtEsponente" Label="Esponente" MaxLength="10" />
                <ar:DropDownList runat="server" ID="ddlColore" Label="Colore" DataTextField="COLORE" DataValueField="CODICECOLORE" />
                <ar:TextBox runat="server" ID="txtScala" Label="Scala" MaxLength="10" />
                <ar:TextBox runat="server" ID="txtPiano" Label="Piano" MaxLength="10" />
                <ar:TextBox runat="server" ID="txtInterno" Label="Interno" MaxLength="10" />
                <ar:TextBox runat="server" ID="txtEsponenteInterno" Label="Esponente interno" MaxLength="10" />
                <ar:TextBox runat="server" ID="txtFabbricato" Label="Fabbricato" MaxLength="10" />
                <ar:TextBox runat="server" ID="txtKm" Label="Km" MaxLength="10" />
                <ar:TextBox runat="server" ID="Note" Label="Note" MaxLength="4000" Rows="4" TextMode="MultiLine" />

                <div class="bottoni">
                    <asp:Button ID="cmdConfirm" runat="server" Text="Conferma" OnClick="cmdConfirm_Click" />
                    <asp:LinkButton ID="cmdCancel" runat="server" Text="Annulla" OnClick="cmdCancel_Click" />
                </div>
            </div>
        </asp:View>
    </asp:MultiView>
</asp:Content>
