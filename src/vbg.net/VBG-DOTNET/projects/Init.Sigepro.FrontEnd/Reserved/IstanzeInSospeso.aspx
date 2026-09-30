<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="IstanzeInSospeso.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.ListaIstanzePresentate"
    Title="Istanze in sospeso" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content ContentPlaceHolderID="headPagina" runat="server" ID="headPagina1">
    <script type="text/javascript">
        $(function () {

            let bottoneElimina = $("#<%=cmdDeleteRows.ClientID%>"),
                modalElimina = $('#<%=bmConfermaEliminazione.ClientID%>');

            function mostraBottoneElimina() {
                var len = $('.chkChecked>input[type=checkbox]:checked').length,
                    text = "Elimina la bozza di pratica selezionata";

                if (len > 1) {
                    text = "Elimina le bozze di pratica selezionate (" + len.toString() + " elementi)";
                }

                bottoneElimina.val(text);
                bottoneElimina.toggle(len > 0);
            }

            $('.chkChecked>input[type=checkbox]').on('click', function () {

                verificaClasseRigaDaCheckbox($(this));
                mostraBottoneElimina();
            });

            function verificaClasseRigaDaCheckbox($checkbox) {
                $checkbox.closest('tr').toggleClass('info', $checkbox.is(':checked'));
            }

            mostraBottoneElimina();

            $('#chkSelectAll').on('click', x => {
                $('.chkChecked>input[type=checkbox]')
                    .prop("checked", $('#chkSelectAll').is(':checked'))
                    .each((idx, el) => {
                        verificaClasseRigaDaCheckbox($(el));
                        mostraBottoneElimina();
                    });
            });

            bottoneElimina.on('click', (e) => {
                modalElimina.modal('show');

                e.preventDefault();
            });

            modalElimina.find('.modal-ok-button').on('click', () => {
                modalElimina.modal('hide');
                mostraModalEliminazioneInCorso();
            });
        });

    </script>
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div class="istanze-in-sospeso">
        <asp:GridView ID="dgIstanzePresentate"
            runat="server"
            DataKeyNames="Id"
            AutoGenerateColumns="False"
            OnSelectedIndexChanged="dgIstanzePresentate_SelectedIndexChanged"
            OnRowDataBound="dgIstanzePresentate_ItemDataBound"
            GridLines="None"
            EmptyDataText="<div class='alert alert-info'>Non sono state trovate pratiche in sospeso</div>"
            CssClass="table">
            <Columns>
                <asp:TemplateField HeaderText="<input type='checkbox' id='chkSelectAll' />" ItemStyle-Width="1%">
                    <ItemTemplate>
                        <asp:HiddenField runat="server" ID="hidBookmark" />
                        <asp:CheckBox runat="server" ID="chkChecked" Checked="false" CssClass="chkChecked" />
                    </ItemTemplate>
                </asp:TemplateField>

                <asp:TemplateField HeaderText="Identificativo domanda">
                    <ItemTemplate>
                        <asp:Label runat="server" ID="lblIdDomanda" Text='<%#Eval("Identificativodomanda") %>' /><br />
                        <i>Ultima modifica:
                        <asp:Label runat="server" ID="lblUltimaModifica" Text='<%#Eval("DataUltimaModifica", "{0:dd/MM/yyyy HH:mm}") %>' /></i>
                    </ItemTemplate>
                </asp:TemplateField>

                <asp:TemplateField HeaderText="Richiedente" ItemStyle-Width="15%">
                    <ItemTemplate>
                        <asp:Label runat="server" ID="lblRichiedente"></asp:Label>
                    </ItemTemplate>
                </asp:TemplateField>



                <asp:TemplateField HeaderText="Tipo intervento" ItemStyle-Width="45%">
                    <ItemTemplate>
                        <asp:Label runat="server" ID="lblTipoIntervento"></asp:Label>
                    </ItemTemplate>
                </asp:TemplateField>

                <asp:TemplateField HeaderText="Oggetto" ItemStyle-Width="15%">
                    <ItemTemplate>
                        <asp:Label runat="server" ID="lblOggetto"></asp:Label>
                    </ItemTemplate>
                </asp:TemplateField>

                <asp:TemplateField ItemStyle-HorizontalAlign="Right" ItemStyle-Width="10%">
                    <ItemTemplate>
                        <asp:LinkButton runat="server" Text="Riprendi" CommandName="Select" CausesValidation="false" ID="Linkbutton1"></asp:LinkButton>
                    </ItemTemplate>
                </asp:TemplateField>
            </Columns>
        </asp:GridView>

        <asp:Button runat="server" ID="cmdDeleteRows" CssClass="btn btn-primary" Text="Elimina le istanze selezionate" Style="display: none" />
        <asp:Button runat="server" ID="cmdClose" CssClass="btn btn-default" Text="Chiudi" OnClick="cmdClose_Click" />
    </div>

    <ar:BootstrapModal runat="server" ID="bmConfermaEliminazione" OnOkClicked="cmdDeleteRows_Click" Title="Conferma eliminazione" OkText="Conferma" KoText="Annulla">
        <ModalBody>
            <div>
                <ar:RisorsaTestualeLabel runat="server" IdRisorsa="conferma_eliminazione_pratiche_in_sospeso"
                    ValoreDefault="Sei sicuro di voler eliminare le pratiche in sospeso selezionate?<br />L'operazione non potrà essere annullata." />
            </div>
        </ModalBody>
    </ar:BootstrapModal>
</asp:Content>
