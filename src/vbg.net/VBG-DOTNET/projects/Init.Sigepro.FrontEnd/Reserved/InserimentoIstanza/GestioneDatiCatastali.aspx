<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"
    AutoEventWireup="true" CodeBehind="GestioneDatiCatastali.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneDatiCatastali"
    Title="Untitled Page" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
    <asp:ScriptManager runat="server" ID="scriptManager1">
    </asp:ScriptManager>
    <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
        <asp:View runat="server" ID="listaView">
            <div class="inputForm">
                <fieldset>
                    <div>
                        <asp:GridView runat="server" ID="dgDatiCatastali"
                            AutoGenerateColumns="False"
                            DataKeyNames="Id"
                            OnRowDeleting="dgDatiCatastali_DeleteCommand"
                            GridLines="None" CssClass="table">
                            <Columns>
                                <asp:TemplateField HeaderText="Localizzazione">
                                    <ItemTemplate>
                                        <asp:Literal runat="server" ID="ltrLocalizzazione" Text='<%# GetLocalizzazione( DataBinder.Eval(Container,"DataItem")) %>' />
                                    </ItemTemplate>
                                </asp:TemplateField>
                                <asp:BoundField DataField="TipoCatasto" HeaderText="Tipo catasto" />
                                <asp:BoundField DataField="Foglio" HeaderText="Foglio" />
                                <asp:BoundField DataField="Particella" HeaderText="Particella" />
                                <asp:BoundField DataField="Sub" HeaderText="Subalterno" />
                                <asp:TemplateField ItemStyle-HorizontalAlign="Right">
                                    <ItemTemplate>
                                        <asp:LinkButton runat="server" ID="lnkElimina" Text="Rimuovi" CommandName="Delete"
                                            OnClientClick="return confirm('Proseguire con l\'eliminazione?')" />
                                    </ItemTemplate>
                                </asp:TemplateField>
                            </Columns>
                        </asp:GridView>
                    </div>
                    <div class="bottoni">
                        <asp:Button runat="server" ID="cmdNuovo" Text="Aggiungi" OnClick="cmdNuovo_Click" />
                    </div>
                </fieldset>
            </div>
        </asp:View>
        <asp:View runat="server" ID="dettaglioView">
            <script type="text/javascript">
                $(function () {
                    var idTipoCatasto = '#<%=ddlTipoCatasto.Inner.ClientID %>';
                    var idSubalterno = '.pnl-subalterno';

                    $(idTipoCatasto).change(function () {
                        var el = $(idSubalterno);
                        var display = $(this).val() == 'F' ? 'block' : 'none';

                        el.css('display', display);
                    });

                    if ($(idTipoCatasto).val() != 'F')
                        $(idSubalterno).css('display', 'none');

                });
            </script>
            <div class="form-small">
                <h3>Riferimenti catastali</h3>
                <ar:DropDownList ID="ddlIndirizzo" runat="server" DataValueField="Key" DataTextField="Value" Label="Localizzazione" Required="true" />
                <div class="row">
                    <ar:DropDownList ID="ddlTipoCatasto" runat="server" DataValueField="Key" DataTextField="Value" Label="Tipo catasto" Required="true" BtSize="Col4" />

                    <ar:TextBox ID="txtFoglio" runat="server" MaxLength="5" Label="Foglio" Required="true" BtSize="Col2" />
                    <ar:TextBox ID="txtParticella" runat="server" MaxLength="5" Label="Particella" Required="true" BtSize="Col2" />
                    <ar:TextBox ID="txtSub" runat="server" MaxLength="5" Label="Subalterno" CssClass="pnl-subalterno" BtSize="Col2" />
                </div>

                <div class="bottoni">
                    <asp:Button ID="cmdAdd" runat="server" Text="Aggiungi" OnClick="cmdAdd_Click" />
                    <asp:LinkButton ID="cmdReset" runat="server" Text="Annulla" CausesValidation="False"
                        OnClick="cmdReset_Click" />
                </div>
            </div>
        </asp:View>
    </asp:MultiView>
</asp:Content>
